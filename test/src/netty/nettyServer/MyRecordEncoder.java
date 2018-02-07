package netty.nettyServer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * 解码器
 * Created by wyz on 2017/10/16.
 */
public class MyRecordEncoder extends MessageToMessageEncoder<MessagePojo> {
    String encoderName;

    public MyRecordEncoder(String encoderName) {
        this.encoderName = encoderName;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, MessagePojo msg, List<Object> out) throws Exception {
        msg.addToNameList(encoderName);
        out.add(msg);
        ctx.fireChannelReadComplete();

    }
}
