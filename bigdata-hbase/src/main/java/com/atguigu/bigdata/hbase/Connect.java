package com.atguigu.bigdata.hbase;

import javafx.scene.control.Tab;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zxfcode
 * @create 2018-10-23 11:16
 */
public class Connect {
    private static ThreadLocal<Admin> adminLocal = new ThreadLocal<Admin>();
    private static ThreadLocal<Connection> connLocal = new ThreadLocal<Connection>();
    public static void main(String[] args) throws IOException {
//        Configuration conf = HBaseConfiguration.create();
//        conf.set("hbase.zookeeper.quorum","hadoop102");
//        conf.set("hbase.zookeeper.property.clientPort","2181");
//         HBaseAdmin admin = new HBaseAdmin(conf);
//        System.out.println(admin);
        Admin admin = getAdmin();
//        boolean student = isTableExist( "student");
//        System.out.println("是否存在表 = " +student);
        //createTable("emp","indo");
//        createTable("atguigu123:emp2","info","default");
//        createTable("emp3","info");
//        System.out.println("创建成功！");
//        deleteTab("emp2");
//        boolean flg = hasNamespace("atguigu123");
//        if(!flg){
//            createNamespace("atguigu123");
//            System.out.println("命名空间创建成功");
//        }
//        addData("student", "1003", "info", "name", "zhangsan");
//        System.out.println("添加数据成功");
        //addData("emp","1001","indo","name","zhangsan");
//        deleteData("student","1003");
//        deleteDatas("student","1001","1002");
//        System.out.println("删除数据成功");
//        getAllData("student");
//        getData("student","1002");
//        getRowData("student","1002","info","name");
        close();

    }
    //获取指定rowkey和列族列名数据
    public static void getRowData(String tabname,String rowkey,String family,String column) throws IOException {
        Table table = getConnection().getTable(TableName.valueOf(tabname));
        Get get = new Get(Bytes.toBytes(rowkey));
        get.addColumn(Bytes.toBytes(family),Bytes.toBytes(column));
        Result result = table.get(get);
        Cell[] cells = result.rawCells();
        for (Cell cell : cells) {
            String row = Bytes.toString(result.getRow());
            String family2 = Bytes.toString(CellUtil.cloneFamily(cell));
            String column2 = Bytes.toString(CellUtil.cloneQualifier(cell));
            String value = Bytes.toString(CellUtil.cloneValue(cell));
            System.out.println(row+","+family2+":"+column2+","+value);
        }
    }
    //获取指定rowkey数据
    public static void getData(String tabname,String rowkey) throws IOException {
        //获取表对象
        Table table = getConnection().getTable(TableName.valueOf(tabname));
        //还有另一种写法,这里用的是工具类
        //rowkey.getBytes("utf-8");
        Get get = new Get(Bytes.toBytes(rowkey));
        //查询单一数据
        Result result = table.get(get);
        Cell[] cells = result.rawCells();
        for (Cell cell : cells) {
            String family = Bytes.toString(CellUtil.cloneFamily(cell));
            String column = Bytes.toString(CellUtil.cloneQualifier(cell));
            String value = Bytes.toString(CellUtil.cloneValue(cell));
            System.out.println(family+":"+column+","+value);
        }
    }
    //获取全部数据
    public static void getAllData(String tabname) throws IOException {
        //获取表对象
        Table table = getConnection().getTable(TableName.valueOf(tabname));
        //全表扫描
        Scan scan = new Scan();
        ResultScanner scanner = table.getScanner(scan);
        for (Result result : scanner) {
            Cell[] cells = result.rawCells();
            for (Cell cell : cells) {


                String row = Bytes.toString(CellUtil.cloneRow(cell));
                String family = Bytes.toString(CellUtil.cloneFamily(cell));
                String column = Bytes.toString(CellUtil.cloneQualifier(cell));
                String value = Bytes.toString(CellUtil.cloneValue(cell));
                System.out.println(row+","+family+":"+column+","+value);
            }
        }
    }
    public static void deleteDatas(String tbname,String... rows) throws IOException {
        //获取表对象
        Table table = getConnection().getTable(TableName.valueOf(tbname));
        //创建要删除数据的集合
        List<Delete> deletes = new ArrayList<Delete>();
        for (String row : rows) {
            Delete delete = new Delete(Bytes.toBytes(row));
            deletes.add(delete);
        }
        table.delete(deletes);
        table.close();
    }
    //删除数据
    public static void deleteData(String tabname,String rowkey) throws IOException {
       //获取表对象
        Table table = getConnection().getTable(TableName.valueOf(tabname));
        //构造删除的数据
        Delete delete = new Delete(Bytes.toBytes(rowkey));
        //删除数据
        table.delete(delete);


    }
    //添加数据
    public static void addData(String tabname,String rowKey,String family,String column,String value) throws IOException {
        //获取表对象
        Table table = getConnection().getTable(TableName.valueOf(tabname));
        //构造数据
        //还有一种写法rowkey.getBytes("utf-8");
        Put put = new Put(Bytes.toBytes(rowKey));
        put.addColumn(Bytes.toBytes(family),Bytes.toBytes(column),Bytes.toBytes(value));
        //增加数据
        table.put(put);
        //关闭表格
        table.close();
    }
    //创建命名空间
    public static void createNamespace(String namespace) throws IOException {
        Admin admin = getAdmin();
        NamespaceDescriptor nd = NamespaceDescriptor.create(namespace).build();
        admin.createNamespace(nd);
    }
    //是否存在命名空间
    public static boolean hasNamespace(String namespace) {
        try {
            Admin admin = getAdmin();
            admin.getNamespaceDescriptor(namespace);
            return true;
        }catch (NamespaceNotFoundException e){
            System.out.println("命名空间不存在！");
//            e.printStackTrace();
            return false;
        }catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    //删除表
    public static void deleteTab(String tabname) throws IOException {
        Admin admin = getAdmin();
        if(isTableExist(tabname)){
            admin.disableTable(TableName.valueOf(tabname));
            admin.deleteTable(TableName.valueOf(tabname));
            System.out.println("删除表成功");
        }else{
            System.out.println("表不存在");
        }
    }
    //创建表
    public static void createTable(String tabname,String family,String... families) throws IOException {
        Admin admin = getAdmin();
        if(isTableExist(tabname)){

        }else{
            HTableDescriptor tabDes = new HTableDescriptor(TableName.valueOf(tabname));
            tabDes.addFamily(new HColumnDescriptor(family));
            for (String s : families) {
                tabDes.addFamily(new HColumnDescriptor(s));
            }
            admin.createTable(tabDes);
        }
    }
    //关闭资源
    public static void close() throws IOException {
        Admin admin = getAdmin();
        if(admin != null){
            admin.close();
            adminLocal.remove();
        }
    }
    //表是否存在
    public static boolean isTableExist(String tablename) throws IOException {
        Admin admin = getAdmin();
        return admin.tableExists(TableName.valueOf(tablename));
    }
    //得到配置信息
    public static Admin getAdmin() throws IOException {
        Admin admin = adminLocal.get();
        //对admin进行判断
        if(admin == null){
            //获取管理对象
            admin = getConnection().getAdmin();
            //将新创建的admin对象放进线程缓存中
            adminLocal.set(admin);
        }
        return admin;
    }
    //管理连接
    public static Connection getConnection() throws IOException {
        Connection conn = connLocal.get();
        if(conn == null){
            //获取配置对象
            //读hbase-default.xml和hbase-site.xml
            Configuration config = HBaseConfiguration.create();
            //建立连接
            conn = ConnectionFactory.createConnection(config);
            //将连接放入线程缓存中
            connLocal.set(conn);
        }
        return conn;
    }

}
