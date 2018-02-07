package nio.server;

import log4j.LogUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * nio 服务器
 * Created by wyz on 2018/2/6.
 */
public class Server {
    public static boolean isRunning = true;

    public void initServer(int port) {
        try {
            Selector selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            /** 开启Reactor线程*/
            Thread reactorThread = new Thread(new ServerReactor(selector), "ServerReactor-Thread");
            reactorThread.start();
            LogUtil.infoLogger.warn("server bind:" + port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.initServer(2333);
    }
}
