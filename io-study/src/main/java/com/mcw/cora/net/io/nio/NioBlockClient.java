package com.mcw.cora.net.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Random;

/**
 * @Author yibi
 * Date 2019/5/26 10:16
 * Version 1.0
 **/
public class NioBlockClient {
    private static final Integer SERVER_PORT = 8888;

    private static final Random random = new Random();

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            SocketChannel socket = null;
            try {
                socket = SocketChannel.open();
                socket.connect(new InetSocketAddress("127.0.0.1", SERVER_PORT));
                sendDataWithThread(socket);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void sendDataWithThread(SocketChannel socket) {
        Thread tt = new Thread(() -> {
            try {

                Thread.sleep(random.nextInt(10) * 1000);
                socket.write(makeData());
                socket.close();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });
        tt.start();
    }

    private static ByteBuffer makeData() {
        String data = "hello my name is java god";
        ByteBuffer byteBuffer = ByteBuffer.wrap(data.getBytes());
        return byteBuffer;
    }
}
