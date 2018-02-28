package lalala;

import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * 是hash表和链表的实现
 * 继承于HashMap
 * 依靠双向链表保证迭代顺序是插入顺序（key相等时新的会替换旧的，顺序还是旧的）
 * 非线程安全
 * 优化：
 * accessOrder参数，为true时
 * get()方法修改集合结构，把最近访问的元素放到链表的末尾
 * Created by wyz on 2018/2/26.
 */
public class MyLinkedHashMap {
    public static void main(String[] args) {
        LinkedHashMap<Integer, Integer> linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(1, 1);
        linkedHashMap.put(2, 2);
        linkedHashMap.put(3, 3);
        linkedHashMap.put(4, 4);
        linkedHashMap.put(5, 5);
        linkedHashMap.put(6, 6);
        linkedHashMap.put(1, 1);
        Iterator<Integer> iterator = linkedHashMap.values().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
