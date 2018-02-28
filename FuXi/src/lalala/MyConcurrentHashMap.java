package lalala;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 利用CAS+Synchronized来保证并发更新的安全，底层采用数组+链表+红黑树的存储结构
 * 1.重要属性
 * node    hashMap元素基本单位, key value hash next【value和next使用volatile修饰】
 * table[] 最终用来存储node节点的数据结构
 * 具体例子：
 * initTable cas保证初始化线程安全
 *      sizeCtl < 0
 *      yield
 *      else
 *      cas sizeCtl()
 * putVal cas+synchronize
 * 直接插入cas
 * 桶链表尾部插入synchronize 锁链表的头元素，避免成环
 * Created by wyz on 2018/2/26.
 */
public class MyConcurrentHashMap {
    public static int a = 1;
    public static void main(String[] args) {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        new Thread("1") {
            @Override
            public void run() {
                concurrentHashMap.put(1, 2);
                a = 2;
            }
        }.start();
        new Thread("2") {
            @Override
            public void run() {
                concurrentHashMap.put(1, 3);
            }
        }.start();
        int a = 0x7fffffff;
        System.out.println(Integer.toBinaryString(a));
    }
}
