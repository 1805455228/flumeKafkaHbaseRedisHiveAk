package atguigu.kafka.test;

/**
 * @author zxfcode
 * @create 2018-10-21 21:36
 */
public class Test {
    public static void main(String[] args) {
        AA aa = new BB();
        test(aa);

    }
    public static void test(AA aa){
        System.out.println("aaaaa");
    }
    public static void test(BB bb){
        System.out.println("bbbb");
    }
}
class AA{

}
class BB extends AA{

}