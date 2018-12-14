package com.atguigu.redis;


import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

/**
 * @author zxfcode
 * @create 2018-11-02 18:08
 */
public class SendCode extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //得到用户获取验证码的电话号码
        String phone_no = req.getParameter("phone_no");
        Jedis jedis = null;
        try {
            jedis = new Jedis("hadoop104",6379);
            //设置key
            String countKey = phone_no + ":count";
            String codeKey = phone_no + ":code";
            //在redis里面设置验证次数
            String cnt = jedis.get(countKey);
            if(cnt == null){
                //第一次请求验证码
                jedis.setex(countKey, 60 * 60 * 24, "1");
                //设计一个六位数的验证码
                StringBuilder code = new StringBuilder();
                for(int i = 0;i<6;i++){
                    code.append(new Random().nextInt(10));
                }
                //设置验证码
                jedis.setex(codeKey,2*60,code.toString());
                //返回验证码请求成功
                resp.getWriter().print("true");
            }else{
                if(Integer.valueOf(cnt) < 3){
                    //设计一个六位数的验证码
                    StringBuilder code = new StringBuilder();
                    for(int i = 0;i<6;i++){
                        code.append(new Random().nextInt(10));
                    }
                    //设置验证码
                    jedis.setex(codeKey,2*60,code.toString());
                    //验证码请求次数加一
                    jedis.incr(countKey);
                    //返回验证码请求成功
                    resp.getWriter().print("true");
                }else{
                    resp.getWriter().print("limit");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis!=null){
                jedis.close();
            }

        }
    }


}
