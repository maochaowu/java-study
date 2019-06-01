package com.mcw.cora.net.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author yibi
 * Date 2019/5/26 14:56
 * Version 1.0
 **/
public class AioNonBlockServer {
    private static final Integer SERVER_PORT = 8888;
    private static final AtomicInteger threadCount = new AtomicInteger();

    public static void main(String[] args) {
        try {
            AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", SERVER_PORT));
            serverSocketChannel.accept("hello", new CompletionHandler<AsynchronousSocketChannel, String>() {
                @Override
                public void completed(AsynchronousSocketChannel socketChannel, String attachment) {
                    serverSocketChannel.accept("hello", this);
                    try {
                        InetSocketAddress socketAddress = (InetSocketAddress) socketChannel.getRemoteAddress();
                        System.out.printf("线程：[%s] 开始处理socket的数据，socket连接的端口为[%d] \n ", Thread.currentThread().getName(), socketAddress.getPort());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    final Integer readCount = 0;
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    socketChannel.read(byteBuffer, readCount, new CompletionHandler<Integer, Integer>() {
                        @Override
                        public void completed(Integer result, Integer attachment) {
                            threadCount.incrementAndGet();
                            if (result != -1) {
                                attachment += result;
                                byteBuffer.clear();
                                socketChannel.read(byteBuffer, attachment, this);
                            } else {
                                System.out.printf("一共读取字节数为%d,当前线程数%d: \n", attachment, threadCount.get());
                            }
                            threadCount.decrementAndGet();
                        }

                        @Override
                        public void failed(Throwable exc, Integer attachment) {

                        }
                    });
                }

                @Override
                public void failed(Throwable exc, String attachment) {

                }
            });
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
