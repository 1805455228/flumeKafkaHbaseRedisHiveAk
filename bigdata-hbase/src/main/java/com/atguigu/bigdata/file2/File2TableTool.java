package com.atguigu.bigdata.file2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.JobStatus;
import org.apache.hadoop.util.Tool;

/**
 * @author zxfcode
 */
public class File2TableTool implements Tool {
    public int run(String[] strings) throws Exception {
        Job job = Job.getInstance();
        job.setJarByClass(File2TableTool.class);

        //Mapper
        job.setMapperClass(File2Mapper.class);

        //reducer


        boolean flg = job.waitForCompletion(true);
        if(flg){
            return JobStatus.State.SUCCEEDED.getValue();
        }else{
            return JobStatus.State.FAILED.getValue();
        }
    }

    public void setConf(Configuration configuration) {

    }

    public Configuration getConf() {
        return null;
    }
}
