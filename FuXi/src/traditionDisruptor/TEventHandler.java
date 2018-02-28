package traditionDisruptor;

import com.lmax.disruptor.EventHandler;

/**
 * Created by wyz on 2018/1/26.
 */
public class TEventHandler implements EventHandler<TEvent> {
    private String name;

    public TEventHandler(String name) {
        this.name = name;
    }

    @Override
    public void onEvent(TEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println(name + " handler:" + event.getValue());
    }
}
