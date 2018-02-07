package netty.webSocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 1.创建连个线程池NioEventLoopGroup：boss：接收连接，work：负责socket的网络读写处理
 * 2.ServerBootstrap,服务器配置对象
 * .group(arg0，arg1) arg0:boss arg1:work
 * .channel(NioServerSocketChannel.class):设置SocketChannel类
 * .handler():bossHandler(用不到)
 * .childHandler(new ChannelInitializer())(连接建立后的消息处理)  重写initChannel方法向ChannelPipeline中添加handler（编码器，解码器，业务逻辑处理handler）
 * .option()设置boss线程池参数，ChannelOption.SO_BACKLOG 设置接收连接池大小
 * .childOption()设置已连接的channel，常用：ChannelOption.SO_KEEPALIVE true,是否启用心跳验证机制 ChannelOption.TCP_NODELAY true,不使用内歌算法，ChannelOption.RCV_BUFF_ALLOCATOR new RecvByteBufAllocator() 设置消息接收缓冲区
 * 3.serverBoosStrap.bind(port).sync() 等待绑定服务器端口成功
 * 4.channelFuture.channel().sync() 阻塞等待服务器关闭
 * 5.finally 块：关闭boss和work线程池
 * Created by wyz on 2017/10/21.
 */
public class NettyServer {

    public void initServer() {
        /**  处理incoming连接，完成三次握手*/
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        /**  io读写处理*/
        EventLoopGroup workGroup = new NioEventLoopGroup();
        ServerInitializer serverInitializer = new ServerInitializer();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(serverInitializer)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        try {
            ChannelFuture channelFuture = serverBootstrap.bind(2333).sync();
            System.out.println("server start at port:" + 2333);
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        NettyServer nettyServer = new NettyServer();
        nettyServer.initServer();
    }
}
