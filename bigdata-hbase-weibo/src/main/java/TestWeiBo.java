import controller.WeiBoController;
import service.WeiBoService;

/**
 * @author zxfcode
 * @create 2018-10-26 12:32
 */
public class TestWeiBo {
    public static void main(String[] args) {
        WeiBoController weiBoController = new WeiBoController();
        //weiBoController.init();
        //1001发布数据
//        String userid = "1001";
//        long time = System.currentTimeMillis();
//        String content = "hello hbase xxxxxx !!!";
//        weiBoController.publishWeibo(userid,time,content);
//        time = System.currentTimeMillis();
//        content = "hello hbase table!!!";
//        weiBoController.publishWeibo(userid,time,content);
//        time = System.currentTimeMillis();
//        content = "hello hbase weibo!!!";
//        weiBoController.publishWeibo(userid,time,content);

//        //A关注B
//        String fanid = "1003";
//        String starid = "1001";
//        weiBoController.attendUser(fanid,starid);
        // A 查看 B 的微博
        weiBoController.seeUser("1003","1001");

        // A 取消关注 B cancelAttend
        //weiBoController.cancelAttend("1003","1001");

        // 明星删除微博
    }




}
