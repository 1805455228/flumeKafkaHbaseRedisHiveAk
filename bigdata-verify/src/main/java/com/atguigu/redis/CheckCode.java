package com.atguigu.redis;


import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zxfcode
 * @create 2018-11-02 18:19
 */
public class CheckCode extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //得到前台传来的电话
        String phone_no = req.getParameter("phone_no");
        //得到传过来的验证码
        String verify_code = req.getParameter("verify_code");
        //codKEY
        String formCode = phone_no + ":code";
        //获取redis里面的验证码
        Jedis jedis = null;

        try {
            jedis = new Jedis("hadoop104", 6379);
            //获得redis里面的code
            String verCode = jedis.get(formCode);
            //没有找到验证码
            if(verCode == null){

            }else{
                if(verCode.equals(verify_code)){
                    //验证成功
                    resp.getWriter().print("true");
                }else{

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
