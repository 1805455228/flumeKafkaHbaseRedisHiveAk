package atguigu.kafka.test;

import java.lang.reflect.Field;

/**
 * @author zxfcode
 * @create 2018-10-22 19:17
 */
public class TestString {
    public static void main(String[] args) throws Exception {
        String s = "abc";
       // s = s.trim();
        //用反射来改变不可变串
        Class clazz = String.class;
        Field f = clazz.getDeclaredField("value");
        // String.java: private final char value[];
        //f是私有的，要先开放权限才可以得到
        f.setAccessible(true);
        char[] cs = (char[]) f.get(s);
        cs[1]='d';
        System.out.println(s);
    }
}
