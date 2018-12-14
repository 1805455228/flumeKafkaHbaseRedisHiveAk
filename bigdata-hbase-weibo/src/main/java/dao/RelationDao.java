package dao;

import hbase.context.NameTable;
import hbase.util.HbaseWeiBoUtils;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zxfcode
 * @create 2018-10-26 15:13
 */
public class RelationDao {
    /**
     *
     * @param cfname
     * @param rowkey
     * @param column
     * @param val
     */
    public void putData(String cfname, String rowkey, String column, String val) {
        Put put = new Put(Bytes.toBytes(rowkey));
        put.addColumn(Bytes.toBytes(cfname),Bytes.toBytes(column),Bytes.toBytes(val));

        try {
            HbaseWeiBoUtils.addData(NameTable.TABLE_RELATIONS.getName(),put);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeAttends(String cfcol, String fanid, String starid) throws IOException {

            Delete rowkey = new Delete(Bytes.toBytes(fanid));
            rowkey.addColumn(Bytes.toBytes(cfcol),Bytes.toBytes(starid));
            HbaseWeiBoUtils.deleteDatas(NameTable.TABLE_RELATIONS.getName(),rowkey);

    }

    public Map<String,Result> getAllFans(String colName, String starid) throws IOException {
        Map<String, Result> mapdata = new HashMap<String, Result>();
        Get get = new Get(Bytes.toBytes(starid));
        get.addFamily(Bytes.toBytes(colName));
        Result data = HbaseWeiBoUtils.getData(NameTable.TABLE_RELATIONS.getName(), get);
        mapdata.put(starid,data);
        return mapdata;
    }
}
