package atguigu.kafka.producers;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

/**
 * @author zxfcode
 * @create 2018-10-21 20:56
 */
public class ProducersConsumer {
    public static void main(String[] args) {
        Properties props2 = new Properties();
        props2.put("bootstrap.servers", "hadoop102:9092");
        props2.put("acks", "-1");
        props2.put("retries", 0);
        props2.put("batch.size", 16384);
        props2.put("linger.ms", 1);
        props2.put("buffer.memory", 33554432);
        props2.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props2.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        
        Producer<String, String> producer = new KafkaProducer<String,String>(props2);
        for (int i = 0; i < 10; i++){
            //封装数据
            //回调函数可以获得发送数据的相关信息，比如发送到哪个分区，数据的偏移量
            ProducerRecord<String, String> record = new ProducerRecord<String, String>("six", Integer.toString(i),Integer.toString(i));
            //加get()就是同步发送
            producer.send(record, new Callback() {
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if(metadata != null){
                        System.out.println(metadata.topic()+","+metadata.partition()+","+metadata.offset());
                    }
                }
            });
        }

        producer.close();
    }
}
