package atguigu.kafka.lower;

import kafka.api.FetchRequest;
import kafka.api.FetchRequestBuilder;
import kafka.cluster.BrokerEndPoint;
import kafka.javaapi.*;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.javaapi.message.ByteBufferMessageSet;
import kafka.message.MessageAndOffset;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zxfcode
 * @create 2018-10-22 19:36
 */
public class ComsumerHelper {
    @SuppressWarnings("all")
    public static List<String> getDatas(String host,int port,String topic,int part,int offset) throws Exception {
        //使用低级API读取数据
        //获取kafka消费者
        SimpleConsumer consumer = new SimpleConsumer(host, port, 100000, 10 * 24, "leader");
        //获取kafka的元数据信息
        //主题元数据请求对象
        TopicMetadataRequest request = new TopicMetadataRequest(Arrays.asList(topic));
        //获取主题元数据响应
        TopicMetadataResponse response = consumer.send(request);
        //主副本
        BrokerEndPoint leader =  null;
        //响应中包含了主题的元数据信息
        leaderLabel:for (TopicMetadata topicMetadata : response.topicsMetadata()) {
            //主题名称
            String stopic = topicMetadata.topic();
            //判断是否是我们需要的主题数据
            if("six".equals(stopic)){
                //循环主题的所有分区信息
                for (PartitionMetadata partitionMetadata : topicMetadata.partitionsMetadata()) {
                    int partNum = partitionMetadata.partitionId();
                    if(partNum == 1){
                        leader = partitionMetadata.leader();
                        break leaderLabel;
                    }
                }
            }
        }
        if(leader == null){
            System.out.println("指定信息无法获取");
            return null;
        }else{
            List<String> dataList = new ArrayList<String>();
            //创建leader消费者，连接leader获取数据
            SimpleConsumer dataConsumer = new SimpleConsumer(leader.host(), leader.port(), 100000, 10 * 1024, "accessLeader");
            //System.out.println(leader.host()+","+leader.port());
            //构建抓取请求
            FetchRequest req = new FetchRequestBuilder().addFetch(
                    topic,part,offset,10*1024
            ).build();
            //获取抓取响应
            FetchResponse resp = dataConsumer.fetch(req);
            ByteBufferMessageSet ms = resp.messageSet(topic, part);
            for (MessageAndOffset m : ms) {
                //由于获取的数据为字节码，还需要转换为字符串，用于业务的操作
                ByteBuffer buff = m.message().payload();
                byte[] bs = new byte[buff.limit()];
                buff.get(bs);
                String val = new String(bs, "UTF-8");
                dataList.add(val);
            }
            return dataList;
        }

    }
}
