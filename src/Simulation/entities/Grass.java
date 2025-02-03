package Simulation.entities;
import Simulation.Coordinates;
import Simulation.IGetTargetCoordinates;

public class Grass extends Entity implements IGetTargetCoordinates {
    public Coordinates coordinates;

    public Grass(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

}
