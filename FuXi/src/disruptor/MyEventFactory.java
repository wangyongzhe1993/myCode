package disruptor;

/**
 * Created by wyz on 2018/1/9.
 */
public class MyEventFactory implements com.lmax.disruptor.EventFactory<MyEvent> {
    @Override
    public MyEvent newInstance() {
        return new MyEvent();
    }
}
