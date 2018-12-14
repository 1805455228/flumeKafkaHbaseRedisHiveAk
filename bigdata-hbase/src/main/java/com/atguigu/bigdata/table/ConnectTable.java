package com.atguigu.bigdata.table;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zxfcode
 * @create 2018-10-23 11:16
 */
public class ConnectTable {
    private static ThreadLocal<Admin> adminLocal = new ThreadLocal<Admin>();
    private static ThreadLocal<Connection> connLocal = new ThreadLocal<Connection>();
    public static void main(String[] args) throws IOException, IllegalAccessException {
        Student student = new Student();
        student.setRowkey("1001");
        student.setName("zhangsan");
        student.setTel("11111");
        addTableData(student);
        close();

    }

    //获取全部数据
    public static void addTableData(Object obj) throws IOException, IllegalAccessException {
        List<Put> puts = new ArrayList<Put>();
        Class clazz = obj.getClass();
        TableRef tabRef = (TableRef) clazz.getAnnotation(TableRef.class);
        String tabName = tabRef.value();
        String rowKey = "";
        Field[] fs = clazz.getDeclaredFields();
        if(fs != null){
            for (Field field : fs) {
                Rowkey rowkeyRef = field.getAnnotation(Rowkey.class);
                if(rowkeyRef != null){
                    field.setAccessible(true);
                    rowKey = (String)field.get(obj);
                }
            }
            for (Field field : fs) {
                ColumnRef columnRef = field.getAnnotation(ColumnRef.class);
                if(columnRef != null){
                    String cf = columnRef.columnFamily();
                    String column = columnRef.column();
                    if("".equals(column) || column == null){
                        column = field.getName();
                    }
                    field.setAccessible(true);
                    String val =(String)field.get(obj);
                    Put put = new Put(Bytes.toBytes(rowKey));
                    put.addColumn(Bytes.toBytes(cf),Bytes.toBytes(column),Bytes.toBytes(val));
                    puts.add(put);
                }
            }
        }
        //获取表对象
        Table table = getConnection().getTable(TableName.valueOf(tabName));
        table.put(puts);
        table.close();
    }

    //关闭资源
    public static void close() throws IOException {
        Admin admin = getAdmin();
        if(admin != null){
            admin.close();
            adminLocal.remove();
        }
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
