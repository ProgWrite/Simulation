package Simulation.Entities;
import Simulation.Coordinates;

public abstract class Entity {

    public Coordinates coordinates;
    public boolean isEntityEaten = false;

    public Entity(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}


