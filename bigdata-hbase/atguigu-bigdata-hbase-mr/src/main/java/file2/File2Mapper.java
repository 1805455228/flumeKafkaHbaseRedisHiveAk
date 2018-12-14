package file2;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author zxfcode
 * @create 2018-10-24 19:10
 */
public class File2Mapper extends Mapper<LongWritable,Text,ImmutableBytesWritable,Put> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        if(line != null && !"".equals(line.trim())){
            String[] datas = line.split("\t");
            byte[] bytes = Bytes.toBytes(datas[0]);
            byte[] family = Bytes.toBytes("info");
            byte[] columnName = Bytes.toBytes("name");
            byte[] columnColor = Bytes.toBytes("color");
            byte[] valName = Bytes.toBytes(datas[1]);
            byte[] valColor = Bytes.toBytes(datas[2]);
            ImmutableBytesWritable rowkey = new ImmutableBytesWritable(bytes);
            Put put = new Put(bytes);
            put.addColumn(family,columnName,valName);
            put.addColumn(family,columnColor,valColor);
            context.write(rowkey,put);
        }

    }
}
