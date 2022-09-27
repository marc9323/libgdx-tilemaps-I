package com.staticvoid.platformer.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Logger;
import com.staticvoid.platformer.world.customgamemap.CustomGameMapData;
import com.staticvoid.platformer.world.customgamemap.CustomGameMapLoader;

public class CustomGameMap extends GameMap{

    private static final Logger log =
            new Logger(CustomGameMap.class.getName(),
                    Logger.DEBUG);

    String id;
    String name;
    int[][][] map;

    // SpriteBatch ... load tile images
    private SpriteBatch batch;
    private TextureRegion[][] tiles;

    public CustomGameMap() {
        CustomGameMapData data =
                CustomGameMapLoader.loadMap("basic", "My Grass Lands!");
        this.id = data.id;
        this.name = data.name;
        this.map = data.map;

        batch = new SpriteBatch();
        // split up the tiles.png tile sheet into a 2D array : 17:40
        tiles = TextureRegion.split(new Texture("tiles.png"),
                TileType.TILE_SIZE, TileType.TILE_SIZE);
    }

    @Override
    public void render(OrthographicCamera camera) {
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        // triple embedded for loop
        for(int layer = 0; layer < getLayers(); layer++) {
            for(int row = 0; row < getHeight(); row++) {
                for(int col = 0; col < getWidth(); col++) {
                    // get the tile type at the current location
                    TileType type = this.getTileTypeByCoordinate(layer, col, row);

                    // check for null - no tile present here
                    if(type != null) {
                        // arrays index from zero to length -1
                        // tile id's however start at 1
                        // for the indices to match up we must substract 1
                        batch.draw(
                                tiles[0][type.getId() - 1],
                                col * TileType.TILE_SIZE,
                                row * TileType.TILE_SIZE
                        );
                    }
                }
            }
        }

        batch.end();
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    // by Tile pixel locations, not by Tile coordinate
    @Override
    public TileType getTileTypeByLocation(int layer, float x, float y) {
        return this.getTileTypeByCoordinate(layer,
                (int)(x / TileType.TILE_SIZE),
                getHeight() - (int)(y / TileType.TILE_SIZE) - 1);
    }

    // by Tile location, not by pixel location
    @Override
    public TileType getTileTypeByCoordinate(int layer, int col, int row) {
        if(col < 0 || col >= getWidth() || row < 0 || row >= getHeight()) {
            return null; // if out of bounds
        }

        // reading it upside down?  inverse of it?  of what?
        // the files get loaded upside down
        // loading in descending order?
        // why not use the damn texture packer / texture atlas tools?
        return TileType.getTileTypeById(
                map[layer]
                [getHeight() - row - 1]
                [col]

        );
    }

    @Override
    public int getWidth() {
        return map[0][0].length;
    }

    @Override
    public int getHeight() {
        return map[0].length; // length of rows, the second index
    }

    @Override
    public int getLayers() {
        // returns length of first index which is the layer [layer][row][col]
        return map.length;
    }
}
