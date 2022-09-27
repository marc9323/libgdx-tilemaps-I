package com.staticvoid.platformer.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.staticvoid.platformer.world.GameMap;

public abstract class Entity {

    protected Vector2 pos; // x, y and math functions
    protected EntityType type;
    protected float velocityY = 0; // velocity for Y axis, per second in y axis
    protected GameMap map; // access map for collision detection
    protected boolean grounded = false;

    public Entity(float x, float y, EntityType type, GameMap map) {
        this.pos = new Vector2(x, y);

        this.type = type;
        this.map = map;
       // this.grounded = grounded;
    }

    public void update(float deltaTime, float gravity) {
        float newY = pos.y;

        this.velocityY += gravity * deltaTime * getWeight();

        newY += this.velocityY * deltaTime;

        // if the do collide, are they moving down?
        if(map.doesRectCollideWithMap(pos.x, newY, getWidth(), getHeight())) {
            if(velocityY < 0) { // means they have hit the ground
                this.pos.y = (float) Math.floor(pos.y);
                grounded = true;
            }
            this.velocityY = 0;
        } else {
            this.pos.y = newY;
            grounded = false;
        }
    }

    public abstract void render(SpriteBatch batch);

    // to calculate collision
    protected void moveX(float amount) {
        float newX = this.pos.x + amount;
        // no collision with the map at newX?
        if(!map.doesRectCollideWithMap(newX, pos.y, getWidth(), getHeight())) {
            this.pos.x = newX; // no collision so do the move
        }
    }

    public Vector2 getPos() {
        return pos;
    }

    public EntityType getType() {
        return type;
    }

    public float getX() {
        return pos.x;
    }

    public float getY() {
        return pos.y;
    }

    public boolean isGrounded() {
        return grounded;
    }

    public int getWidth() {
        return type.getWidth();
    }

    public int getHeight() {
        return type.getHeight();
    }

    public float getWeight() {
        return type.getWeight();
    }

}
