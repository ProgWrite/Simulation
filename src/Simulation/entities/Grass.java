package Simulation.entities;
import Simulation.Coordinates;
import Simulation.IGetTargetCoordinates;

public class Grass extends Entity implements IGetTargetCoordinates, Eatable {
    public Coordinates coordinates;
    private boolean isEaten = false;

    public Grass(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setEaten(boolean isEaten) {
        this.isEaten = isEaten;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

}
