package com.bomberman.client;

import com.bomberman.common.model.Player;

public class PlayerHandler {
    private Player player;

    public PlayerHandler(Player player) {
        this.player = player;
    }

    public void characterMove(int x, int y) {
        player.move(x, y);
    }
}
