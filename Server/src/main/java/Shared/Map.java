package Shared;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class Map {

    ArrayList<MapObject> map = new ArrayList<MapObject>();
    ArrayList<MapObject> bombs = new ArrayList<MapObject>();
    ArrayList<MapObject> players = new ArrayList<MapObject>();

    public void addDestructibleWall(int positionX, int positionY) {
        map.add(new Wall(positionX, positionY, true));
    }

    public void addIndestructibleWall(int positionX, int positionY) {
        map.add(new Wall(positionX, positionY, false));
    }

    public void addPowerUP(int positionX, int positionY) {
        map.add(new PowerUP(positionX, positionY));
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

}
