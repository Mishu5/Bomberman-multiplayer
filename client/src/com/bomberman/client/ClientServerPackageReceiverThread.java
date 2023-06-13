package com.bomberman.client;

import java.io.ObjectInputStream;

import com.bomberman.common.serialization.MapDTO;
import com.bomberman.common.model.Map;

public class ClientServerPackageReceiverThread extends Thread {

    private ObjectInputStream in;
    private Map map;


    public ClientServerPackageReceiverThread(Map map, ObjectInputStream in) {
        this.map = map;
        this.in = in;
    }

    public void run() {

    }

}
