package nio.client;

import log4j.LogUtil;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by wyz on 2018/2/7.
 */
public class ClientReactor implements Runnable {
    private Selector selector;
    private Client client;

    public ClientReactor(Selector selector, Client client) {
        this.selector = selector;
        this.client = client;
    }

    public void run() {
        try {
            while (Client.isRunning) {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    if (selectionKey.isConnectable()) {
                        if (socketChannel.finishConnect()) {
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            client.channelActive();
                        } else {
                            LogUtil.infoLogger.error("connect failed");
                            System.exit(1);
                        }
                    }
                    if (selectionKey.isReadable()) {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        int readNum = socketChannel.read(byteBuffer);
                        byteBuffer.flip();
                        if (readNum > 0) {
                            byte[] bytes = new byte[readNum];
                            byteBuffer.get(bytes);
                            LogUtil.infoLogger.warn("receive:" + new String(bytes));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            client.channelInactive();
        }
    }
}
