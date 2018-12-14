package dao;

import hbase.context.ColumnFa;
import hbase.context.NameTable;
import hbase.util.HbaseWeiBoUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zxfcode
 * @create 2018-10-26 18:04
 */
public class ContentDao {
    /**
     * 增加数据
     * @param userid
     * @param time
     * @param content
     */
    public void putData(String userid, long time, String content) {
        //用户id加上发布的时间戳来作为微博数据的rowkey
        byte[] rowkey = getBytes(userid + "_" + (Long.MAX_VALUE - time));
        //列族
        byte[] cf = getBytes(ColumnFa.CF_INFO.getColName());
        //列名
        byte[] column = getBytes(ColumnFa.CF_CONTENT.getColName());
        //微博内容
        byte[] val = getBytes(content);
        //Put
        Put put = new Put(rowkey);
        put.addColumn(cf,column,val);
        //调用工具类完成添加数据
        try {
            HbaseWeiBoUtils.addData(NameTable.TABLE_WEIBO.getName(),put);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到粉丝的id
     * @param userid
     */
    public List<String> getFanId(String userid) {
        byte[] rowkey = getBytes(userid);
        byte[] cf = getBytes(ColumnFa.CF_FANS.getColName());

        Get get = new Get(rowkey);
        get.addFamily(cf);

        List<String> fansid = new ArrayList<String>();
        try {
            Result result = HbaseWeiBoUtils.getData(NameTable.TABLE_RELATIONS.getName(), get);
            for (Cell cell : result.rawCells()) {
                fansid.add(Bytes.toString(CellUtil.cloneQualifier(cell)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fansid;
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

    /**
     * 扫描出明星的前五条信息
     * @param starid
     * @return
     */
    //|是字符里面最大的  明星微博信息范围：1001_ < xxx < 1001_|
    public List<String> scanData(String starid) {
        //扫描处明星的所有数据
        Scan scan = new Scan();
        scan.setStartRow(Bytes.toBytes(starid + "_"));
        scan.setStopRow(Bytes.toBytes(starid + "|"));

        List<String> weiboRowkeys = new ArrayList<String>();

        try {
            ResultScanner scanners = HbaseWeiBoUtils.scanData(NameTable.TABLE_WEIBO.getName(), scan);
            for (Result result : scanners) {
                if(weiboRowkeys.size() == 5){
                    break;
                }else{
                    for (Cell cell : result.rawCells()) {
                        weiboRowkeys.add( Bytes.toString(CellUtil.cloneRow(cell)));
                    }
                }

            }
            scanners.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return weiboRowkeys;
    }

    public Result getUserCon(String tabname,String rowkey) {
        Result data = null;
        try {
            byte[] cf = getBytes(ColumnFa.CF_INFO.getColName());
            Get get = new Get(Bytes.toBytes(rowkey));
            get.addFamily(cf);
            data = HbaseWeiBoUtils.getData(tabname, get);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public List<String> getNoticData(String colFa, String souid, String marid) {
        List<String> list = new ArrayList<String>();
        Get get = new Get(Bytes.toBytes(souid));
        get.addColumn(Bytes.toBytes(colFa),Bytes.toBytes(marid));
        try {
            Result data = HbaseWeiBoUtils.getData(NameTable.TABLE_RELATIONS.getName(), get);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


    public Map<String,Result> getContent(List<String> rowkeys) {
        Map<String,Result> mapData = new HashMap<String, Result>();
        try {
            for (String rowkey : rowkeys) {
                Get get = new Get(Bytes.toBytes(rowkey));
                Result data = HbaseWeiBoUtils.getData(NameTable.TABLE_WEIBO.getName(), get);
                mapData.put(rowkey,data);
            }
            return mapData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteWeibo(String starid, String weiboid) throws IOException {
//        Delete delete = new Delete(Bytes.toBytes(starid));
//        delete.addColumn(Bytes.toBytes(ColumnFa.CF_INFO.getColName()),Bytes.toBytes(ColumnFa.CF_CONTENT.getColName()));
        HbaseWeiBoUtils.deleteData(NameTable.TABLE_WEIBO.getName(),starid);
    }
}
