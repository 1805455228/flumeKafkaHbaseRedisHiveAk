package fileio;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author zxfcode
 * @create 2018-10-19 9:29
 */
public class ReadFirstBlock {
    @Test
    public void getFileFromHdfs1() throws IOException, URISyntaxException, InterruptedException {
        Configuration con = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:9000"), con, "atguigu");
        FSDataInputStream fis = fs.open(new Path("/gulivideo/4.txt"));
        FileOutputStream fos = new FileOutputStream(new File("d:/zxf/hadoop/getHdfs/fourTxt.part1"));
        byte[] buf = new byte[1024];
        for(int i = 0;i<1024*128;i++){
            fis.read(buf);
            fos.write(buf);
        }
        IOUtils.closeStream(fis);
        IOUtils.closeStream(fos);
        fs.close();
    }
    @Test
    public void getFileFromHdfs2() throws URISyntaxException, IOException, InterruptedException {
        Configuration con = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:9000"), con, "atguigu");
        FSDataInputStream fis = fs.open(new Path("/gulivideo/4.txt"));
        fis.seek(1024*1024*128);
        FileOutputStream fos = new FileOutputStream(new File("d:/zxf/hadoop/getHdfs/fourTxt.part2"));
        IOUtils.copyBytes(fis,fos,con);
        IOUtils.closeStream(fis);
        IOUtils.closeStream(fos);
    }
}
