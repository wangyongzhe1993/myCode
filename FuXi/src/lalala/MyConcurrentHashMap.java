package lalala;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 利用CAS+Synchronized来保证并发更新的安全，底层采用数组+链表+红黑树的存储结构
 * 1.重要属性
 * keys value 永远不为 null.
 * node：    基本与hashMap相同但是：hash next使用volatile修饰，保证多线程下的内存可见性
 * sizeCtl：  table初始化和resize控制变量。负数时：-1表示正在被初始化。其他负数表示：-(1+正在resize的线程的数量),正数时表示扩容阈值
 * baseCount: table中的元素个数
 * cellsBusy：？？？
 * counterCells：？？？
 *
 * 重要方法：
 * initTable()
 *          1.如果table未初始化进入死循环
 *          2.如果sizeCtl小于零，说明有其他线程正在进行初始化，调用yield，让出cpu，等待初始化完成
 *          3.sizeCtl大于零,原子修改sizeCtl值为-1，若成功，则进入初始化代码
 * putVal(key,value,putIfAbsent)
 *          1.检查key value都不为空
 *          2.进入死循环
 *              1)如果table未初始化，调用initTable()进行初始化
 *              2)如果已初始化，判断对应index如果为空，直接赋值(此处取值使用原子取，赋值使用cas赋值)
 *              3)如果节点hash值为-1，该节点为ForwardingNode节点(其他线程正在对其resize)，调用helpTransfer帮助resize
 *              4)以上条件都不满足，则该index存储了多个元素
 *                      1】进入synchronize块，锁节点，防止其他线程修改使链表成环
 *                      2】再次获取节点，判断是否该节点被其他线程修改，如修改则退出
 *                      3】如果hash值大于零，说明是链表结构，遍历链表找到对应key元素替换，没找到则添加到最后
 *                          如果hash值小于零，说明是树形结构，调用相关方法进行处理
 *                      4】判断链表长度，是否需要转换成树
 *          3.调用addCount()方法，判断是否需要resize
 * addCount(x,check)     更新baseCount的值
 *          为了在高并发下拥有好的性能，使用Striped64机制
 *          1.
 *
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
                concurrentHashMap.remove(1);
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
