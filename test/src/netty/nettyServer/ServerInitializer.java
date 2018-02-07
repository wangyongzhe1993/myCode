package netty.nettyServer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by wyz on 2017/9/27.
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();
        channelPipeline.addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
//        channelPipeline.addLast(new MyByteToMessageDecoder());
//        channelPipeline.addLast(new MyRecordDecoder("De1"));
//        channelPipeline.addLast(new MyRecordDecoder("De2"));
//        channelPipeline.addLast(new MyRecordDecoder("De3"));
//        channelPipeline.addLast(new MyMessageToByteEncoder());
//        channelPipeline.addLast(new MyRecordEncoder("En3"));
//        channelPipeline.addLast(new MyRecordEncoder("En2"));
//        channelPipeline.addLast(new MyRecordEncoder("En1"));
//        channelPipeline.addLast(new LengthFieldBasedFrameDecoder());
        channelPipeline.addLast(new SoutAndSendTimeHandler());
    }
}
