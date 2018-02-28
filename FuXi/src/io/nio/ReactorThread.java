package io.nio;

import log.LogUtil;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by wyz on 2018/1/4.
 */
public class ReactorThread extends Thread {

    private Selector selector;

    public ReactorThread(Selector selector) {
        this.selector = selector;
    }

    public void run() {
        while (true) {
            try {
                selector.select(1000);
                Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
                while (selectionKeyIterator.hasNext()) {
                    SelectionKey selectionKey = selectionKeyIterator.next();
                    selectionKeyIterator.remove();
                    try {
                        if (selectionKey.isAcceptable()) {
                            /** 处理连接事件*/
                            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            LogUtil.infoLogger.error("已连接:" + socketChannel.getRemoteAddress().toString());
                        } else if (selectionKey.isReadable()) {
                            /** 处理读事件*/
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                            int readNum = socketChannel.read(byteBuffer);
                            if (readNum > 0) {
                                byteBuffer.flip();
                                byte[] bytes = new byte[byteBuffer.remaining()];
                                byteBuffer.get(bytes);
                                String message = new String(bytes);
                                LogUtil.infoLogger.info(socketChannel.getRemoteAddress().toString() + "receive:" + message);
                            }
                            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                            writeBuffer.put("hheheeheh".getBytes());
                            writeBuffer.flip();
                            socketChannel.write(writeBuffer);
                        }
                    } catch (Exception e) {
                        if (selectionKey != null) {
                            selectionKey.cancel();
                            if (selectionKey.channel() != null) {
                                selectionKey.channel().close();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
