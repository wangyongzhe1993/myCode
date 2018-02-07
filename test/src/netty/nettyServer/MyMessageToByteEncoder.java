package netty.nettyServer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码器
 * Created by wyz on 2017/10/16.
 */
public class MyMessageToByteEncoder extends MessageToByteEncoder<MessagePojo> {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, MessagePojo msg, ByteBuf out) throws Exception {
        msg.addToNameList("MyMessageToByteEncoder");
        int id = msg.getMessageId();
        String str = msg.getStr();
        out.writeInt(id);
        out.writeBytes(str.getBytes());
        System.out.println(out);
    }
}
