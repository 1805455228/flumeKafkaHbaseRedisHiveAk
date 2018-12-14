package dao;

import hbase.util.HbaseWeiBoUtils;

import java.io.IOException;

/**
 * @author zxfcode
 * @create 2018-10-26 18:13
 */
public class AdminDao {
    public void createNamespace(String name) throws IOException {
       HbaseWeiBoUtils.createNamespace(name);
    }

    public void close() throws IOException {
        HbaseWeiBoUtils.close();
    }


    public void createTable(String tabname, String... families) throws IOException {
        HbaseWeiBoUtils.createTable(tabname,families);
    }

    public void createTable( String tableName, int versions, String... cfNames ) {
        try {
            HbaseWeiBoUtils.createTable(tableName, versions, cfNames);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean isTableExists(String tabname) throws IOException {
        return HbaseWeiBoUtils.isTableExists(tabname);
    }

    public void deleteTable(String tabname) throws IOException {
        HbaseWeiBoUtils.deleteTable(tabname);
    }
}
