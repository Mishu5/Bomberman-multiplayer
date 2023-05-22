package com.bomberman.common.model;

import static com.bomberman.common.utils.GraphicUtils.POWER_UP_TEXTURE;

public class PowerUp extends MapObject{

    public PowerUp(int positionX, int positionY){
        super(positionX,positionY,false,true, POWER_UP_TEXTURE);
    }
}
