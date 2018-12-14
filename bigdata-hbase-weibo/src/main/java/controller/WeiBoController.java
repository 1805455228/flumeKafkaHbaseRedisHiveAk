package controller;

import hbase.context.ColumnFa;
import hbase.context.NameTable;
import service.WeiBoService;

/**
 * @author zxfcode
 * @create 2018-10-26 15:13
 */
public class WeiBoController {
    private WeiBoService weiBoService= new WeiBoService();
    //初始化
    public void init(){
        //创建命名空间
        weiBoService.createNamespace(NameTable.NAMESPACE_ATGUIGU.getName());
        //创建表
        // weibo:info:content
        weiBoService.createTable(NameTable.TABLE_WEIBO.getName(), ColumnFa.CF_INFO.getColName());
        // relation:attends,fans:userid
        weiBoService.createTable(NameTable.TABLE_RELATIONS.getName(),ColumnFa.CF_ATTENDS.getColName(),ColumnFa.CF_FANS.getColName());
        //inbox:info:userid1
        weiBoService.createTable(NameTable.TABLE_INBOX.getName(),5,ColumnFa.CF_INFO.getColName());
    }
    /**
     * 发布微博
     */
    public void publishWeibo(String userid,long time,String content){
        weiBoService.publishWeibo(userid,time,content);
    }

    /**
     * 关注微博
     * @param fanid
     * @param starid
     */
    public void attendUser(String fanid,String starid){
        weiBoService.attendUser(fanid,starid);
    }

    public void seeUser(String rowKey,String column){
        weiBoService.getColumnData(rowKey,column);

    }

    // A 取消关注 B
    public void cancelAttend(String fanid,String starid){
        weiBoService.cancelStar(fanid,starid);

    }

    // 明星删除微博

    public void deleteWeibo(String starid,String weiboid){
        weiBoService.deleteWeibo(starid,weiboid);

    }
}
