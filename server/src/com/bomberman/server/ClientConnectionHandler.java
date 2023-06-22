package com.bomberman.server;

import com.bomberman.common.engine.GameServices;
import com.bomberman.common.engine.PlayerHandler;
import com.bomberman.common.model.Player;
import com.bomberman.common.model.Spawn;


import java.util.ArrayList;

import static java.lang.System.exit;

import java.io.*;
import java.net.*;

public class ClientConnectionHandler extends Thread {

    private final GameServices gameEngine;

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private final ArrayList<ObjectOutputStream> outputs;

    //server clients threads
    private final ArrayList<ClientHandlerThread> clientThreads;

    public ClientConnectionHandler(GameServices gameEngine) {
        this.gameEngine = gameEngine;
        this.clientThreads = new ArrayList<>();
        this.outputs = new ArrayList<>();
    }

    public void run() {

        initializeSockets();

        ClientPackageSenderThread sender = new ClientPackageSenderThread(outputs, gameEngine);
        sender.start();

        System.out.println("Waiting for players...");

        while (true) {

            acceptPlayer();

            //checking for available slots
            int currentPlayerCount = gameEngine.getMap().getPlayers().size();
            int MAX_PLAYER_COUNT = 4;
            if (currentPlayerCount == MAX_PLAYER_COUNT) {
                denyPlayer();
                continue;
            }
            if (gameEngine.getMap().getGameStarted()) {
                denyPlayer();
                continue;
            }

            //adding new player

            BufferedReader tempBufferedReader = makeBufferedReader(clientSocket);
            ObjectOutputStream tempObjectOutputStream = makeNewObjectOutPutStream(clientSocket);

            //adding output stream
            outputs.add(tempObjectOutputStream);

            //creating player
            Spawn tempSpawnHolder = gameEngine.getMap().getSpawn(currentPlayerCount);
            PlayerHandler currentPlayerHandler = gameEngine.addPlayer(new Player(tempSpawnHolder.getPositionX(), tempSpawnHolder.getPositionY(), currentPlayerCount));

            //creating and starting new thread
            ClientHandlerThread tempHandlerThreadHolder = new ClientHandlerThread(currentPlayerHandler, tempBufferedReader);

            clientThreads.add(tempHandlerThreadHolder);
            tempHandlerThreadHolder.start();

        }

    }

    private void initializeSockets() {
        try {
            //server socket related
            int PORT = 21370;
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            int SOCKET_INITIALIZATION_ERROR = 1;
            exit(SOCKET_INITIALIZATION_ERROR);
        }
    }

    private void acceptPlayer() {

        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.out.println("Failed to accept client");
            int SOCKET_CLIENT_ACCEPTANCE_ERROR = 2;
            exit(SOCKET_CLIENT_ACCEPTANCE_ERROR);
        }

    }

    private void denyPlayer() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Failed to deny client");
            int SOCKET_CLIENT_DENY_ERROR = 5;
            exit(SOCKET_CLIENT_DENY_ERROR);
        }
    }

    private ObjectOutputStream makeNewObjectOutPutStream(Socket playerSocket) {

        ObjectOutputStream tempObjectOutputStreamHolder = null;

        try {
            tempObjectOutputStreamHolder = new ObjectOutputStream(playerSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Error in creating output stream");
            int OUTPUT_STREAM_CREATION_ERROR = 3;
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
            int BUFFERED_READER_CREATION_ERROR = 4;
            exit(BUFFERED_READER_CREATION_ERROR);
        }

        return tempBufferedReaderHolder;
    }

}
