package Simulation;

import Simulation.entities.*;

import java.util.*;


public class GameMap {
    private final Map<Coordinates, Entity> entities = new HashMap<>();
    private final int height;
    private final int width;

    public GameMap(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Collection<Entity> getAllEntities() {
        return entities.values();
    }

    public void setEntity(Coordinates coordinates, Entity entity) {
        if (isCoordinatesValid(coordinates) && isSquareEmpty(coordinates)) {
            entities.put(coordinates, entity);
        } else {
            try {
                entities.put(coordinates, entity);
            } catch (IllegalArgumentException e) {
                System.out.println("Coordinates isn't empty or out of bounds");
            }
        }
    }

    public Entity getEntity(Coordinates coordinates) {
        if (isCoordinatesValid(coordinates)) {
            try {
                entities.get(coordinates);
            } catch (NullPointerException e) {
                System.out.println("Entity not found" + e.getMessage());
            }
        }
        return entities.get(coordinates);
    }

    public void removeEntity(Coordinates coordinates) {
        if (isCoordinatesValid(coordinates)) {
            entities.remove(coordinates);
        } else {
            try {
                entities.remove(coordinates);
            } catch (IllegalArgumentException e) {
                System.out.println("Entity not found" + e.getMessage());
            }
        }

    }

    public boolean isSquareEmpty(Coordinates coordinates) {
        return !entities.containsKey(coordinates);
    }

    public boolean isCoordinatesValid(Coordinates coordinates) {
        return coordinates.row >= 0 && coordinates.row < height &&
                coordinates.column >= 0 && coordinates.column < width;
    }

}





