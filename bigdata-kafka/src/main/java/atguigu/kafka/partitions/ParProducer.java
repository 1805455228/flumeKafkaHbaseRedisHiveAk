package atguigu.kafka.partitions;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

/**
 * @author zxfcode
 * @create 2018-10-21 21:51
 */
public class ParProducer {
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
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //指定分区，当有分区的业务逻辑时，增加这个类例如：张三的数据放到张三文件夹下
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,"atguigu.kafka.partitions.ProPartition");
        //构建生产者对象，使用kafka javaapi
        Producer<String, String> producer = new KafkaProducer<String,String>(props);
        for (int i = 0; i < 10; i++) {
            //把数据转换成producerRecord对象后发送,添加get()后生产者生产的是有序的
            //保证数据有顺序，这里可以加分区号
            ProducerRecord<String, String> parrecord = new ProducerRecord<String, String>("six", 1,Integer.toString(i), Integer.toString(i));
            producer.send(parrecord, new Callback() {
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    System.out.println(metadata.topic()+","+metadata.partition()+","+metadata.offset());
                }
            });
        }
        //关闭资源
        producer.close();
    }
}
