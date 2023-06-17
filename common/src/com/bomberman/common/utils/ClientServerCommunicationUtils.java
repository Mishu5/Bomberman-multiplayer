package com.bomberman.common.utils;

import java.util.HashMap;
import java.util.Map;

public class ClientServerCommunicationUtils {

    public static final int GAME_TICK_RATE = 24;

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
