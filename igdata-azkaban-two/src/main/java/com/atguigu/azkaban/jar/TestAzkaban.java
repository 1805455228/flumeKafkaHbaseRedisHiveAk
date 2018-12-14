package com.atguigu.azkaban.jar;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author zxfcode
 * @create 2018-10-29 13:01
 */
public class TestAzkaban {
    public void run() throws IOException {
        FileOutputStream fos = new FileOutputStream("/opt/module/azkaban/output.txt");
        fos.write("this is a java azkaban".getBytes());
        fos.close();
    }

    public static void main(String[] args) throws IOException {
        TestAzkaban testAzkaban = new TestAzkaban();
        testAzkaban.run();
    }
}
