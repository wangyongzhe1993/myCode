package lalala;

import java.util.HashMap;

/**
 * 是按key存取值的数据结构,底层采用ArrayList、链表、红黑树实现
 * 实现目的：最高效率
 * ArrayList随机访问效率高，删除，添加效率低
 * 链表随机访问效率低，删除添加效率高
 * 基础概念：
 * size：    map中元实际素个数
 * modCount：map元素被修改的次数
 * hash:    k的hash值，通过hash()方法获得【扰动函数】
 * 等于k.hashCode高16位与低16位做异或，加大低位的随机性，使得低位携带高位的特征，降低散列值冲突
 * node：   hashMap元素基本单位,key value hash next
 * table[]：存储node节点的数组，在put时初始化，默认初始长度为16
 * loadFactor:负载因子
 * threshold：扩容阈值 = 容量*负载因子，map内元素个数>=该值是，会进行扩容，调用resize方法
 * TREEIFY_THRESHOLD:   链表转化成红黑树的阈值，默认为8，调用treeifyBin方法，因为链表很长时查找效率低，红黑树可以保证在最坏的情况下还有较高的查找效率
 * UNTREEIFY_THRESHOLD：红黑树转化为链表的阈值，默认为6，调用untreeify方法，数据少时链表效率比树高
 * 常用方法：
 * put(key,value)
 *              1.数组没初始化，调用resize方法初始化
 *              2.tab对应key的下标没有元素，直接添加此节点
 *              3.该位置有元素，进入
 *                  1）先判断该位置的元素的key是否相等，相等则替换
 *                  2）如果节点类型为TreeNode，调用树相关方法添加
 *                  3）如果链表长度大于1，进入死循环，用key遍历查找是否存在相同的node（key == k || (key != null && key.equals(k))），
 *                     相同则替换，没有则在链表尾部添加，同时检查链表长度，是否treeifyBin
 *              4.检查元素个数，判断扩容
 * Created by wyz on 2018/2/24.
 */
public class MyHashMap {
    public static void main(String[] args) {
        HashMap hashMap = new HashMap();
        hashMap.put(null, null);
        hashMap.get(null);
        int a = 0b001000000;
        System.out.println(a);
        a = a - 1;
        System.out.println(Integer.toBinaryString(a));
        System.out.println(a);
        System.out.println(0b1111);
    }
}
