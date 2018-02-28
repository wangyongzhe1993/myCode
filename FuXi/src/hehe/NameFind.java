package hehe;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wyz on 2018/1/20.
 */
public class NameFind {
    static ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    static BasicDBObject allPlayer;

    public static void main(String[] args) throws Exception {
        allPlayer = readJsonFile("conf/playerTemplate.json");
        System.out.println("allPlayer:" + allPlayer.size());

        HashMap<String, String> hashMap_88 = readFile("conf/81.txt");
        System.out.println("hashMap_88:" + hashMap_88.size());
        arrayList.add(hashMap_88);

        HashMap<String, String> hashMap_77 = readFile("conf/71.txt");
        System.out.println("hashMap_77:" + hashMap_77.size());
        arrayList.add(hashMap_77);

        HashMap<String, String> hashMap_66 = readFile("conf/61.txt");
        System.out.println("hashMap_66:" + hashMap_66.size());
        arrayList.add(hashMap_66);

        HashMap<String, String> hashMap_55 = readFile("conf/51.txt");
        System.out.println("hashMap_55:" + hashMap_55.size());
        arrayList.add(hashMap_55);

        findData();
    }

    public static synchronized HashMap<String, String> readFile(String fileName) {
        HashMap<String, String> hashMap = new HashMap<>();
        try {
            String s;
            int lineNum = 1;
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
            while ((s = br.readLine()) != null) {
                int nameStartIndex = s.indexOf("\\\"");
                int nameEndIndex = s.indexOf("\\\"", nameStartIndex + 1);
                if (nameStartIndex < 0 || nameEndIndex < 0) {
                    System.out.println("error log data:" + s);
                } else {
                    int dataStartIndex = s.indexOf("\"");
                    String data = s.substring(dataStartIndex + 1, s.length() - 1);
                    String name = s.substring(nameStartIndex + 2, nameEndIndex);
                    hashMap.put(name, data);
                    lineNum++;
                }
            }
            System.out.println(lineNum);
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashMap;
    }

    public static BasicDBObject readJsonFile(String fileName) {
        StringBuilder result = new StringBuilder();
        try {
            String s;
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "GBK"));
            while ((s = br.readLine()) != null) {
                result.append(System.lineSeparator() + s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allPlayer = (BasicDBObject) JSON.parse(result.toString());
    }

    public static void findData() throws Exception {
        BasicDBObject basicDBObject = new BasicDBObject();
        BasicDBObject zzzzzDBObject = new BasicDBObject();
        int index = 0;
        int zzzindex = 1;
        int fileNum = 1;
        for (Object value : allPlayer.values()) {
            BasicDBObject template = (BasicDBObject) value;
            String name = template.getString("name");
            String account = template.getString("account");
            String password = template.getString("password");
            if (account.contains("\r\n")) {
                account = account.substring(0, account.length() - 2);
            }
            if (password.contains("\r\n")) {
                password = password.substring(0, password.length() - 2);
            }
            if (name.contains("\r\n")) {
                name = password.substring(0, name.length() - 2);
            }
            if (account.contains("\r\n")) {
                account = account.substring(0, account.length() - 2);
            }
            if (password.contains("\r\n")) {
                password = password.substring(0, password.length() - 2);
            }
            if (name.contains("\r\n")) {
                name = password.substring(0, name.length() - 2);
            }
            password.replaceAll("\r", "");
            String data;
            boolean isFind = false;
            for (HashMap<String, String> hashMap : arrayList) {
                if ((data = hashMap.get(name)) != null) {
                    BasicDBObject playerData = new BasicDBObject();
                    playerData.put("account", account);
                    playerData.put("password", password);
                    playerData.put("name", name);
                    playerData.put("player", data);
                    basicDBObject.put("" + index++, playerData);
                    if ((index != 0 && index % 50 == 0)) {
                        outToFile(fileNum++, basicDBObject.toString(), "find");
                        basicDBObject = new BasicDBObject();
                    }
                    isFind = true;
                    break;
                }
            }
            if (!isFind) {
                BasicDBObject playerData = new BasicDBObject();
                playerData.put("password", password);
                playerData.put("account", account);
                playerData.put("name", name);
                playerData.put("roleId", "");
                zzzzzDBObject.put("" + zzzindex++, playerData);
            }
        }
        outToFile(fileNum, basicDBObject.toString(), "find");
        outToFile(1, zzzzzDBObject.toString(), "notFind");
    }

    public static void outToFile(int fileNum, String str, String fileName) throws Exception {
        str = StringEscapeUtils.unescapeJava(str);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("wyz/" + fileName + fileNum + ".json"), "UTF-8"));
        bw.write(str);
        bw.close();
    }
}
