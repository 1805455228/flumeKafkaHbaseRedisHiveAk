package dao;

import hbase.context.ColumnFa;
import hbase.context.NameTable;
import hbase.util.HbaseWeiBoUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List; /**
 * @author zxfcode
 * @create 2018-10-26 12:38
 */
public class IboxDao {
    /**
     * 给粉丝收件箱发送数据
     * @param fanIds
     * @param userid
     * @param weiboid
     */
    public void putData(List<String> fanIds, String userid, String weiboid) {
        List<Put> puts = new ArrayList<Put>();
        for (String fanId : fanIds) {
            byte[] rowkey = getBytes(fanId);
            byte[] cf = getBytes(ColumnFa.CF_INFO.getColName());
            byte[] column = getBytes(userid);
            byte[] val = getBytes(weiboid);

            Put put = new Put(rowkey);
            put.addColumn(cf,column,val);
            puts.add(put);
        }

        try {
            HbaseWeiBoUtils.addDatas(NameTable.TABLE_INBOX.getName(),puts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将star微博加入粉丝收件箱中
     * @param fanid
     * @param starid
     * @param weiboRowkeys
     */
    public void putDatas(String fanid, String starid, List<String> weiboRowkeys) {
        List<Put> puts = new ArrayList<Put>();
        try {
            for (String weiboRowkey : weiboRowkeys) {
                byte[] rowkey = getBytes(fanid);
                byte[] cf = getBytes(ColumnFa.CF_INFO.getColName());
                byte[] collumn = getBytes(starid);
                byte[] val = getBytes(weiboRowkey);

                Put put = new Put(rowkey);

                //保存数据时：t1 = long.max_value-time
                //取数据时：long.max_value-t1===>long.max_value-(long.max_value - time) = time
                long ts = Long.MAX_VALUE - Long.valueOf(weiboRowkey.split("_")[1]);
                put.addColumn(cf,collumn,ts, val);

                puts.add(put);
                HbaseWeiBoUtils.addData(NameTable.TABLE_INBOX.getName(),put);
            }
            //HbaseWeiBoUtils.addDatas(NameTable.TABLE_INBOX.getName(),puts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 将bytes工具获取信息封装一下
     * @param stringname
     * @return
     */
    public byte[] getBytes(String stringname){
        byte[] bytes = Bytes.toBytes(stringname);
        return bytes;
    }

    //粉丝得到收件箱中指定明星的微博
    public Result getColumnData(String tabname, String rowKey, String family, String column) {
        Result colData = null;
        try {
            colData = HbaseWeiBoUtils.getColData(tabname, rowKey, family, column);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return colData;
    }

    public void deleteStarInfo(String fanid, String starid) throws IOException {
        Delete delete = new Delete(Bytes.toBytes(fanid));
        delete.addColumn(Bytes.toBytes(ColumnFa.CF_INFO.getColName()),Bytes.toBytes(starid));
        HbaseWeiBoUtils.deleteDatas(NameTable.TABLE_INBOX.getName(),delete);
    }

    public Result getData(String rowKey, String column) {
        try {
            Get get = new Get(Bytes.toBytes(rowKey));
            get.setMaxVersions(5);
            get.addColumn(Bytes.toBytes(ColumnFa.CF_INFO.getColName()),Bytes.toBytes(column));
            return HbaseWeiBoUtils.getData(NameTable.TABLE_INBOX.getName(), get);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteData(String fanid, String starid) {
    }
}
