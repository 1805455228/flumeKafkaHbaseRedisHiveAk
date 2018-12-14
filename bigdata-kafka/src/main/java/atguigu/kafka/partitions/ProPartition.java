package atguigu.kafka.partitions;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * @author zxfcode
 * @create 2018-10-21 21:33
 */
public class ProPartition implements Partitioner{

    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        //return 0 就是往0分区发送东西
        return 0;

    }

    public void close() {

    }

    public void configure(Map<String, ?> configs) {

    }
}
