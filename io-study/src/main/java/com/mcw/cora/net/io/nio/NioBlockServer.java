package com.mcw.cora.net.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author yibi
 * Date 2019/5/26 11:21
 * Version 1.0
 **/
public class NioBlockServer {
    private static final Integer SERVER_PORT = 8888;
    private static final AtomicInteger threadCount = new AtomicInteger();

    public static void main(String[] args) {
        try {
            Selector selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", SERVER_PORT));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterable = keys.iterator();
                while (keyIterable.hasNext()) {
                    SelectionKey selectionKey = keyIterable.next();
                    keyIterable.remove();
                    if (selectionKey.isValid() && selectionKey.isAcceptable()) {
                        ServerSocketChannel serverSocketChannel1 = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel socketChannel = serverSocketChannel1.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } else if (selectionKey.isValid() && selectionKey.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        //通过异步线程读取时，此处要取消对读请求的监听，否则selector会一直就绪
                        selectionKey.interestOps(selectionKey.interestOps() & (~SelectionKey.OP_READ));
                        handleSocketData(socketChannel, selectionKey);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void handleSocketData(SocketChannel socketChannel, SelectionKey selectionKey) {
        Thread tt = new Thread(() -> {
            threadCount.incrementAndGet();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            try {
                InetSocketAddress socketAddress = (InetSocketAddress) socketChannel.getRemoteAddress();
                System.out.printf("线程：[%s] 开始处理socket的数据，socket连接的端口为[%d] \n ", Thread.currentThread().getName(), socketAddress.getPort());
                int len = 0;
                int totalCount = 0;
                while ((len = socketChannel.read(byteBuffer)) != -1) {
                    totalCount += len;
                    byteBuffer.clear();
                }
                System.out.printf("一共读取字节数为%d,当前线程数%d: \n", totalCount, threadCount.get());
                //恢复读请求的监听
                selectionKey.interestOps(selectionKey.interestOps() & (SelectionKey.OP_READ));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            threadCount.decrementAndGet();
        });
        tt.start();
    }
}
