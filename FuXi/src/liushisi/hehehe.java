package liushisi;

/**
 * Created by wyz on 2018/1/26.
 */
public class hehehe {
    public static void main(String[] args) {
        char[] chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ+-".toCharArray();
        String str0 = "5a6802d076ce";
//        String str0 = "5a6802d076ce";
        String str1 = "ec550484d304";
        long num = Long.parseLong(str0, 16);
        System.out.println(num);
        System.out.println("num:" + Long.toBinaryString(num));
        long xianzhi = 0b111111;
        long temp = num & xianzhi;
        String sb = "";
        while (num > 0) {
            System.out.println(Long.toBinaryString(temp) + " : " + temp + " : " + chars[(int) temp]);
            sb = chars[(int) temp] + sb;
            num = num >> 6;
            temp = num & xianzhi;
        }
        System.out.println("sb:" + sb.toString());

    }
}
