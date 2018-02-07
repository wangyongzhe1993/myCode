package netty.nettyServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.nio.ByteBuffer;

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
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        ServerInitializer serverInitializer = new ServerInitializer();
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
            e.printStackTrace();
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String args[]) {
        NettyServer nettyServer = new NettyServer();
        nettyServer.initServer();

        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.readerIndex();
        byteBuf.writerIndex();

        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.position();
    }
    /**
     *                   ________________________                                 __________________________
     *                  |                        |                               |                          |
     *                  |   <-----Inbound-----   |                               |   ---inbound------- >    |   ________
     *                  |   _____        ______  |                               |    _______      ____     |  |        |
     *      _______     |  |     |       |    |  |                               |    |     |     |    |    |  |        |
     *     |       |    |  |  ②  |       |  ③ |  |      ___________________      |    |  ⑤  |     |  ⑥ |    |  |        |
     *     |       |    |  |_____|       |____|  |     |                   |     |    |_____|     |____|    |  |        |
     *     |client |----|--------______----------|-----|      network      |-----|--------------------------|--| server |
     *     |       |    |       |     |          |     |___________________|     |          ______          |  |        |
     *     |       |    |       |  ①  |          |                               |          |     |         |  |        |
     *     |       |    |       |_____|          |                               |          |  ④  |         |  |________|
     *     |       |    |                        |                               |          |_____|         |
     *     |_______|    |   -----Outbound--->    |                               |    <-----outbound----    |
     *                  |___ChannelPipeline______|                               |______ChannelPipeline_____|
     *
     *  ①：StringEncoder继承于MessageToMessageEncoder，而MessageToMessageEncoder又继承于ChannelOutboundHandlerAdapter
     *  ②：HelloWorldClientHandler.java
     *  ③：StringDecoder继承于MessageToMessageDecoder，而MessageToMessageDecoder又继承于ChannelInboundHandlerAdapter
     *  ④：StringEncoder 编码器
     *  ⑤：StringDecoder 解码器
     *  ⑥：HelloWorldServerHandler.java
     *
     *
     * 淦
     *
     * 类别	NETTY特性实现
     * 设计	为多种网络传输类型提供统一的APIs,包括阻塞IO和非阻塞IO
     *      简单但高效的线程模型
     *      真实的无连接的数据包socket的支持
     *      逻辑组件通过链接的链式方式使用，使每个组件可以重复使用
     * 易用性	丰富的JAVA文档和大量的代码示例
     *          仅仅依赖于JDK1.6，部分特性可能需要依赖JDK1.7或者其他的额外jar包
     * 性能	有更大的吞吐量和低延迟性相比于直接使用JAVA 的原生APIs
     *      引用了池的概念和重复利用的理念来减少资源的消耗
     *      最大限度的减少内存拷贝
     * 健壮性	不会因为连接的缓慢，连接的损耗产生OOM
     *          在优质的网络环境中消除Netty应用的不公平的读写比
     */
}
