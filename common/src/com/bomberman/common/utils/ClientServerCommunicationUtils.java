package com.bomberman.common.utils;

import java.util.HashMap;
import java.util.Map;

public class ClientServerCommunicationUtils {

    public static final int GAME_TICK_RATE = 24;
    public static final int BOMB_TICK_DELAY = 1000;
    public static final int CLIENT_SENDER_DELAY = 150;
    public static final int CLIENT_RECEIVER_DELAY = 50;

    public enum Token{
        UP,
        DOWN,
        RIGHT,
        LEFT,
        BOMB,
        START_GAME
    };

    public static Map<Token,String> communicationDictionary = Map.of(
            Token.UP, "w",
            Token.DOWN, "s",
            Token.RIGHT,"d",
            Token.LEFT,"a",
            Token.BOMB,"b",
            Token.START_GAME,"e"
    );
}