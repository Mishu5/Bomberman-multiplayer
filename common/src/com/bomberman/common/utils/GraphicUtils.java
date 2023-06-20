package com.bomberman.common.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.HashMap;

public class GraphicUtils {
    public static final int FPS = 60;
    public static final int SIDE_PANEL_PART = 5;
    public static final int WINDOW_HEIGHT = 2000;
    public static final int WINDOW_WIDTH = 2300;
    private static int BLOCK_SIZE = 50;
    public static final float DESTRUCTION_ANIMATION_TIME = 0.4f;
    public static final String FLOOR_TEXTURE = "floor.png";
    public static final String BOMB_TEXTURE = "bomb.png";
    public static final String PLAYER_TEXTURE = "1.png";
    public static final String POWER_UP_TEXTURE = "powerup.png";
    public static final String WALL_TEXTURE = "wall.png";
    public static final String SPAWN_TEXTURE = "spawn.png";
    public static final String DESTRUCTIBLE_WALL_TEXTURE = "destwall.png";
    public static final String DESTRUCTION_CENTER = "bomb_center.png";
    public static final String DESTRUCTION_LEFT = "bomb_l.png";
    public static final String DESTRUCTION_RIGHT = "bomb_r.png";
    public static final String DESTRUCTION_BOTTOM = "bomb_b.png";
    public static final String DESTRUCTION_TOP = "bomb_t.png";
    public static final String DESTRUCTION_TOP_END= "bomb_t_end.png";
    public static final String DESTRUCTION_BOTTOM_END = "bomb_b_end.png";
    public static final String DESTRUCTION_LEFT_END = "bomb_l_end.png";
    public static final String DESTRUCTION_RIGHT_END = "bomb_r_end.png";
    public static final String CURSOR = "cursor.png";


    public static int getBlockSize() {
        return BLOCK_SIZE;
    }

    public static void setBlockSize(int blockSize) {
        BLOCK_SIZE = blockSize;
    }

    public static final Skin SKIN = new Skin(Gdx.files.internal("../assets/skin/uiskin.json"));

    private static final HashMap<String, Texture> textures = new HashMap<String, Texture>();

    static public Texture getTexture(String path) {
        if(textures.containsKey(path)) return textures.get(path);
        Pixmap placeholderPixmap = new Pixmap(Gdx.files.internal(path));
        Pixmap outputPixmap = new Pixmap(BLOCK_SIZE, BLOCK_SIZE, placeholderPixmap.getFormat());
        outputPixmap.drawPixmap(placeholderPixmap,
                0, 0, placeholderPixmap.getWidth(), placeholderPixmap.getHeight(),
                0, 0, outputPixmap.getWidth(), outputPixmap.getHeight()
        );
        Texture texture = new Texture(outputPixmap);
        textures.put(path, texture);
        placeholderPixmap.dispose();
        outputPixmap.dispose();
        return texture;
    }
}
