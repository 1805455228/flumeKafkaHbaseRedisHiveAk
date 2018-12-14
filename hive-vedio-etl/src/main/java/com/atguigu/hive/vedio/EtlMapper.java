package com.atguigu.hive.vedio;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author zxfcode
 * @create 2018-11-08 15:30
 */
public class EtlMapper extends Mapper<LongWritable,Text,Text,NullWritable>{
    Text k = new Text();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //获取一行数据
        String line = value.toString();
        //逻辑业务处理
        String str = EtlUtils.strEtl(line);
        if(StringUtils.isBlank(str)) return;
        //提交业务
        k.set(str);
        context.write(k, NullWritable.get());


    }
}
