/**
 * @author zxfcode
 * @create 2018-10-18 13:55
 */
public class StaticTest {
    public static void main(String[] args) {
        A a = new B();
        System.out.println(a.mString);
        System.out.println(a.getString());
    }
    public static class A{
        public String mString = "A";
        public String getString(){
            return mString;
        }
    }
    public static class B extends A{
        public String mString = "B";
        public String getString(){
            return mString;
        }
    }
}
