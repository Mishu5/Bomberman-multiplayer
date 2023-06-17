package com.bomberman.client;

import com.bomberman.common.model.Map;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientServices {

    private final int PORT = 21370;

    private Map map;
    private Receiver receiver;
    private Sender sender;
    private Socket clientSocket;
    private PrintWriter out;
    private ObjectInputStream in;
    private AtomicBoolean isConnected;

    public ClientServices(Map map) {
        this.map = map;
        isConnected = new AtomicBoolean(false);
    }

    public boolean connectToServer() {

        String ip = getIp("config.txt");

        try {
            //connect to server
            clientSocket = new Socket(ip, 21370);
            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        isConnected.set(true);

        //connected
        //starting new thread

        receiver = new Receiver(map, in);
        receiver.start();
        sender = new Sender(out);
        sender.start();

        return true;
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
            System.out.println("No file");
            return null;
        }

        return ip;
    }

    public int getPlayerId() {
        return receiver.getPlayerId();
    }

    synchronized public boolean isConnected() {
        if(!isConnected.get() || !receiver.isRunning()) {
            receiver.stopThread();
            sender.stopThread();
            isConnected.set(false);
            return false;
        }
        return true;
    }
}
