package com.mcw.cora.net.io.bio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Random;

/**
 * @Author yibi
 * Date 2019/5/26 10:16
 * Version 1.0
 **/
public class SyncBlockClient {
    private static final Integer SERVER_PORT = 8888;

    private static final Random random = new Random();

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            Thread tt = new Thread(() -> {
                try {
                    Thread.sleep(random.nextInt(6) * 1000);
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress("127.0.0.1", SERVER_PORT));
                    socket.getOutputStream().write(makeData());
                    socket.close();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            });
            tt.start();
        }
    }

    private static byte[] makeData() {
        String data = "hello my name is java god";
        return data.getBytes();
    }
}
