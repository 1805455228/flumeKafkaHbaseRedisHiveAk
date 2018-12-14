package hbase.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zxfcode
 * @create 2018-10-26 12:34
 */
public class HbaseWeiBoUtils {
    private static  ThreadLocal<Admin> adminLocal = new ThreadLocal<Admin>();
    private static ThreadLocal<Connection> connLocal = new ThreadLocal<Connection>();

    //得到资源
    public static Admin getAdmin() throws IOException {
        Admin admin = adminLocal.get();
        if(admin == null){
            admin = getConnect().getAdmin();
            adminLocal.set(admin);
        }
        return admin;
    }

    //得到连接
    public static Connection getConnect() throws IOException {
        Connection conn = connLocal.get();
        if(conn == null){
            //获取配置对象
            //从hbase-default.xml和hbase-site.xml中获得
            Configuration conf = HBaseConfiguration.create();
            //建立连接
            conn = ConnectionFactory.createConnection(conf);
            connLocal.set(conn);
        }
        return conn;
    }

    //新建命名空间
    public static void createNamespace(String namespace) throws IOException {
        Admin admin = getAdmin();
        //??
        NamespaceDescriptor nd = NamespaceDescriptor.create(namespace).build();
        admin.createNamespace(nd);
    }

    //命名空间是否存在
    public static boolean hasNamespace(String namespace) throws IOException {
        try {
            Admin admin = getAdmin();
            admin.getNamespaceDescriptor(namespace);
            return true;
        } catch (NamespaceNotFoundException e){
//            e.printStackTrace();
            System.out.println("命名空间不存在");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //关闭资源
    public static void close() throws IOException {
        Admin admin = adminLocal.get();
        if(admin != null){
            admin.close();
            adminLocal.remove();
        }

    }
    //创建表
    public static void createTable(String tabname,String... families) throws IOException {
        createTable(tabname,1,families);
    }
    //创建表
    public static void createTable(String tabname,int versions,String... families) throws IOException {
        Admin admin = getAdmin();
        HTableDescriptor table = new HTableDescriptor(TableName.valueOf(tabname));
        for (String s : families) {
            HColumnDescriptor cf = new HColumnDescriptor(s);
            cf.setMaxVersions(versions);
            cf.setMinVersions(versions);
            table.addFamily(cf);
        }
        admin.createTable(table);
    }

    //表是否存在
    public static boolean isTableExists(String tabname) throws IOException {
        return getAdmin().tableExists(TableName.valueOf(tabname));
    }

    //删除表
    public static void deleteTable(String tabname) throws IOException {
        Admin admin = getAdmin();
        admin.disableTable(TableName.valueOf(tabname));
        admin.deleteTable(TableName.valueOf(tabname));
    }

    /**
     * 明星发布微博后，给自己的表添加该信息
     * @param tabname
     * @param put
     * @throws IOException
     */
    public static void addData(String tabname, Put put) throws IOException{
        Table table = getConnect().getTable(TableName.valueOf(tabname));
        table.put(put);
    }
    /**
     * 查村指定表中的数据，这里是用户关系表的uerid
     * @param tabname
     * @param get
     */
    public static Result getData(String tabname, Get get) throws IOException {
        Table table = getConnect().getTable(TableName.valueOf(tabname));
        return table.get(get);
    }

    /**
     * 明星发微博后，给粉丝收件箱发送该信息
     * @param tabname
     * @param puts
     */
    public static void addDatas(String tabname, List<Put> puts) throws IOException {
        Table table = getConnect().getTable(TableName.valueOf(tabname));
        table.put(puts);
    }

    /**
     * 扫描表得到所有数据
     * @param tabname
     * @param scan
     * @throws IOException
     */
    public static ResultScanner scanData(String tabname, Scan scan) throws IOException {
        Table table = getConnect().getTable(TableName.valueOf(tabname));
        return table.getScanner(scan);
    }

    //删除数据
    public static void deleteData(String tabname,String rowkey) throws IOException {
        //获取表对象
        Table table = getConnect().getTable(TableName.valueOf(tabname));
        //构造删除的数据
        Delete delete = new Delete(Bytes.toBytes(rowkey));
        //删除数据
        table.delete(delete);
    }

    //得到指定列族数据
    public static Result getColData(String tabname,String rowKey,String family,String column) throws IOException {
        Table table = getConnect().getTable(TableName.valueOf(tabname));
        Get get = new Get(Bytes.toBytes(rowKey));
        return table.get(get);

    }

    public static void deleteDatas(String tabname, Delete rowkey) throws IOException {
        Table table = getConnect().getTable(TableName.valueOf(tabname));
        table.delete(rowkey);
    }
}
