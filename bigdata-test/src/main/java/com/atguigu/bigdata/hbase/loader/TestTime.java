package com.atguigu.bigdata.hbase.loader;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author zxfcode
 * @create 2018-11-08 20:20
 */
public class TestTime {

    @Test
    public void test(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        String f = sdf.format(d);
       // System.out.println(f);

        //localdate代表一个日期，
        //通过静态方法now()可以获得当前日期
        //他是内容不可改变的对象，所以任何对于日期对象的修改都会产生新对象
        //withXxxx(xx)可以在当前对昂基础上修改某属性，并产生新对象
        //getXxx(XX)可以获取某个属性值
        //plusXxx(int value)在当前日期对象的基础上加一个数量

        //of(int year,int month,int day)
        LocalDate date = LocalDate.now();
        System.out.println(date);

        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        System.out.println(year +"-" + month + "-" + day);

        //with不会修改对象属性，产生的是一个新的对象
        //会消耗内存但是安全
        LocalDate lDate1 = date.withYear(2018);
        LocalDate lDate2 = lDate1.withDayOfMonth(5);
        System.out.println(lDate1);
        System.out.println(lDate2);

        //链式
        LocalDate lDate3 = lDate1.withMonth(5).withDayOfMonth(10);
        System.out.println(lDate3);

        //在date基础上加五年
        LocalDate fiveAfter = date.plusYears(5);
        System.out.println(fiveAfter);

        //减5年
        LocalDate lDate4 = date.minusYears(5);
        System.out.println(lDate4);

        //1997,7,1
        LocalDate of = LocalDate.of(1997, 7, 1);
        System.out.println(of);

        //创建一个时间对象，获取当前时间
        //修改为生日，创建一个新日期对象  2009-5-3 20：08：22
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);

        LocalDateTime birth = now.withYear(2009).withMonth(5).withDayOfMonth(3).withHour(20).withMinute(8).withSecond(22);
        LocalDateTime of1 = LocalDateTime.of(2009, 5, 3, 10, 8, 22);

        //SimpleDateFormat老版本不能和现在的版本互相转换
        //新格式化器可以和现在的互相转换
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(dtf);
    }
}
