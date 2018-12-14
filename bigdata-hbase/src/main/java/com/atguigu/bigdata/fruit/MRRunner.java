package com.atguigu.bigdata.fruit;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * @author zxfcode
 * @create 2018-10-24 14:32
 */
public class MRRunner {
    public static void main(String[] args) throws Exception {
        //运行mapreduce
        ToolRunner.run(new ToolDruit(),args);
    }
}
