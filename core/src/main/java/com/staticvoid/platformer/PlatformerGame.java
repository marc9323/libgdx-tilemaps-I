package com.staticvoid.platformer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.staticvoid.platformer.world.CustomGameMap;
import com.staticvoid.platformer.world.GameMap;
import com.staticvoid.platformer.world.TileType;
import com.staticvoid.platformer.world.TiledGameMap;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class PlatformerGame extends ApplicationAdapter {
	OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture image;

	GameMap gameMap;

	@Override
	public void create() {
		batch = new SpriteBatch();
		image = new Texture("libgdx.png");

		camera = new OrthographicCamera();
		camera.setToOrtho(false,
				Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		camera.update();

	//	gameMap = new TiledGameMap();
		gameMap = new CustomGameMap();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// delta x and y are how much it was moved since last frame
		if(Gdx.input.isTouched()) {
			camera.translate(-Gdx.input.getDeltaX(),
					Gdx.input.getDeltaY());
			camera.update();
		}

		if(Gdx.input.justTouched()) {
			// getX/getY gives location on SCREEN where you are clicking
			// unproject gets the location WITHIN THE GAME WORLD of the clicking
			// SCREEN TO GAME WORLD COORDINATE CONVERSION
			Vector3 position = camera.unproject(new Vector3(Gdx.input.getX(),
					Gdx.input.getY(), 0));
			// from second layer, so thats layer 1, the first is zero?!?
			TileType type = gameMap.getTileTypeByLocation(1, position.x, position.y);

			// TODO:  just create a toString method for the TileType
			if(type != null) {
				System.out.println("You clicked on tile with id " + type.getId()
				+ type.getName() + " " + type.isCollideable() + " " + type.getDamage());
			}

			// if null, go down one layer, check the tile type, if not null, print type

			// if null, then check the next lower layer
			// TODO:  replace magic numbers with variables or constants
			// TODO:  put number of layer in GameConfig, turn this into a method with a while
			// loop, is it a valid tile?  get the type and output.  If it isn't back down a layer
			// and repeat.
			// make it a method or lambda call passing???
			if(type == null) {
				type = gameMap.getTileTypeByLocation(0, position.x, position.y);
				if(type != null) {
					System.out.println("You clicked on tile with id " + type.getId()
							+ type.getName() + " " + type.isCollideable() + " " + type.getDamage());
				}
			}
		}

		gameMap.render(camera);

	}

	@Override
	public void dispose() {
		batch.dispose();
		image.dispose();
	}
}