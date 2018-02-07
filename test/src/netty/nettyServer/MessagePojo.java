package netty.nettyServer;

import java.util.ArrayList;

/**
 * Created by wyz on 2017/10/16.
 */
public class MessagePojo {
    ArrayList<String> coderNameList = new ArrayList<>();
    int messageId;
    String str;

    public MessagePojo(int messageId, String str) {
        this.messageId = messageId;
        this.str = str;
    }

    public int getMessageId() {
        return messageId;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public void addToNameList(String name) {
        this.coderNameList.add(name);
    }

    @Override
    public String toString() {
        return "MessagePojo{" +
                "messageId=" + messageId +
                ", str='" + str + '\'' +
                '}';
    }

}
