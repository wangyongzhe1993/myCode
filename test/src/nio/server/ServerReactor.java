package nio.server;

import log4j.LogUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by wyz on 2018/2/6.
 */
public class ServerReactor implements Runnable {
    Selector selector;

    public ServerReactor(Selector selector) {
        this.selector = selector;
    }

    public void run() {
        while (Server.isRunning) {
            try {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    if (selectionKey.isAcceptable()) {
                        /** 连接接入*/
                        try {
                            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            LogUtil.infoLogger.warn("accept connect:" + socketChannel.getRemoteAddress().toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                            LogUtil.infoLogger.warn("selectionKey.isAcceptable()", e);
                        }
                    } else if (selectionKey.isReadable()) {
                        /** 消息读取*/
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        try {
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            int readNum = socketChannel.read(byteBuffer);
                            if (readNum > 0) {
                                byteBuffer.flip();
                                byte[] bytes = new byte[readNum];
                                byteBuffer.get(bytes);
                                LogUtil.infoLogger.warn("receive:" + new String(bytes));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            LogUtil.infoLogger.warn("session disconnect", e);
                            socketChannel.close();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
