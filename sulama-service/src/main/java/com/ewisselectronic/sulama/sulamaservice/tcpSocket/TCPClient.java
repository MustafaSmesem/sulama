package com.ewisselectronic.sulama.sulamaservice.tcpSocket;

import com.ewisselectronic.sulama.sulamacore.logging.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean connectionState = false;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        connectionState = true;
        new Thread(()->{
            String inputLine;
            try {
                while ((inputLine = in.readLine()) != null){
                    Log.info("server msg: %s", inputLine);
                    Thread.sleep(1);
                }
                connectionState = false;
                System.out.println("disconnected");
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                connectionState = false;
            }
        }).start();
    }

    public void sendMessage(String msg){
        out.println(msg);
    }

    public void stopConnection() {
        try {
            out.close();
            clientSocket.close();
            connectionState = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean getState() {
        return connectionState;
    }
}
