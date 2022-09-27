package com.staticvoid.platformer.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.staticvoid.platformer.entities.Entity;
import com.staticvoid.platformer.entities.Player;

import java.util.ArrayList;


public abstract class GameMap {

    protected ArrayList<Entity> entities; // TODO:  use badlogic Array?

    public GameMap() {
        entities = new ArrayList<Entity>();
        entities.add(new Player(40, 450, this));
    }

    public void render(OrthographicCamera camera, SpriteBatch batch) {
        // loop through entities and add them
        for(Entity entity : entities) {
            entity.render(batch);
        }
    }

    public void update(float deltaTime) {
        for(Entity entity : entities) {
            entity.update(deltaTime, -9.8f); // TODO: use a constant!
        }
    }

    public abstract void dispose();

    /**
     * Gets a tile by pixel position within the game world at a specified layer
     * @param layer
     * @param x
     * @param y
     * @return
     */
    public TileType getTileTypeByLocation(int layer, float x, float y) {
        return this.getTileTypeByCoordinate(layer,
                (int) x / TileType.TILE_SIZE,
                (int) y / TileType.TILE_SIZE);
    }

    /**
     * Gets a tile at its coordinate within the map at a specified layer
     * @param layer
     * @param col
     * @param row
     * @return
     */
    public abstract TileType getTileTypeByCoordinate(int layer, int col, int row);

    public boolean doesRectCollideWithMap(float x, float y, int width, int height) {
        if(x < 0
                || y < 0
                || x + width > getPixelWidth()
                || y + height > getPixelHeight()
        ){
            return true;
        }

        // TODO: check reference:
        //  https://www.youtube.com/watch?v=IQMFiJSPVcY&list=PLrnO5Pu2zAHIKPZ8o14_FNIp9KVvwPNpn&index=5
        // row: y axis
        // triple embedded loop to check each tile
        for(int row = (int)(y / TileType.TILE_SIZE); row < Math.ceil((y + height) / TileType.TILE_SIZE); row++){
            for(int col = (int)(x / TileType.TILE_SIZE); col < Math.ceil((x + width) / TileType.TILE_SIZE); col++) {
                for(int layer = 0; layer < getLayers(); layer++) {
                    // loops through whatever tile the Entity we are checking touches
                    TileType type = getTileTypeByCoordinate(layer, col, row);
                    if(type != null && type.isCollideable()) {
                        return true; // collision
                    }
                }
            }
        }
        return false; // no collisions detected
    }

    public int getPixelWidth() {
        return this.getWidth() * TileType.TILE_SIZE;
    }

    public int getPixelHeight() {
        return this.getHeight() * TileType.TILE_SIZE;
    }

    // TODO:  see Rectangle collision videos for entity to entity collision

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract int getLayers();
}
