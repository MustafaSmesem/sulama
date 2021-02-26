package com.ewisselectronic.sulama.sulamaservice.tcpSocket;


import com.ewisselectronic.sulama.sulamacore.logging.Log;
import lombok.SneakyThrows;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientSocketHandler extends Thread {

    final private Socket socket;
    final private String socketId;
    private PrintWriter out;
    private BufferedReader in;
    private final int hbTimeOut;
    private boolean connected = true;
    private static long connectionTimer;
    private String deviceId;

    public ClientSocketHandler(Socket socket, String threadName, ThreadGroup threadGroup, int hbTimeOut) {
        super(threadGroup, threadName);
        this.socket = socket;
        socketId = threadName;
        this.hbTimeOut = hbTimeOut;
        Log.info("Client %s connected!", socketId);
    }


    @SneakyThrows
    @Override
    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            connected = true;
//            out.println(socketId);
            String inputLine;
            connectionTimer = System.currentTimeMillis();
            new Thread(()->{
                while (connected) {
                    if (System.currentTimeMillis() - connectionTimer > hbTimeOut)
                        connected = false;
                    try {
                        sleep(hbTimeOut - 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            while ((inputLine = in.readLine()) != null && connected) {
                parseMessage(inputLine);
            }

            in.close();
            out.close();
            socket.close();
            System.out.println(socketId + " disconnected");
        } catch (IOException e) {
            System.out.println(socketId + " disconnected :: " + e.toString());
        }
    }

    private void parseMessage(String inputLine) throws InterruptedException {
        connectionTimer = System.currentTimeMillis();
        Log.debug("Client[%s] message: %s", socketId, inputLine);
        if ("H".equals(inputLine))
            Log.debug("Client[%s] Heart Beat", socketId);
        else {
            try {
                JSONObject jsonObject = new JSONObject(inputLine);
                if (jsonObject.has("DC"))
                    checkLicence(jsonObject);
            } catch (JSONException e) {
                Log.error("Client[%s] message parse error | message: %s | ERROR: %s ", socketId, inputLine, e.toString());
            }
        }
        Thread.sleep(0, 20);
    }

    private void checkLicence(JSONObject jsonObject) throws JSONException {
        setDeviceId(jsonObject.getString("DC"));
        if (jsonObject.has("LK")) {
            Log.info("Device[%s] Licence Key: {%s} Accepted", deviceId, jsonObject.getString("LK"));
            out.println("{\"SC\":\"" + deviceId + "\",\"RM\":1}");
        } else {
            out.println("{\"SC\":\"\",\"RM\":0}");
            connected = false;
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
