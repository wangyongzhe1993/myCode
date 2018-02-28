package disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * Created by wyz on 2018/1/9.
 */
public class MyEnentHandler implements EventHandler<MyEvent> {
    @Override
    public void onEvent(MyEvent myEvent, long l, boolean b) throws Exception {
        System.out.println("value:" + myEvent.getValue());
    }
}
