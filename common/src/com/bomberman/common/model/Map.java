package com.bomberman.common.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class Map {




    ArrayList<MapObject> map = new ArrayList<>();
    ArrayList<MapObject> bombs = new ArrayList<>();
    ArrayList<Player> players = new ArrayList<>();


    public void addDestructibleWall(int positionX, int positionY) {
        map.add(new Wall(positionX, positionY, true));
    }


    public void addIndestructibleWall(int positionX, int positionY) {
        map.add(new Wall(positionX, positionY, false));
    }

    public void addPowerUp(int positionX, int positionY) {
        map.add(new PowerUp(positionX, positionY));
    }

    public void addFloor(int positionX, int positionY) {
        map.add(new Floor(positionX, positionY));
    }


    public void addPlayer(int positionX, int positionY, int playerID) {
        players.add(new Player(positionX, positionY, playerID));
    }

    public void addBomb(int positionX, int positionY) {
        bombs.add(new Bomb(positionX, positionY));
    }

    public Player getPlayer(int index) {
        return players.get(index);
    }

    public void loadMapFromFile(String Filename) {


        char[][] array = null;
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(Filename));
            String line;
            int numberOfRows = 0;
            int numberOfColumns = 0;

            // Liczenie liczby wierszy i kolumn
            while ((line = reader.readLine()) != null) {
                numberOfRows++;
                numberOfColumns = line.length();
            }

            // Inicjalizacja tablicy
            array = new char[numberOfRows][numberOfColumns];

            // Ponowne czytanie pliku i wpisywanie znaków do tablicy
            reader.close();
            reader = new BufferedReader(new FileReader(Filename));

            int row = 0;


            while ((line = reader.readLine()) != null) {
                for (int collumn = 0; collumn < numberOfColumns; collumn++) {
                    array[row][collumn] = line.charAt(collumn);
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        for (int x = 0; x < Objects.requireNonNull(array).length; x++) {
            for (int y = 0; y < array[x].length; y++) {
                if (array[x][y] == '-') {
                    addIndestructibleWall(x, y);


                } else if (array[x][y] == 'x') {
                    addFloor(x, y);

                } else if (array[x][y] == '+') {
                    addDestructibleWall(x, y);

                }
            }

        }

    }

    public void draw(SpriteBatch batch) {
        for(MapObject obj: map) obj.draw(batch);
        for(MapObject obj: bombs) obj.draw(batch);
        for(MapObject obj: players) obj.draw(batch);
    }

    public int wallcheck(int x,int y){
        for (MapObject object : map) {
            if (object.getPositionX() == x && object.getPositionY() == y) {
                if (object.getTransparent() == false) {
                    return 1;
                }else return 0;
            }
        }
        return 0;
    }

}