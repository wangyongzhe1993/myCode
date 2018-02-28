package lalala;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * 底层为数组的数据结构
 * 优点：随机访问效率高
 * 缺点：添加删除效率低
 * add方法：当add后的长度大于容量时自动扩容，每次扩容size>>2
 * clone浅表克隆
 * Created by wyz on 2018/2/26.
 */
public class MyArrayList {
    public static void main(String[] args) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(null);
        arrayList.get(0);
        arrayList.remove(0);
        arrayList.clone();
        arrayList.sort(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return 0;
            }
        });
        System.out.println(15>>1);
    }
}
