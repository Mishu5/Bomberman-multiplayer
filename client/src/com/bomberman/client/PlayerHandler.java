package com.bomberman.client;

import com.bomberman.common.model.Map;
import com.bomberman.common.model.MapObject;
import com.bomberman.common.model.Player;
import com.bomberman.common.model.Wall;



public class PlayerHandler {
    private Player player;

    private Map map;

    public PlayerHandler(Player player) {
        this.player = player;
    }


    private int validate_move(int x, int y){
        map= new Map();
        map.loadMapFromFile("assets/map.txt");

        if(map.wallcheck(player.getPositionX() + x, player.getPositionY() + y)){
            return 1;
        }else{
            return 0;
        }
    }

    public void characterMove(int x, int y) {
        if(validate_move(x,y)==0){
            player.move(x,y);
        }else{
            //player.move(currentPosX,currentPosY);
            return;
        }

        //player.move(x,y);

    }
}
