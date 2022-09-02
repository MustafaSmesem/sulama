package com.ewisselectronic.sulama.sulamaservice.tcpSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;

public class MainSocket extends Thread {

    final private int port;
    private ServerSocket serverSocket;
    private ThreadGroup tcpSocketThreadPool;
    private final int hbTimeOut;

    List<ClientSocketHandler> devices = new ArrayList<>();

    public MainSocket(int port, int hbTimeOut) {
        this.port = port;
        this.hbTimeOut = hbTimeOut;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            tcpSocketThreadPool = new ThreadGroup("TCP socket threads pool");
            while (true) {
                ClientSocketHandler handler = new ClientSocketHandler(serverSocket.accept(), UUID.randomUUID().toString(), tcpSocketThreadPool, hbTimeOut);
                devices.add(handler);
                handler.start();
                try {
                    Thread.sleep(0, 20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void closeSocketServer() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message, String deviceId) {
        boolean state = false;
        for (ClientSocketHandler handler:devices) {
            if (handler.getDeviceId().equals(deviceId)) {
                handler.sendMessage(message);
                state = true;
                break;
            }
        }
        if (!state)
            System.out.println("device is not connected");
    }
}
