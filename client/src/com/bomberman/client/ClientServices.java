package com.bomberman.client;

import com.bomberman.common.model.Map;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.io.*;
import java.net.*;

public class ClientServices {

    private final int PORT = 21370;

    private Map map;
    private ClientServerPackageReceiverThread receiver;
    private Socket clientSocket;
    private PrintWriter out;
    private ObjectInputStream in;

    public ClientServices(Map map) {
        this.map = map;
    }

    public boolean connectToServer() {

        String ip = getIp("config.txt");

        try {
            //connect to server
            clientSocket = new Socket(ip, 21370);
            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new PrintWriter(clientSocket.getOutputStream(), true);

        } catch (IOException e) {
            System.out.println(e);
            return false;
        }

        //connected
        //starting new thread

        receiver = new ClientServerPackageReceiverThread(map, in);
        receiver.start();

        return true;
    }

    public void SendInput(String input) {
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

}
