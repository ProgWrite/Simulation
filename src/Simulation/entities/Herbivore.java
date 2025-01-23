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
    public Coordinates takeTargetCoordinates(Creature creature, GameMap entities) {
        return super.getTargetCoordinates(creature, Grass.class, entities);
    }

    public boolean eat(Coordinates currentCoordinates, GameMap entities){
        return super.eatEntity(currentCoordinates, entities, Grass.class);
    }

    protected void isCreatureDiedOfHunger(){
        startingHerbivoreCount--;
    };

    protected void increaseHealth(){
        this.health += 5;
    }

}


















//    @Override
//    public boolean eat(Coordinates currentCoordinates, GameMap entityPool) {
//        boolean hasEaten = false;
//        while (hasEaten == true) {
//            Coordinates targetCoordinates = takeTargetCoordinates(this);
//            if(targetCoordinates == null) {
//                break;
//            }
//            if (eatEntity(targetCoordinates, entityPool, Grass.class)) {
//                hasEaten = true;
//                currentCoordinates = targetCoordinates;
//            }
//        }
//        return hasEaten;
//    }


