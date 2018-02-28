package io.nio;

import log.LogUtil;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * 1.打开ServerSocketChannel
 * 2.
 * Created by wyz on 2017/11/29.
 */
public class NioServer {
    public void initChannel(int port) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port), 1024);
        serverSocketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        /** 开启reactor线程*/
        new ReactorThread(selector).start();
        LogUtil.infoLogger.info("server bind port:" + port);
    }

    public static void main(String[] args) throws Exception {
        new NioServer().initChannel(2333);
    }

}
