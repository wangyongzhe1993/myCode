package disruptor;

/**
 * Created by wyz on 2018/1/26.
 */
public class TEvent {
    private long value;

    public TEvent() {
        System.out.println();
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
