package com.bomberman.server;

import com.bomberman.common.model.Map;

public class Server {

    public static void main(String[] args) {

        System.out.println("Hello world!");
        Map map = new Map();
        map.loadMapFromFile("assets/map.txt");
    }
}