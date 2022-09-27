package com.staticvoid.platformer.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.staticvoid.platformer.world.GameMap;

import java.util.HashMap;

@SuppressWarnings("rawtypes")
public enum EntityType {

    // TODO:  add in further entities here

    // Tile size is 16, make player 14 so it fits through some cracks
    // weight for gravity

    PLAYER("player", Player.class, 14, 32, 40);

    private String id;
    private Class loaderClass;
    private int width, height;
    private float weight;

    EntityType(String id, Class loaderClass, int width, int height, float weight) {
        this.id = id;
        this.loaderClass = loaderClass;
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

    // returns an entity created using an entity snapshot (json)
    public static Entity createEntityUsingSnapshot(EntitySnapshot entitySnapshot, GameMap map) {
        EntityType type = entityTypes.get(entitySnapshot.type);
        try {
            @SuppressWarnings("unchecked")
            Entity entity = (Entity) ClassReflection.newInstance(type.loaderClass);
            entity.create(entitySnapshot, type, map);
            return entity;
        } catch(ReflectionException e) {
            Gdx.app.error("Entity Loader: ",
                    "Could not load entity of type " + type.id);
            return null;
        }
    }

//    public static Entity createEntityUsingSnapshot (EntitySnapshot entitySnapshot, GameMap map) {
//        EntityType type = entityTypes.get(entitySnapshot.type);
//        try {
//            @SuppressWarnings("unchecked")
//            Entity entity = ClassReflection.newInstance(type.loaderClass);
//            entity.create(entitySnapshot, type, map);
//            return entity;
//        } catch (ReflectionException e) {
//            Gdx.app.error("Entity Loader", "Could not load entity of type " + type.id);
//            return null;
//        }
//    }

    private static HashMap<String, EntityType> entityTypes;
    static {
        entityTypes = new HashMap<String, EntityType>();
        for(EntityType type : EntityType.values()) {
            entityTypes.put(type.id, type);
        }
    }

    @Override
    public String toString() {
        return "EntityType{" +
                "id='" + id + '\'' +
                ", loaderClass=" + loaderClass +
                ", width=" + width +
                ", height=" + height +
                ", weight=" + weight +
                '}';
    }
}
