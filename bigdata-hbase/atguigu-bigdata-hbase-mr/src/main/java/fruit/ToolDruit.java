package fruit;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.JobStatus;
import org.apache.hadoop.util.Tool;

/**
 * @author zxfcode
 * @create 2018-10-24 15:07
 */
public class ToolDruit implements Tool {
    public int run(String[] strings) throws Exception {

        Job job = Job.getInstance();
        job.setJarByClass(ToolDruit.class);

        //扫描表
        Scan scan = new Scan();
        //mapper
        //hbase对job的配置进行了封装
        TableMapReduceUtil.initTableMapperJob(
                "fruit",
                scan,
                FruitMapper.class,
                ImmutableBytesWritable.class,
                Put.class,
                job
        );
        //reducer
        TableMapReduceUtil.initTableReducerJob("fruit_mr",FruitReducer.class,job);

        return job.waitForCompletion(true)? JobStatus.State.SUCCEEDED.getValue() : JobStatus.State.FAILED.getValue();
    }

    public void setConf(Configuration configuration) {

    }

    public Configuration getConf() {
        return null;
    }
}
