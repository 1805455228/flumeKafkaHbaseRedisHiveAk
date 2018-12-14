package atguigu.kafka.interceptor;

import org.apache.kafka.clients.producer.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author zxfcode
 * @create 2018-10-21 23:38
 */
public class TimerProducer {
    public static void main(String[] args) {
        //获取属性值
        Properties props = new Properties();
        //设置主机和端口号
        props.put("bootstrap.servers", "hadoop102:9092");
        //ack机制
        props.put("acks", "-1");
        //重试
        props.put("retries", 0);
        //批数据大小
        props.put("batch.size", 16384);
        //延迟提交
        props.put("linger.ms", 1);
        //buffer.memory缓存时长
        props.put("buffer.memory", 33554432);
        //用byte[]序列化
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        List<String> interceptors = new ArrayList<String>();
        interceptors.add("atguigu.kafka.interceptor.TimerInterceptor");
        interceptors.add("atguigu.kafka.interceptor.ACKInterceptor");
        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,interceptors);
        //构建生产者对象，使用kafka javaapi
        Producer<String, String> producer = new KafkaProducer<String,String>(props);
        for (int i = 0; i < 10; i++) {
            //把数据转换成producerRecord对象后发送,添加get()后生产者生产的是有序的
            ProducerRecord<String, String> records = new ProducerRecord<String, String>("six", Integer.toString(i), Integer.toString(i));
            producer.send(records, new Callback() {
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    System.out.println(metadata);
                }
            });
        }
        //关闭资源
        producer.close();
    }
}
