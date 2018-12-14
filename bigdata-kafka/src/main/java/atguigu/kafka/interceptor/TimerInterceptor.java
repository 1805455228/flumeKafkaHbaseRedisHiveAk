package atguigu.kafka.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.internals.ProducerInterceptors;

import java.util.Map;

/**
 * @author zxfcode
 * @create 2018-10-21 23:32
 */
public class TimerInterceptor implements ProducerInterceptor  {
    public ProducerRecord onSend(ProducerRecord record) {
        String newValue = System.currentTimeMillis() + "-" + record.value();
        return new ProducerRecord(record.topic(),record.partition(),record.timestamp(),record.key(),newValue);
    }

    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {

    }

    public void close() {

    }

    public void configure(Map<String, ?> configs) {

    }
}
