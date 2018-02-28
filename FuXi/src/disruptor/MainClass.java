package disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by wyz on 2018/1/9.
 */
public class MainClass {
    public static void main(String[] args) throws Exception {
        Executor executor = Executors.newCachedThreadPool();

        MyEventFactory eventFactory = new MyEventFactory();

        int buffSize = 1024;

        Disruptor<MyEvent> disruptor = new Disruptor<>(eventFactory, buffSize, executor);

        disruptor.handleEventsWith(new MyEnentHandler());

        disruptor.start();

        RingBuffer<MyEvent> ringBuffer = disruptor.getRingBuffer();


    }

}
