package fruit;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;

import java.io.IOException;

/**
 * @author zxfcode
 * @create 2018-10-24 14:33
 */
public class FruitMapper extends TableMapper<ImmutableBytesWritable,Put>{
    @Override
    protected void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {
        Put put = new Put(key.get());
        for (Cell cell : value.rawCells()) {
            put.addColumn(CellUtil.cloneFamily(cell),CellUtil.cloneQualifier(cell),CellUtil.cloneValue(cell));
        }
        context.write(key,put);
    }
}
