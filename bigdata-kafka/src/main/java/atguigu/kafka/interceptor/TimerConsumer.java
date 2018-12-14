package atguigu.kafka.interceptor;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * @author zxfcode
 * @create 2018-10-22 18:33
 */
public class TimerConsumer {
    public static void main(String[] args) {
        //消费者配置信息
        Properties props = new Properties();
        //kafka集群信息
        props.put("bootstrap.servers", "hadoop102:9092");
        //消费者组
        props.put("group.id", "test234");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        //反序列化
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //创建消费者对象
        KafkaConsumer<String, String> consumer = new KafkaConsumer <String,String>(props);
        //订阅主题
        consumer.subscribe(Arrays.asList(new String[]{"six"}));
        //消费死循环
        while (true) {
            //读取数据（多条）
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records)
                System.out.println(record);}
    }
}
