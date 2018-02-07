package netty.nettyServer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * 解码器
 * Created by wyz on 2017/10/16.
 */
public class MyRecordDecoder extends MessageToMessageDecoder<MessagePojo> {
    String decoderName;

    public MyRecordDecoder(String decoderName) {
        this.decoderName = decoderName;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, MessagePojo messagePojo, List<Object> out) throws Exception {
        messagePojo.addToNameList(decoderName);
        out.add(messagePojo);
        /** 调用下一个handler的channelActive方法*/
//        ctx.fireChannelActive();
        /** 调用下一个handler的channelInactive方法*/
//        ctx.fireChannelInactive();
        /** 调用下一个handler的channelRead方法*/
//        ctx.fireChannelRead(messagePojo);
        ByteBuffer buffer = ByteBuffer.allocate(1000);
        buffer.limit();
        buffer.capacity();
        buffer.position();

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }
}
