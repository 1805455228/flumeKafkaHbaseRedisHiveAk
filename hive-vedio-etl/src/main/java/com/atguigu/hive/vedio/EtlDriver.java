package com.atguigu.hive.vedio;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;

/**
 * @author zxfcode
 * @create 2018-11-08 15:30
 */
public class EtlDriver implements Tool {
    Configuration con = null;
    public int run(String[] args) throws Exception {
        con=this.getConf();
        con.set("inpath",args[0]);
        con.set("outpath",args[1]);
        //
        Job job = Job.getInstance(con);
        job.setJarByClass(EtlDriver.class);
        job.setMapperClass(EtlMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);
        //只有map，此时设置reduce个数为0
        job.setNumReduceTasks(0);
        //设置输入路径
        this.initInputFormat(job);
        //设置输出路径
        this.initOutputFormat(job);
        boolean b = job.waitForCompletion(true);
        return b?0:1;
    }

    private void initOutputFormat(Job job) throws IOException {
        Configuration confi = job.getConfiguration();
        String outpath = confi.get("outpath");
        FileSystem fs = FileSystem.get(confi);
        Path outPath = new Path(outpath);
        if(fs.exists(outPath)){
            fs.delete(outPath,true);
        }
        FileOutputFormat.setOutputPath(job,outPath);
    }

    private void initInputFormat(Job job) throws IOException {
        Configuration confi = job.getConfiguration();
        String inpath = confi.get("inpath");
        FileSystem fs = FileSystem.get(confi);
        Path inPath = new Path(inpath);
        if(fs.exists(inPath)){
            FileInputFormat.addInputPath(job,inPath);
        }else{
            throw new RuntimeException("没有这个输入路径："+inPath);
        }

    }

    public void setConf(Configuration configuration) {
        //将自己的con和配置里的con对应
        this.con = configuration;
    }

    public Configuration getConf() {

        return this.con;
    }

    public static void main(String[] args) {
        try {
            int run = ToolRunner.run(new EtlDriver(), args);
            if(run==0){
                System.out.println("success");
            }else{
                System.out.println("fail");
            }
            System.exit(run);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(1);
        }
    }
}
