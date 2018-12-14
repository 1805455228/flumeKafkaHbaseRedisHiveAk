package atguigu.kafka.stream;

/**
 * @author zxfcode
 * @create 2018-10-22 18:51
 */
public class TransferStream {
        //implements Processor<byte[],byte[]>{
//    public static void main(String[] args) {
//        String fromTopic = "six";
//        String toTopic = "five";
//        TopologyBuilder builder = new TopologyBuilder();
//        builder.addSource("SOURCE",fromTopic);
//        builder.addProcessor("PROCESSER", new ProcessorSupplier() {
//            public Processor get() {
//                return new TransferStream();
//            }
//        },"SOURCE");
//        builder.addSink("SINK",toTopic,"PROCESSER");
//        Properties prop = new Properties();
//        prop.put(StreamsConfig.APPLICATION_ID_CONFIG, "logFilter");
//        prop.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop102:9092");
//
//        // 创建kafka stream
//        KafkaStreams streams = new KafkaStreams(builder, prop);
//        streams.start();
//
//    }
//    private ProcessorContext context;
//
//    public void init(ProcessorContext processorContext) {
//        this.context = processorContext;
//    }
//
//    public void process(byte[] key,byte[] value) {
//
//        try {
//            //获取数据value
//            String val = new String(value,"utf-8");
//            // 将特殊内容进行转换
//            val = val.replaceAll(">>>","");
//            //通过上下文对象（环境对象）发送到目的地
//            context.forward(key,val.getBytes());
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void punctuate(long l) {
//
//    }
//
//    public void close() {
//
//    }
}
