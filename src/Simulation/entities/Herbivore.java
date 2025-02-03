package Simulation.entities;

import Simulation.Coordinates;
import Simulation.GameMap;
import Simulation.IGetTargetCoordinates;

import java.util.ArrayList;
import java.util.List;

public class Herbivore extends Creature<Grass> implements IGetTargetCoordinates  {

    private static final int STARTING_MINIMUM_HERBIVORE = 8;
    private static final int STARTING_MAXIMUM_HERBIVORE = 11;
    public static int startingHerbivoreCount = generateStartingCreatureCount(STARTING_MINIMUM_HERBIVORE, STARTING_MAXIMUM_HERBIVORE);
    protected List<Grass> grassPool = new ArrayList<>();



    public Herbivore(Coordinates coordinates) {
        super(coordinates, 10);
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public Coordinates takeTargetCoordinates(Creature creature, GameMap entities) {
        return findNearestCoordinates(Grass.class, entities);
    }


    @Override

    public boolean eat(Coordinates currentCoordinates, GameMap entities) {
        return super.eatEntity(currentCoordinates, entities, Grass.class);
    }

    protected void isCreatureDiedOfHunger() {
        startingHerbivoreCount--;
    }

    protected void increaseHealth() {
        this.health += 5;
    }
}

