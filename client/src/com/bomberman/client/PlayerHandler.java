package com.bomberman.client;

import com.bomberman.common.model.Map;
import com.bomberman.common.model.MapObject;
import com.bomberman.common.model.Player;
import com.bomberman.common.model.Wall;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PlayerHandler {
    private Player player;

    private Map map;

    public int validate_move(int x, int y){
        map= new Map();
        map.loadMapFromFile("assets/map.txt");

        if(map.wallcheck(player.getPositionX()+x, player.getPositionY() + y)==1){
            return 1;
        }else{
            return 0;
        }
    }

    public PlayerHandler(Player player) {
        this.player = player;
    }


    public void characterMove(int x, int y) {
        int currentPosX= player.getPositionX();
        int currentPosY= player.getPositionY();
        if(validate_move(x,y)==0){
            player.move(x,y);
        }else{
            //player.move(currentPosX,currentPosY);
            return;
        }

        //player.move(x,y);

    }
}
