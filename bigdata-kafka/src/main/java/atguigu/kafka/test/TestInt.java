package atguigu.kafka.test;

/**
 * @author zxfcode
 * @create 2018-10-21 21:41
 */
public class TestInt {
    public static void main(String[] args) {
        byte b = 10;
        byte bb = 10+10;
        byte bbb = (byte)( b+1);
        byte ba = b++;//一元运算不提升数据类型

        test(b);
    }
    public static void test(byte b){
        System.out.println("bbbb");
    }
    public static void test(short s){
        System.out.println("ssss");
    }
    public static void test(char c){
        System.out.println("ccccc");
    }
    public static void test(int i){
        System.out.println("iiiii");
    }
}
