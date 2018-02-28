package hehe;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.*;
import java.util.*;

/**
 * Created by wyz on 2018/1/20.
 */
public class AccountIdFind {
    static ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    static BasicDBObject allPlayer;

    public static void main(String[] args) throws Exception {
        allPlayer = readJsonFile("wyz/notFind1.json");
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

        findRoleId();
    }


    public static synchronized HashMap<String, String> readFile(String fileName) {
        HashMap<String, String> hashMap = new HashMap<>();
        try {
            String s;
            int lineNum = 1;
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
            while ((s = br.readLine()) != null) {
                int dataStartIndex = s.indexOf("\"");
                int roleIDIndex = s.indexOf("roleID:");
                int roleEndIndex = s.indexOf(",", roleIDIndex);
                String data = s.substring(dataStartIndex + 1, s.length() - 1);
                String roleId = s.substring(roleIDIndex + 8, roleEndIndex);
                hashMap.put(roleId, data);
                lineNum++;
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
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
            while ((s = br.readLine()) != null) {
                result.append(System.lineSeparator() + s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allPlayer = (BasicDBObject) JSON.parse(result.toString());
    }

    public static void findRoleId() throws Exception {
        BasicDBObject basicDBObject = new BasicDBObject();
        BasicDBObject zzzzzDBObject = new BasicDBObject();
        int index = 0;
        int zzzindex = 1;
        for (Object value : allPlayer.values()) {
            BasicDBObject template = (BasicDBObject) value;
            String name = template.getString("name");
            String account = template.getString("account");
            String password = template.getString("password");
            String roleId = template.getString("roleId");
            if (account.contains("\r\n")) {
                account = account.substring(0, account.length() - 2);
            }
            if (password.contains("\r\n")) {
                password = password.substring(0, password.length() - 2);
            }
            password.replaceAll("\r", "");
            String data;
            boolean isFind = false;
            for (HashMap<String, String> hashMap : arrayList) {
                if ((data = hashMap.get(roleId)) != null) {
                    BasicDBObject playerData = new BasicDBObject();
                    playerData.put("account", account);
                    playerData.put("password", password);
                    playerData.put("name", name);
                    playerData.put("player", data);
                    basicDBObject.put("" + index++, playerData);
                    isFind = true;
                    break;
                }
            }
            if (!isFind) {
                BasicDBObject playerData = new BasicDBObject();
                playerData.put("account", account);
                playerData.put("password", password);
                playerData.put("name", name);
                playerData.put("roleId", roleId);
                zzzzzDBObject.put("" + zzzindex++, playerData);
            }
        }
        outToFile(1, zzzzzDBObject.toString(), "roleIdNotFind");
        outToFile(1, basicDBObject.toString(), "roleFind");
        System.out.println("zzzzzDBObject:" + zzzzzDBObject.size());
        System.out.println("basicDBObject:" + basicDBObject.size());
    }

    public static void outToFile(int fileNum, String str, String fileName) throws Exception {
        str = StringEscapeUtils.unescapeJava(str);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("roleFind/" + fileName + fileNum + ".json"), "UTF-8"));
        bw.write(str);
        bw.close();
    }
}
