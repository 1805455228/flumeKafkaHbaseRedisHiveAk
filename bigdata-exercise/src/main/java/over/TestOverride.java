package over;

/**
 * @author zxfcode
 * @create 2018-10-20 10:43
 */
public class TestOverride {
    public static void main(String[] args) {
        byte bb = 10;
        //byte aa = bb+10;
        byte cc = bb++;//一元运算符不提升数据类型（byte）(bb+1)
        test(bb);
    }
    public static void test(byte bbb){

    }
}
