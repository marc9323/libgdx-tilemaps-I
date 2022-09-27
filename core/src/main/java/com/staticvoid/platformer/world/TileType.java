package com.staticvoid.platformer.world;

import java.util.HashMap;

public enum TileType {

    GRASS(1, true, "Grass"),
    DIRT(2, true, "Dirt"),
    SKY(3, false, "Sky"),
    LAVA(4, true, "Lava"),
    CLOUD(5, true, "Cloud"),
    STONE(6, true, "Stone");

    public static final int TILE_SIZE = 16;

    private int id;
    private boolean collideable;
    private String name;
    private float damage;

    private static HashMap<Integer, TileType> tileMap;

    static {
        tileMap = new HashMap<Integer, TileType>();

        for(TileType tileType : TileType.values()) {
            tileMap.put(tileType.getId(), tileType);
        }
    }

    public static TileType getTileTypeById(int id) {
        return tileMap.get(id);
    }

    private TileType(int id, boolean collideable, String name) {
        this(id, collideable, name, 0);
    }

    private TileType(int id, boolean collideable, String name, float damage) {
        this.id = id;
        this.collideable = collideable;
        this.name = name;
        this.damage = damage;
    }

    public int getId() {
        return id;
    }


    public boolean isCollideable() {
        return collideable;
    }


    public String getName() {
        return name;
    }


    public float getDamage() {
        return damage;
    }


}
