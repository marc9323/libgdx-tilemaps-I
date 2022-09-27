package com.staticvoid.platformer.entities;

public enum EntityType {

    // TODO:  add in further entities here

    // Tile size is 16, make player 14 so it fits through some cracks
    // weight for gravity

    PLAYER("player", 14, 32, 40);

    private String id;
    private int width, height;
    private float weight;

    EntityType(String id, int width, int height, float weight) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public float getWeight() {
        return weight;
    }
}
