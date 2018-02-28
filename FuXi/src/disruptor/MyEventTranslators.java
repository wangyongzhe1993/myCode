package disruptor;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

/**
 * Created by wyz on 2018/1/9.
 */
public class MyEventTranslators {
    private final RingBuffer<MyEvent> ringBuffer;

    public MyEventTranslators(RingBuffer<MyEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    private static final EventTranslatorOneArg<MyEvent, Long> TRANSLATOR = new EventTranslatorOneArg<MyEvent, Long>() {
        @Override
        public void translateTo(MyEvent myEvent, long l, Long aLong) {
            myEvent.setValue(l);
        }
    };

    public void onData(long value) {
        ringBuffer.publishEvent(TRANSLATOR, value);
    }
}
