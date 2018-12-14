package source;

import com.sun.org.apache.bcel.internal.generic.NEW;
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
 * @create 2018-10-17 8:53
 */
public class MyNumSource extends AbstractSource implements EventDrivenSource, Configurable {
    private int numSize = 0;
    List<Event> eventList= new ArrayList<Event>();
    public void configure(Context context) {
        numSize = context.getInteger("numSize");
    }

    @Override
    public synchronized void start() {
        try {
            for(int i = 0;i<numSize;i++){
                String numLine = "" + new Random().nextInt(10);
                eventList.add(EventBuilder.withBody(numLine.getBytes("UTF-8")));
            }
            getChannelProcessor().processEventBatch(eventList );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        super.start();
    }
}
