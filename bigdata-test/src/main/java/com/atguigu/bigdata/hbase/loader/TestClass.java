package com.atguigu.bigdata.hbase.loader;

import org.junit.Test;

import java.lang.annotation.ElementType;

/**
 * @author zxfcode
 * @create 2018-10-23 21:23
 */
public class TestClass {
    public static void main(String[] args) {
        try {
            Class c1 = "hello".getClass();
            Class c2 = String.class;
            Class c3 = Class.forName("java.lang.String");
            Class c4 = ClassLoader.getSystemClassLoader().loadClass("java.lang.String");
            System.out.println(c1 == c2);
            System.out.println(c1 == c3);
            System.out.println(c1 == c4);
            System.out.println(c2 == c3);
            System.out.println(c2 == c4);
            System.out.println(c3 == c4);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test(){
        Class c1 = int.class;
        Class c2 = void.class;
        Class c3 = Comparable.class;
        Class c4 = Class.class;
        Class c5 = Object.class;

        Class c6 = int[].class;//只要元素类型与维度一样，就是同一个Class
        int[] a = new int[10];
        int[] b = new int[100];
        Class c7 = a.getClass();
        Class c8 = b.getClass();
        System.out.println(c6 == c7);
        System.out.println(c6 == c8);

        Class c9 = String[].class;
        Class c10 = int[][].class;
        System.out.println(c6 == c9);
        System.out.println(c6 == c10);

        Class c11 = Override.class;
        Class c12 = ElementType.class;

    }
}
