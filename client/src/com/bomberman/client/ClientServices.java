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
    private ClientServerPackageReceiverThread receiver;
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

        receiver = new ClientServerPackageReceiverThread(map, in);
        receiver.start();

        return true;
    }

    public void sentInput(String input) {
        out.println(input);
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

    public boolean isConnected() {
        return isConnected.get() && receiver.isRunning();
    }
}
