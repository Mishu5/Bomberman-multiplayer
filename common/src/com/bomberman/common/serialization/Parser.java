package com.bomberman.common.serialization;

import com.bomberman.common.model.Map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

/**
 * TODO
 * Parser pobiera z pliku w folderze "assets" plik tekstowy
 * mapy i zamienia znaki na obiekty
 */
public class Parser {

    public void loadMapFromFile(String Filename, Map map) {
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
                    map.addIndestructibleWall(y, array[x].length - 1 - x);
                } else if (array[x][y] == 'x') {
                    map.addFloor(y, array[x].length - 1 - x);
                } else if (array[x][y] == '+') {
                    map.addDestructibleWall(y, array[x].length - 1 - x);
                }
            }
        }
    }
}
