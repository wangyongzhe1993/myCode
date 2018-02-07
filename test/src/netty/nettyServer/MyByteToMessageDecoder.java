package netty.nettyServer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 解码器
 * Created by wyz on 2017/10/16.
 */
public class MyByteToMessageDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        /** 消息第一个int为id，剩下的为消息体，每个消息长512个字节*/
        int id = in.readInt();
        byte[] bytes = new byte[in.readableBytes()];
        in.readBytes(bytes);
        MessagePojo messagePojo = new MessagePojo(id, new String(bytes));
        messagePojo.addToNameList("MyByteToMessageDecoder");
        out.add(messagePojo);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }
}
