package echo.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by wyz on 2019/3/26.
 */
public class EchoServer {
    public static void main(String args[]) {
        try {
            NioEventLoopGroup group = new NioEventLoopGroup();
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.localAddress(new InetSocketAddress(2333));
            bootstrap.childHandler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel channel) throws Exception {
                    channel.pipeline().addLast(new EchoServerHandler());
                }
            });
            ChannelFuture future = bootstrap.bind().sync();
            System.out.println("server start!!!!!!!!");
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
