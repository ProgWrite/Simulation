package Simulation.entities;
import Simulation.Coordinates;
import Simulation.GameMap;

public class Herbivore extends Creature<Grass> {

    public static final int STARTING_MINIMUM_HERBIVORE = 8;
    public static final int STARTING_MAXIMUM_HERBIVORE = 11;
    public static int startingHerbivoreCount = generateStartingCreatureCount(STARTING_MINIMUM_HERBIVORE, STARTING_MAXIMUM_HERBIVORE);
    public Herbivore(Coordinates coordinates) {
        super(coordinates, 10);
    }

    @Override
    public Coordinates takeTargetCoordinates(Creature creature) {
        return super.getTargetCoordinates(creature, Grass.class);
    }

    public boolean eat(Coordinates currentCoordinates, GameMap gameMap){
        return super.eatEntity(currentCoordinates, gameMap, Grass.class);
    }

    protected void isCreatureDiedOfHunger(){
        startingHerbivoreCount--;
    };

    protected void increaseHealth(){
        health += 5;
    }

}


















//    @Override
//    public boolean eat(Coordinates currentCoordinates, GameMap entities) {
//        boolean hasEaten = false;
//        while (hasEaten == true) {
//            Coordinates targetCoordinates = takeTargetCoordinates(this);
//            if(targetCoordinates == null) {
//                break;
//            }
//            if (eatEntity(targetCoordinates, entities, Grass.class)) {
//                hasEaten = true;
//                currentCoordinates = targetCoordinates;
//            }
//        }
//        return hasEaten;
//    }


