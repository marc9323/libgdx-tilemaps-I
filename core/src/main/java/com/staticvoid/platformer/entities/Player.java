package com.staticvoid.platformer.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.staticvoid.platformer.world.GameMap;

public class Player extends Entity {

    private static final int SPEED = 80; // x axis
    private static final int JUMP_VELOCITY = 5;

    Texture image;

    // This is Player so we know EntityType is PLAYER.. just pass to super
    public Player(float x, float y,
                  GameMap map) {
        super(x, y, EntityType.PLAYER, map);
        image = new Texture("player.png");
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(
                image,
                pos.x, pos.y,
                getWidth(), getHeight()
        ); // just in case size is not same as specified in EntityType enum
    }

    @Override
    public void update(float deltaTime, float gravity) {
        // hit space bar when player is on ground
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && grounded) {
            this.velocityY += JUMP_VELOCITY * getWeight();
            // hold spacebar while jumping to jump higher, if still going up
        } else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)
                && !grounded
                && this.velocityY > 0) {
            this.velocityY += JUMP_VELOCITY * getWeight() * deltaTime;
        }

        super.update(deltaTime, gravity); // applies the gravity

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moveX(-SPEED * deltaTime);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveX(SPEED * deltaTime);
        }

    }
}
