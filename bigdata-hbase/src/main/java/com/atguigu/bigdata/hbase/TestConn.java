package com.atguigu.bigdata.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import sun.awt.ConstrainableGraphics;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author zxfcode
 * @create 2018-10-23 19:51
 */
public class TestConn {
    private static  ThreadLocal<Admin> adminLocal = new ThreadLocal<Admin>();
    private static ThreadLocal<Connection> connLocal = new ThreadLocal<Connection>();
    public static void main(String[] args) throws IOException {
        //得到资源
//        Admin admin = getAdmin();
//        System.out.println(admin);
        //表是否存在
//        boolean flg = isTableExists("student");
//        System.out.println("表存在吗：==" + flg);
        //创建表
//        createTable("yueyue","info","congming");

        //删除表
//        deleteTable("emp3");
        //命名空间是否存在
//        boolean flg = isNamespaceExists("atguigu123");
//        System.out.println("空间是否存在 = " +flg);
        //新建命名空间
//        createNamespace("atguigu777");
        //关闭资源
        //得到连接
        //添加数据
//        addData("student","1001","info","name","zhangsan");
//        addData("student","1002","info","name","lisi");
//        addData("student","10011","info","name","wangwu");
//        addData("student","10012","info","name","zhaoliu");
        //删除指定rowkey的数据-->删除几条数据
//        deleteData("student","1001","10011");
        //得到表所有数据
//        getAllData("student");
        //得到指定列族数据
        getColData("student" ,"1001","info","name");
        System.out.println("操作成功");
        close();
    }
    //得到指定列族数据
    public static void getColData(String tabname,String rowKey,String family,String column) throws IOException {
        Table table = getConnect().getTable(TableName.valueOf(tabname));
        Get get = new Get(Bytes.toBytes(rowKey));
        Result result = table.get(get);
        Cell[] cells = result.rawCells();
        for (Cell cell : cells) {
            System.out.println(result.getRow());
            System.out.println(Bytes.toString(CellUtil.cloneFamily(cell)));
            System.out.println(Bytes.toString(CellUtil.cloneQualifier(cell)));
            System.out.println(Bytes.toString(CellUtil.cloneValue(cell)));
        }

    }
    //得到表所有数据
    public static void getAllData(String tabname) throws IOException {
        Table table = getConnect().getTable(TableName.valueOf(tabname));
        Scan scan = new Scan();
        ResultScanner results = table.getScanner(scan);
        for (Result result : results) {
            Cell[] cells = result.rawCells();
            for (Cell cell : cells) {
                String row = Bytes.toString(CellUtil.cloneRow(cell));
                String family = Bytes.toString(CellUtil.cloneFamily(cell));
                String column = Bytes.toString(CellUtil.cloneQualifier(cell));
                String value = Bytes.toString(CellUtil.cloneValue(cell));
                System.out.println(row+","+family+","+column+","+value);
            }

        }
    }
    //删除几条数据
    public static void deleteData(String tabname,String... rows) throws IOException {
        Table table = getConnect().getTable(TableName.valueOf(tabname));
        ArrayList<Delete> deletes = new ArrayList<Delete>();
        for (String row : rows) {
            Delete delete = new Delete(Bytes.toBytes(row));
            deletes.add(delete);
        }
        table.delete(deletes);
        table.close();
    }
    //添加数据
    public static void addData(String tabname,String rowkey,String family,String column,String value) throws IOException {
        Table table = getConnect().getTable(TableName.valueOf(tabname));
        Put put = new Put(Bytes.toBytes(rowkey));
        put.addColumn(Bytes.toBytes(family),Bytes.toBytes(column),Bytes.toBytes(value));
        table.put(put);
        table.close();
    }
    //关闭资源
    public static void close() throws IOException {
        Admin admin = adminLocal.get();
        if(admin != null){
            admin.close();
            adminLocal.remove();
        }

    }
    //新建命名空间
    public static void createNamespace(String namespace) throws IOException {
        Admin admin = getAdmin();
        //??
        NamespaceDescriptor nd = NamespaceDescriptor.create(namespace).build();
        admin.createNamespace(nd);
    }
    //命名空间是否存在
    public static boolean isNamespaceExists(String namespace) throws IOException {
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
    //删除表
    public static void deleteTable(String tabname) throws IOException {
        Admin admin = getAdmin();
        admin.disableTable(TableName.valueOf(tabname));
        admin.deleteTable(TableName.valueOf(tabname));
    }
    //得到表
    public static void createTable(String tabname,String family,String... families) throws IOException {
        Admin admin = getAdmin();
        HTableDescriptor table = new HTableDescriptor(TableName.valueOf(tabname));
        table.addFamily(new HColumnDescriptor(family));
        for (String s : families) {
            table.addFamily(new HColumnDescriptor(s));
        }
        admin.createTable(table);
    }
    //表是否存在
    public static boolean isTableExists(String tabname) throws IOException {
        return getAdmin().tableExists(TableName.valueOf(tabname));
    }
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
}
