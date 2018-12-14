package source;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDrivenSource;
import org.apache.flume.conf.Configurable;
import org.apache.flume.event.EventBuilder;
import org.apache.flume.source.AbstractSource;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author zxfcode
 * @create 2018-10-16 23:59
 */
public class RandomNumSource extends AbstractSource implements EventDrivenSource, Configurable{
    private int numSize=0;
    final List<Event> eventList = new ArrayList<Event>();
    public void configure(Context context) {
       numSize=context.getInteger("numSize");
    }

    @Override
    public synchronized void start() {

            try {
                for(int i = 0;i<numSize;i++){
                    String number = ""+new Random().nextInt(10);
                    eventList.add(EventBuilder.withBody(number.getBytes("UTF-8")));
                    getChannelProcessor().processEventBatch(eventList);
            }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
        }
        super.start();
    }
}
