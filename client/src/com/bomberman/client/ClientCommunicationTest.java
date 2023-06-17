package com.bomberman.client;

import com.bomberman.common.serialization.MapDTO;

import java.io.*;
import java.net.*;

public class ClientCommunicationTest {

    public static void main(String[] args) {

        Socket clientSocket = null;
        PrintWriter out = null;
        ObjectInputStream in = null;
        MapDTO map = null;

        try {
            clientSocket = new Socket("127.0.0.1", 21370);
            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new PrintWriter(clientSocket.getOutputStream(), true);

        } catch (IOException e) {
            System.out.println("Can't connect");
        }

        try {
            while (true) {
                map = (MapDTO) in.readObject();
                System.out.println(map.getMap().size());
            }
        } catch (ClassNotFoundException e) {
            System.out.println("No class");
        } catch (IOException e) {
            System.out.println("IO error"+ e);
        }

    }

}
