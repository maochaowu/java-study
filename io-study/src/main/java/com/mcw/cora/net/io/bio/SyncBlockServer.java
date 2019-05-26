package com.mcw.cora.net.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author yibi
 * Date 2019/5/26 9:58
 * Version 1.0
 * 同步阻塞io
 **/
public class SyncBlockServer {
    private static final Integer SERVER_PORT = 8888;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress("127.0.0.1", SERVER_PORT));
            while (true) {
                Socket socket = serverSocket.accept();
                handleSocketData(socket);
                socket.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 处理socket中的写入信息
     *
     * @return void
     * @Author yibi
     * @Date 10:04 2019/5/26
     * @Param [socket]
     **/
    private static void handleSocketData(Socket socket) {
        try {
            InetSocketAddress socketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
            System.out.printf("线程：[%s] 开始处理socket的数据，socket连接的端口为[%d] \n ", Thread.currentThread().getName(), socketAddress.getPort());
            InputStream ins = socket.getInputStream();
            int len = 0;
            int totalCount = 0;
            byte[] buffer = new byte[1024];
            while ((len = ins.read(buffer)) != -1) {
                totalCount += len;
            }
            System.out.println("一共读取字节数为: " + totalCount);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
