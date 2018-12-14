package service;

import dao.AdminDao;
import dao.ContentDao;
import dao.IboxDao;
import dao.RelationDao;
import hbase.context.ColumnFa;
import hbase.context.NameTable;
import hbase.util.HbaseWeiBoUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.*;

/**
 * @author zxfcode
 * @create 2018-10-26 15:13
 */
public class WeiBoService {
    //service
    private AdminDao adminDao = new AdminDao();
    private ContentDao contentDao = new ContentDao();
    private IboxDao iboxDao =new IboxDao();
    private RelationDao relationDao = new RelationDao();
    //创建命名空间
    public void createNamespace(String nameSapce) {
        try {
            adminDao.createNamespace(nameSapce);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                adminDao.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //创建一个表格版本号默认为1的表
    public  void createTable(String tabname,String... families) {
        createTable(tabname,1,families);
    }


    //创建表格
    public void createTable(String tabname,int versions, String... families) {
        try {
            if(adminDao.isTableExists(tabname)){
                adminDao.deleteTable(tabname);
            }
            adminDao.createTable(tabname,versions,families);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                adminDao.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发布微博
     * @param userid
     * @param time
     * @param content
     */
    public void publishWeibo(String userid, long time, String content) {
        //增加A微博的数据
        contentDao.putData(userid,time,content);

        //向A的所有粉丝的收件箱增加数据
        List<String> fanIds = contentDao.getFanId(userid);

        //像每一个粉丝的收件箱发送数据
        if(!fanIds.isEmpty()){
            iboxDao.putData(fanIds,userid,userid+"_"+(Long.MAX_VALUE-time));
        }
    }

    /**
     * 粉丝关注明星
     * @param fanid
     * @param starid
     *
     * ①粉丝关注明星后，以自己的id为rowkey，列族为attend，列命为start，列信息为明星id
     * ②明星的粉丝数量增加，同上
     */
    public void attendUser(String fanid, String starid) {
        //粉丝关注明星
        relationDao.putData(ColumnFa.CF_ATTENDS.getColName(),fanid,starid,starid);

        //明星增加粉丝
        relationDao.putData(ColumnFa.CF_FANS.getColName(),starid,fanid,fanid);

        //将star的微博加到粉丝的收件箱中-->1.查询star微博no.5
        List<String> weiboRowkeys = contentDao.scanData(starid);
        System.out.println("weibo num =" + weiboRowkeys.size());

        //2.将微博数据加到粉丝收件箱中
        iboxDao.putDatas(fanid,starid,weiboRowkeys);
    }

    /**
     * 粉丝查看明星微博
     * @param rowKey
     * @param column
     */
    //查看消息
    // A 查看 B 的微博
    // 1） 查询粉丝收件箱中指定明星的微博数据（rowkey）
    // 2） 从微博表中获取指定rowkey的数据
    // 3） 如果查询不到指定微博信息进行相应的提示
    public void getColumnData(String rowKey, String column) {
        try {
            Result result = iboxDao.getData(rowKey,column);
            List<String> rowkeys = new ArrayList<String>();
            for (Cell cell : result.rawCells()) {
                rowkeys.add(Bytes.toString(CellUtil.cloneValue(cell)));
            }
            Map<String,Result> resultMap = contentDao.getContent(rowkeys);
            Iterator<String> keyIter = resultMap.keySet().iterator();
            if(keyIter.hasNext()){
                String key = keyIter.next();
                Result r = resultMap.get(key);
                if(r != null){
                    for (Cell cell : r.rawCells()) {
                        System.out.println("信息为："+Bytes.toString(CellUtil.cloneValue(cell)));
                    }
                }else{
                    System.out.println("查询的信息不存在或已删除");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 粉丝取消关注
     * @param fanid
     * @param starid
     */
    public void cancelStar(String fanid, String starid) {
        try {
            // 1）将粉丝的关注列族中明星数据删除
            relationDao.removeAttends(ColumnFa.CF_ATTENDS.getColName(),fanid,starid);
            // 2）将明星的粉丝列族中删除当前粉丝
            relationDao.removeAttends(ColumnFa.CF_FANS.getColName(),fanid,starid);
            // 3）将粉丝收件箱中关于这个明星的微博信息全部删除
            iboxDao.deleteStarInfo(fanid,starid);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 明星删除微博
     * @param starid
     * @param weiboid
     */
    public void deleteWeibo(String starid, String weiboid) {
        try {
            // 1）将微博表中指定的数据删除
            contentDao.deleteWeibo(starid,weiboid);
            // 2）获取明星所有的粉丝数据
            Map<String,Result> mapFans =  relationDao.getAllFans(ColumnFa.CF_FANS.getColName(),starid);

            // 3）删除所有粉丝的收件箱中指定的微博数据
            Iterator<String> faniter = mapFans.keySet().iterator();
            if(faniter.hasNext()){
                String fanid = faniter.next();
                iboxDao.deleteData(fanid,starid);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
