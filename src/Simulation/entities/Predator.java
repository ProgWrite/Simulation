package Simulation.entities;

import Simulation.Coordinates;
import Simulation.GameMap;

public class Predator extends Creature<Herbivore> {
    public final int speed;
    private static final int STARTING_MINUMUM_PREDATOR = 1;
    private static final int STARTING_MAXIMUM_PREDATOR = 2;
    public static int startingPredatorCount = generateStartingCreatureCount(STARTING_MINUMUM_PREDATOR, STARTING_MAXIMUM_PREDATOR);

    public Predator(Coordinates coordinates, int speed) {
        super(coordinates, 2);
        this.speed = speed;
    }

    public Coordinates takeTargetCoordinates(Creature creature, GameMap entities) {
        return findNearestCoordinates(Herbivore.class, entities);
    }

    public boolean eat(Coordinates currentCoordinates, GameMap entities) {
        boolean eaten = super.eatEntity(currentCoordinates, entities, Herbivore.class);
        if (eaten) {
            Herbivore.startingHerbivoreCount--;
        }
        return eaten;
    }

    protected void isCreatureDiedOfHunger() {
        startingPredatorCount--;
    }

    protected void increaseHealth() {
        this.health += 2;
    }
}