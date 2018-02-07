package Generic;

/**
 * 泛型和通配符
 * 通配符：？
 * 上界通配符：<? extends A>  表示一切继承于A的类的类型，该类型对象作为泛型方法的形参类型时只能接受null，但是可以方便获取各种类型的数据
 * 下界通配符：<? super A>    表示一切A的基类的类型，   该类型对象作为泛型方法返回值时只能返回Object,但是可以方便添加各种类型的数据
 */
public class TestGeneric {

    static class Food {

    }

    static class Fruit extends Food {
    }

    static class Apple extends Fruit {
    }

    static class RedApple extends Apple {
    }

    public static void main(String[] args) {
//        ArrayList<? extends Fruit> arrayList = new ArrayList<Apple>();
//        arrayList.add(new Fruit());

        Hehe<? super Fruit> hehe = new Hehe<>(new RedApple());

        hehe.setT(new Fruit());

//        Fruit fruit = hehe.getT();

        Object redApple = hehe.getT();

    }

    static class Hehe<T> {

        public Hehe(T t) {
            this.t = t;
        }

        private T t;

        public T getT() {
            return t;
        }

        public void setT(T t) {
            this.t = t;
        }
    }
}
