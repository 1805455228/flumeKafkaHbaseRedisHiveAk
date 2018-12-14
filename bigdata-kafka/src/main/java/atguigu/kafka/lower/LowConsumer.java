package atguigu.kafka.lower;

import java.util.List;

/**
 * @author zxfcode
 * @create 2018-10-22 18:50
 */
public class LowConsumer {
    @SuppressWarnings("all")
    public static void main(String[] args) throws Exception {
        List<String> datas = ComsumerHelper.getDatas("hadoop102", 9092, "six", 0, 100);
        System.out.println(datas);
        if(datas == null){
            System.out.println("没有获取到数据");
        }else{
            for (String data : datas) {
                System.out.println(data);
            }
        }
    }
}
