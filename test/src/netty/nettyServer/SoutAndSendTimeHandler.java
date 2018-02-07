package netty.nettyServer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import log4j.LogUtil;

import java.util.Scanner;

/**
 * Created by wyz on 2017/9/27.
 */
public class SoutAndSendTimeHandler extends SimpleChannelInboundHandler<MessagePojo> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessagePojo msg) throws Exception {
        LogUtil.infoLogger.warn("server receive:" + msg);
        String str = "now" + System.currentTimeMillis();
        ctx.writeAndFlush(msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        new Thread(new MyScanner(ctx)).start();
        LogUtil.infoLogger.warn(this.getClass().getSimpleName() + " channelActive port:" + ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LogUtil.infoLogger.warn(this.getClass().getSimpleName() + " channelInactive");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        LogUtil.infoLogger.warn("handlerAdded");
        super.handlerAdded(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        LogUtil.infoLogger.warn("handlerRemoved");
        super.handlerRemoved(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    public class MyScanner implements Runnable {
        ChannelHandlerContext channelHandlerContext;

        public MyScanner(ChannelHandlerContext channelHandlerContext) {
            this.channelHandlerContext = channelHandlerContext;
        }

        public void run() {
            Scanner scanner = new Scanner(System.in);
            while (channelHandlerContext.channel().isActive()) {
                while (scanner.hasNext()) {
                    String str = scanner.nextLine();
                    ByteBuf byteBuf = channelHandlerContext.alloc().buffer();
                    byte[] bytes = str.getBytes();
                    byteBuf.writeBytes(bytes);
                    channelHandlerContext.writeAndFlush(byteBuf);
                }
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        ctx.fire
    }
}
