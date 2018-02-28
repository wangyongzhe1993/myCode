package disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by wyz on 2018/1/9.
 */
public class MainClass2 {
    public static void handleEvent(MyEvent myEvent, long sequence, boolean endOfBatch) {
        System.out.println(myEvent);
    }

    public static void translate(MyEvent myEvent, long sequence, long value) {
        myEvent.setValue(value);
    }

    public static void main(String args[]) throws Exception {
        Executor executor = Executors.newCachedThreadPool();

        int buffSize = 1024;

        Disruptor<MyEvent> disruptor = new Disruptor<>(MyEvent::new, buffSize, executor);

        disruptor.handleEventsWith(MainClass2::handleEvent);

        disruptor.start();

        RingBuffer<MyEvent> ringBuffer = disruptor.getRingBuffer();
        long i = 0;
        while (true) {
            ringBuffer.publishEvent(MainClass2::translate, i++);
            Thread.sleep(100);
            //1010
            //1100
        }
    }
}
