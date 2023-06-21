package com.bomberman.client.communication;

import com.bomberman.common.model.Map;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.bomberman.common.utils.ClientServerCommunicationUtils.CONFIG_PATH;
import static com.bomberman.common.utils.EngineUtils.OFFLINE_PLAYER_INDEX;

public class ClientServices {

    private final int PORT = 21370;

    private Map map;
    private Receiver receiver;
    private Sender sender;
    private Socket clientSocket;
    private PrintWriter out;
    private ObjectInputStream in;
    private final AtomicBoolean isConnected;

    public ClientServices(Map map) {
        this.map = map;
        isConnected = new AtomicBoolean(false);
    }

    public void connectToServer() {
        String ip = getIp(CONFIG_PATH);
        try {
            //connect to server
            clientSocket = new Socket(ip, 21370);
            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (Exception e) {
            //e.printStackTrace();
            return;
        }
        isConnected.set(true);
        receiver = new Receiver(map, in);
        receiver.start();
        sender = new Sender(out);
        sender.start();
    }

    public void post(String packet) {
        sender.sendPacket(packet);
    }

    private String getIp(String filename) {
        String ip = null;
        File config;
        Scanner file;
        try {
            config = new File(filename);
            file = new Scanner(config);
            ip = file.nextLine();
            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("No config file to read IP");
            return null;
        }
        return ip;
    }

    public int getPlayerId() {
        if(receiver == null) return OFFLINE_PLAYER_INDEX;
        return receiver.getPlayerId();
    }

    synchronized public boolean isConnected() {
        if(!isConnected.get() || !receiver.isRunning()) {
            if(receiver != null) receiver.stopThread();
            if(sender != null) sender.stopThread();
            isConnected.set(false);
            return false;
        }
        return true;
    }

    synchronized public void disconnect() {
        if(receiver != null) {
            receiver.stopThread();
            receiver = null;
        }
        if(sender != null) {
            sender.stopThread();
            sender = null;
        }
    }
}
