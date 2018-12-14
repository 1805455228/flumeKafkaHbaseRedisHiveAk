package sink;

import org.apache.flume.*;
import org.apache.flume.conf.Configurable;
import org.apache.flume.event.EventHelper;
import org.apache.flume.sink.AbstractSink;

/**
 * @author zxfcode
 * @create 2018-10-17 8:54
 */
public class MyNumSink  extends AbstractSink implements Configurable {

    public Status process() throws EventDeliveryException {
        System.out.println("sink.....process......");
        Status result = Status.READY;
        Channel channel = getChannel();
        Transaction transaction = channel.getTransaction();
        Event event = null;

        try {
            transaction.begin();
            event = channel.take();

            if (event != null) {
                System.out.println("Event:"+new String(event.getBody(),"UTF-8"));
            } else {
                // No event found, request back-off semantics from the sink runner
                result = Status.BACKOFF;
            }
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            throw new EventDeliveryException("Failed to log event: " + event, ex);
        } finally {
            transaction.close();
        }

        return result;
    }

    public void configure(Context context) {

    }
}
