package file2;

import org.apache.hadoop.util.ToolRunner;

/**
 * @author zxfcode
 * @create 2018-10-24 19:06
 */
public class HBaseMapperReducer {
    public static void main(String[] args) throws Exception {
        ToolRunner.run(new File2TableTool(),args);
    }
}
