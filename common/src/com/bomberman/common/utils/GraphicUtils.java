package com.bomberman.common.utils;

public class GraphicUtils {
    public static final int FPS = 60;
    public static final int SIDE_PANEL_PART = 12;
    public static final int WINDOW_HEIGHT = 2000;
    public static final int WINDOW_WIDTH = (int)(WINDOW_HEIGHT * (1 + 0.01 * SIDE_PANEL_PART));
    public static final int BLOCK_SIZE = WINDOW_HEIGHT / 20;
    public static final String FLOOR_TEXTURE = "floor.png";
    public static final String BOMB_TEXTURE = "bomb.png";
    public static final String PLAYER_TEXTURE = "1.png";
    public static final String POWER_UP_TEXTURE = "powerup.png";
    public static final String WALL_TEXTURE = "wall.png";
    public static final String DESTRUCTIBLE_WALL_TEXTURE = "destwall.png";
}
