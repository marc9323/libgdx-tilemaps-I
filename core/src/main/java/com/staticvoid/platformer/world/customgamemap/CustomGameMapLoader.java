package com.staticvoid.platformer.world.customgamemap;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.staticvoid.platformer.world.TileType;

import java.util.Random;

public class CustomGameMapLoader {

    private static Json json = new Json();
    private static final int SIZE = 100;

    // loads the map from the folder the game is being run from
    // if you save to .jar and run it will create maps directory
    public static CustomGameMapData loadMap (String id, String name) {
        // create the maps folder, get relative path to the jar file
        Gdx.files.local("maps/").file().mkdirs(); // make map directory
        // get handle to map file
        FileHandle file = Gdx.files.local("maps/" + id + ".map");
        // exists?
        if (file.exists()) {
            // get data from file, turn it into a CustomGameMap object that we can
            // send to map loader later
            // use libgdx json tool to load data from map file - convert string data to CustomMapData
            CustomGameMapData data = json.fromJson(CustomGameMapData.class, file.readString());
            return data;
        } else {
            // if file doesn't already exist - generate a random map
            CustomGameMapData data = generateRandomMap(id, name);
            saveMap(data.id, data.name, data.map);
            return data;
        }
    }

    public static void saveMap (String id, String name, int[][][] map) {
        CustomGameMapData data = new CustomGameMapData();
        data.id = id;
        data.name = name;
        data.map = map;

        Gdx.files.local("maps/").file().mkdirs();
        FileHandle file = Gdx.files.local("maps/" + id + ".map");
        file.writeString(json.prettyPrint(data), false);
    }

    public static CustomGameMapData generateRandomMap (String id, String name) {
        CustomGameMapData mapData = new CustomGameMapData();
        mapData.id = id;
        mapData.name = id;
        mapData.map = new int[2][SIZE][SIZE];

        Random random = new Random();

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                mapData.map[0][row][col] = TileType.SKY.getId();

                if (row > SIZE - 3 - 1) {
                    mapData.map[1][row][col] = TileType.LAVA.getId();
                } else if (row > SIZE - 20) {
                    mapData.map[1][row][col] = TileType.STONE.getId();
                } else if (row > SIZE - 25) {
                    mapData.map[1][row][col] = TileType.DIRT.getId();
                } else if (row > SIZE - 26) {
                    mapData.map[1][row][col] = TileType.GRASS.getId();
                } else {
                    if (random.nextInt(50) == 0)//1 chance out of 50 of being a cloud
                        mapData.map[1][row][col] = TileType.CLOUD.getId();
                }
            }
        }

        return mapData;
    }
}
