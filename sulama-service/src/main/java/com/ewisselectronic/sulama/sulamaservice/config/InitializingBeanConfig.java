package com.ewisselectronic.sulama.sulamaservice.config;

import com.ewisselectronic.sulama.sulamacore.logging.Log;
import com.ewisselectronic.sulama.sulamaservice.tcpSocket.MainSocket;
import com.ewisselectronic.sulama.sulamaservice.tcpSocket.TCPClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class InitializingBeanConfig implements InitializingBean {

    @Value("${server.port}")
    private int serverPort;

    @Value("${tcp-socket.port}")
    private int tcpSocketPort;

    @Value("${tcp-socket.heartbeat.timeout}")
    private int hbTimeOut;

    @Value("${tcp-socket.client-test.state}")
    private boolean clientState;
    @Value("${tcp-socket.client-test.number}")
    private int clientTestNumber;


    @Override
    public void afterPropertiesSet() {
        MainSocket server = new MainSocket(tcpSocketPort, hbTimeOut);
        server.start();
        Log.warn("Server Started at port %d, and tcp socket in port %d", serverPort, tcpSocketPort);


        //tcp client test
        if (clientState) {
            for (int i = 0; i < clientTestNumber; i++) {
                new Thread(() -> {
                    TCPClient tcpClient = new TCPClient();
                    try {
                        tcpClient.startConnection("127.0.0.1", 6623);
                        tcpClient.sendMessage("{\"DC\":\"S213KJBK@##BK\",\"LK\":\"khfahposdfh876587nc437yr73wnyc78r2cna837nyr\"}");
                        while (tcpClient.getState()) {
                            tcpClient.sendMessage("H");
                            try {
                                Thread.sleep(hbTimeOut - 1000);
                            } catch (InterruptedException e) {
                                Log.error("Thread sleep exception: %s", e.toString());
                            }
                        }
                        Log.warn("Server Socket Disconnected, try after 60 second");
                    } catch (IOException e) {
                        Log.debug("Cannot connect to dashboard, " + e.toString());
                    }
                }).start();
            }
        }
    }

}
