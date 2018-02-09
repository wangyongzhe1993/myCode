package io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wyz on 2018/2/6.
 */
public class Test {
    private static String charSet = "GBK";

    static String getUrlContext(String url) {
        StringBuffer sb = new StringBuffer();
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();
            connection.connect();
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "GBK"));
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }

        }
        return sb.toString();
    }

    static String RegexString(String targetStr, String patternStr) {
        // 定义一个样式模板，此中使用正则表达式，括号中是要抓的内容
        // 相当于埋好了陷阱匹配的地方就会掉下去
        Pattern pattern = Pattern.compile(patternStr);
        // 定义一个matcher用来做匹配
        Matcher matcher = pattern.matcher(targetStr);
        // 如果找到了
        if (matcher.find()) {
            // 打印出结果
            return matcher.group(1);
        }
        return "Nothing";
    }

    public static void main(String[] args) throws Exception {
//        String url = "http://www.jd.com";
        String url = "https://passport.jd.com/new/login.aspx";
        String result = getUrlContext(url);
        System.out.println(result);
        System.out.println(result.length());
    }

}
