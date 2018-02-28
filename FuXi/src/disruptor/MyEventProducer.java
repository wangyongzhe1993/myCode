package disruptor;

import com.lmax.disruptor.RingBuffer;

/**
 * Created by wyz on 2018/1/9.
 */
public class MyEventProducer {
    private RingBuffer<MyEvent> ringBuffer;

    public MyEventProducer(RingBuffer<MyEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(long value) {
        long sequence = ringBuffer.next();
        try {
            MyEvent myEvent = ringBuffer.get(sequence);
            myEvent.setValue(value);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
