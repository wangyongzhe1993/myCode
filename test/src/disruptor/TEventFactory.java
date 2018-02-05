package disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * Created by wyz on 2018/1/26.
 */
public class TEventFactory implements EventFactory<TEvent> {
    @Override
    public TEvent newInstance() {
        return new TEvent();
    }
}
