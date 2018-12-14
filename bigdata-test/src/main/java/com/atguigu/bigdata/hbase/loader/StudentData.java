package com.atguigu.bigdata.hbase.loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zxfcode
 * @create 2018-11-08 21:53
 */
public class StudentData {
    public static void main(String[] args) {
        ClassLoader classLoader;
        BufferedReader bufferedReader = null;

        List<Student> list = new ArrayList<>();
        try {
            //创建各种
            InputStream is = StudentData.class.getClassLoader().getResourceAsStream("student.txt");
            bufferedReader = new BufferedReader(new InputStreamReader(is,"utf-8"));
            String line = bufferedReader.readLine();

            while (line != null){
                //处理数据
                //把一行字符串整合成一个对象
                String[] values = line.split(",");
                int id  = Integer.parseInt(values[0]);
                String name = values[1];
                int grade = Integer.parseInt(values[2]);
                double score = Double.parseDouble(values[3]);
                list.add(new Student(id,name,grade,score));

                //System.out.println(line);
               // list.add()
                //继续读
                line = bufferedReader.readLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        for (Student student : list) {
            System.out.println(student);
        }
    }
}
