package com.bomberman.server;

import com.bomberman.common.engine.GameServices;
import com.bomberman.common.engine.PlayerHandler;
import com.bomberman.common.model.Player;
import com.bomberman.common.serialization.Parser;
import java.util.ArrayList;

import static java.lang.System.exit;

import java.io.*;
import java.net.*;

public class ClientConnectionHandler extends Thread {

    private GameServices gameEngine;
    private final int MAX_PLAYER_COUNT = 4;
    private final int SOCKET_INITIALIZATION_ERROR = 1;
    private final int SOCKET_CLIENT_ACCEPTANCE_ERROR = 2;
    private final int OUTPUT_STREAM_CREATION_ERROR = 3;
    private final int BUFFERED_READER_CREATION_ERROR = 4;

    //server socket related
    private final int PORT = 21370;
    private ServerSocket serverSocket;
    private Socket clientSocket;

    //server clients threads
    private ArrayList<ClientHandlerThread> clientThreads;

    public ClientConnectionHandler(GameServices gameEngine) {
        this.gameEngine = gameEngine;
        this.clientThreads = new ArrayList<>();
    }

    public void run() {

        initializeSockets();

        System.out.println("Waiting for players...");

        while (true) {

            acceptPlayer();

            //checking for available slots
            int currentPlayerCount = gameEngine.getMap().getPlayers().size();
            if (currentPlayerCount == MAX_PLAYER_COUNT) continue;

            //adding new player
            /**
             * TODO
             * Player constructor should define player position
             * add output stream
             */
            BufferedReader tempBufferedReaderHolder = makeBufferedReader(clientSocket);
            PlayerHandler currentPlayerHandler = gameEngine.addPlayer(new Player(1, 1, currentPlayerCount));
            ClientHandlerThread tempHandlerThreadHolder = new ClientHandlerThread(currentPlayerHandler,tempBufferedReaderHolder);
            clientThreads.add(tempHandlerThreadHolder);
            tempHandlerThreadHolder.start();

        }

    }

    private void initializeSockets() {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            exit(SOCKET_INITIALIZATION_ERROR);
        }
    }

    private void acceptPlayer() {

        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.out.println("Failed to accept client");
            exit(SOCKET_CLIENT_ACCEPTANCE_ERROR);
        }

    }

    private ObjectOutputStream makeNewObjectOutPutStream(Socket playerSocket) {

        ObjectOutputStream tempObjectOutputStreamHolder = null;

        try {
            tempObjectOutputStreamHolder = new ObjectOutputStream(playerSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Error in creating output stream");
            exit(OUTPUT_STREAM_CREATION_ERROR);
        }
        return tempObjectOutputStreamHolder;

    }

    private BufferedReader makeBufferedReader(Socket clientSocket) {

        BufferedReader tempBufferedReaderHolder = null;

        try {
            tempBufferedReaderHolder = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Error in creating buffered reader");
            exit(BUFFERED_READER_CREATION_ERROR);
        }

        return tempBufferedReaderHolder;
    }

}
