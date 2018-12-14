package atguigu.kafka.test;

/**
 * @author zxfcode
 * @create 2018-10-21 21:57
 */
public class TestDuo {
    public static void main(String[] args) {
        //动态绑定技术
        E e = new F();
        System.out.println(e.getResult());

    }
}
class E{
    public int i = 10;
    public int getResult(){
        return getI()+10;
    }
    public int getI(){
        return i;
    }
}
class F extends E{
    public int i = 20;
    public int getResult(){
        return i+20;
    }
    public int getI(){
        return i;
    }
}