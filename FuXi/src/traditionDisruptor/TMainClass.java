package traditionDisruptor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 *
 * Created by wyz on 2018/1/26.
 * 消息队列 线程间数据共享
 * 生产者模式：
 *      单生产者：ProducerType.SINGLE
 *      多生产者：ProducerType.MULTI
 * 消费者：一个EventHandler就是一个消费者
 *      平行：handleEventsWith(A,B,C);
 *      依赖：handleEventsWith(A).then(B).then(C);A先于B先于C执行
 * 等待策略：
 *      BlockingWaitStrategy:   最慢的等待策略，cpu负载低。适用于对吞吐量和延迟要求不高的程序。
 *                              使用ReentrantLock进行等待，取不到对应sequence进入await,等待生产者向RingBuffer中添加event后唤醒再取
 *      SleepingWaitStrategy:   低延迟策略，cpu负载与性能平衡较好，在静默期后可能出现延迟峰值。适用于对延迟要求不高的程序
 *                              先自旋，再yield，再parkNanos
 *
 *      YieldingWaitStrategy：  低延迟策略，cpu负载较高。无较明显cpu峰值。需要handler数量小于逻辑cpu核心数量（一个消费者能满载一个cpu）
 *                              采用死循环的方式进行获取，降低延迟。先执行100次获取，再获取不到进入yield死循环。
 *      BusySpinWaitStrategy:   低延迟策略，最低，耗尽cpu资源，无cpu峰值波动，必须把消费者线程绑定在指定的cpu核心上才能获得最好效果
 *                              完完全全的死循环。
 *  调优：
 *      单写模式，消费者事件回收，减少事件对象的生命周期，选择合适的等待策略。
 *
 *
 *  底层实现：
 *      Disruptor：
 *          RingBuffer
 *          Executor
 *          ConsumerRepository
 *          started
 *          ExceptionHandler
 *
 *      RingBuffer：有头无尾的环形数据结构
 *                  属性：
 *                      bufferSize：     ringbuffer容量大小，必须是2的指数
 *                      indexMask：      下标掩码,运算技巧： m % 2n = m&(2n-1)
 *                      EventFactory：   事件工厂,用来初始化填充entries。
 *                      entries：        实际存储事件的数组,indexMask & sequence.value即可取到事件的下标,前后各有可填充满整行缓存行个的元素，
 *                      WaitStrategy：   等待策略,负责协调消费者从生产者获取事件的策略。
 *                      Sequence：       基础槽对象,只有一个value属性有用，代表ringbuffer的环形数据结构的下标，使用15个long型填充，避免伪共享.
 *                      Sequencer：      生产者对象,负责事件的获取与发布，向消费者提供当前生产信息。
 *                          cursor：         游标对象,是一个sequence，表示当前生产者生产到了哪个位置，生产者更新
 *                          gatingSequences: 控制sequence，数组，表示消费者消费到了哪（多消费之表示最后一个handler消费到哪）,
 *                                           由消费者更新,更新使用原子更新(AtomicReferenceFieldUpdater)
 *                   方法：
 *                      next(n);这里有个缓存值，记录的上次publis时的最大的可以生产的位置，如果要取的位置大于了这个缓存或者这个缓存大于了上次生产的位置（也就是走过了一圈）
 *                              那么，sequence会进入死循环来唤醒消费者来消费直至取到需要的位置(parkNanos)
 *      ConsumerRepository:消费者仓库
 *              ConsumerInfo
 *                  SequenceBarrier     获取事件的实际方法,协调生产者，调用WaitStrategy的wait方法来获取事件
 *                  BatchEventProcessor 消费者线程，调用SequenceBarrier的wait方法来获取事件消费 dependentSequence完成消费者依赖
 *                  EventHandler        实际处理事件的对象，onEvent方法
 */
public class TMainClass {
    public static void main(String[] args) {
        Executor executor = Executors.newCachedThreadPool();

        TEventFactory eventFactory = new TEventFactory();

        Disruptor<TEvent> disruptor = new Disruptor<>(eventFactory, 16, executor, ProducerType.SINGLE, new BusySpinWaitStrategy());

        TEventHandler eventHandler1 = new TEventHandler("1");
        TEventHandler eventHandler2 = new TEventHandler("2");
        TEventHandler eventHandler3 = new TEventHandler("3");
        TEventHandler eventHandler4 = new TEventHandler("4");

//        disruptor.handleEventsWith(eventHandler1).then(eventHandler2).then(eventHandler3).then(eventHandler4);
//        disruptor.handleEventsWith(eventHandler1).then(eventHandler2).then(eventHandler3);
        disruptor.handleEventsWith(eventHandler1).then(eventHandler2);
        disruptor.handleEventsWith(eventHandler1);

        disruptor.start();

        RingBuffer<TEvent> ringBuffer = disruptor.getRingBuffer();

//        for (int i = 0; i < 100; i++) {
//            long sequence = ringBuffer.next();
//            try {
//                TEvent tEvent = ringBuffer.get(sequence);
//                tEvent.setValue(i);
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                ringBuffer.publish(sequence);
//            }
//        }
    }
}
