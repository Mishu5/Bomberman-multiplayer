package com.bomberman.common.utils;

public class EngineUtils {
    public enum Direction {TOP, BOT, LEFT, RIGHT};
    public enum GameState { RUNNING, LOSS, WIN, IDLE}
    public static final int PLAYER_SPEED = 1;
    public static final int DETONATION_TIME = 3;
    public static final int DETONATION_RADIUS = 2;
    public static final int EVENT_SERVICE_DELAY = 0;
}
