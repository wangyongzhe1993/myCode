package nio.client;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created by wyz on 2018/2/7.
 */
public class Client {
    private SocketChannel socketChannel;
    public static boolean isRunning = true;

    public void doConnect(int port, String address) {
        try {
            socketChannel = SocketChannel.open();
            Selector selector = Selector.open();
            socketChannel.configureBlocking(false);
            boolean isConnectSuccess = socketChannel.connect(new InetSocketAddress(address, port));
            if (isConnectSuccess) {
                socketChannel.register(selector, SelectionKey.OP_READ);
                this.channelActive();
            } else {
                socketChannel.register(selector, SelectionKey.OP_CONNECT);
            }
            Thread thread = new Thread(new ClientReactor(selector, this), "ClientReactor-Thread");
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void channelActive() {
        try {
            this.doWrite("channelActive");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void channelInactive() {

    }

    public void doWrite(String str) throws Exception {
        byte[] bytes = str.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
        byteBuffer.put(bytes);
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.doConnect(2333, "192.168.21.168");
    }


}
