package com.atguigu.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * @author zxfcode
 * @create 2018-11-02 21:04
 */
public class SecondKillServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String prodid = req.getParameter("prodid");
        //得到用户id
        String userid = "" + new Random().nextInt(10000);
        Jedis jedis = null;
        try {
            JedisPool pool = JedisPoolUtil.getJedisPoolInstance();
            jedis = pool.getResource();
            //jedis = new Jedis("hadoop104", 6379);
            String memberKey = "sk:" + prodid + ":users";
            //当前用户是否秒杀过
            Boolean flg = jedis.sismember(memberKey, userid);

            if(flg){
                //秒杀过，显示错误
                System.out.println("已经参加过秒杀！");
            }else{
                //没有秒杀过
                //获取商品库存
                String qtKey = "sk:"+prodid+":qt";
                String pString = jedis.get(qtKey);
                if(pString == null){
                    System.out.println("商品不存在！");
                }else{
                    Integer pcnt = Integer.valueOf(pString);
                    //判断库存大小
                    if(pcnt<=0){
                        //小于0--false
                        resp.getWriter().print("false");
                        System.out.println("秒杀失败");
                    }else{
//                        //大于零，库存减一
//                        jedis.decr(qtKey);
//                        //增加秒杀成功者清单
//                        jedis.sadd(memberKey,userid);
//                        System.out.println("秒杀成功");
                        Transaction multi = jedis.multi();
                        multi.decr(qtKey);
                        multi.sadd(memberKey,userid);
                        List<Object> result = multi.exec();
                        if(result == null || result.isEmpty()){
                            System.out.println("操作失败");
                        }else{
                            System.out.println("秒杀成功");
                        }
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }
}
