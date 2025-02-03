package Simulation.entities;

import Simulation.Coordinates;
import Simulation.GameMap;
import Simulation.IGetTargetCoordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Creature<T extends Entity> extends Entity implements Eatable  {
    public boolean isEaten = false;
    protected int health;
    public Coordinates coordinates;
    private final static int[] MOVE_RIGHT = new int[]{0,1};
    private final static int[] MOVE_LEFT = new int[]{0,-1};
    private final static int[] MOVE_UP = new int[]{1,0};
    private final static int[] MOVE_DOWN = new int[]{-1,0};
    private final static int[][] MOVEMENT_DIRECTIONS = {
            MOVE_RIGHT, MOVE_LEFT, MOVE_UP, MOVE_DOWN
    };
    protected List<Entity> entityPool = new ArrayList<>();

    @Override
    public String toString() {
        return super.toString();
    }

    public Creature(Coordinates coordinates, int health) {
        this.coordinates = coordinates;
        this.health = health;
    }

    public void setEaten(boolean isCreatureEaten) {
        this.isEaten = isCreatureEaten;
    }

    public void makeMove(Coordinates coordinatesToMove, GameMap entities){
        if(health <= 0){
            return;
        }
        if(eat(coordinates, entities)){
            increaseHealth();

        } else if (entities.isSquareEmpty(coordinatesToMove)){
            if(health > 0) {
                entities.removeEntity(coordinates);
                entities.setEntity(coordinatesToMove, this);
                this.coordinates = coordinatesToMove;
            }
        }
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public int decreaseHealthByOne (int health){
       return health - 1;
    }

    abstract void increaseHealth();

    public abstract Coordinates takeTargetCoordinates(Creature creature, GameMap entities);

    protected Coordinates findNearestCoordinates(Class<T> entityTarget, GameMap entities) {
        entityPool.clear();
        entityPool.addAll(entities.getAllEntities());
        int minimumDistance = Integer.MAX_VALUE;
        Coordinates targetCoordinates = null;

        for (Entity target : entityPool) {
            if (entityTarget.isInstance(target)) {
                T typedTarget = entityTarget.cast(target);
                if (typedTarget instanceof IGetTargetCoordinates) {
                    Coordinates checkCoordinates = ((IGetTargetCoordinates) typedTarget).getCoordinates();
                    int distance = Math.abs(coordinates.row - checkCoordinates.row) + Math.abs(coordinates.column - checkCoordinates.column);
                    if (distance < minimumDistance) {
                        minimumDistance = distance;
                        targetCoordinates = checkCoordinates;
                    }
                }
            }
        }
        return targetCoordinates != null ? targetCoordinates : coordinates;
    }



    protected abstract boolean eat(Coordinates currentCoordinates, GameMap entities);

    protected boolean eatEntity(Coordinates currentCoordinates, GameMap entities, Class<T> entityTarget) {
        for (int[] direction : MOVEMENT_DIRECTIONS) {
            int checkRow = currentCoordinates.row + direction[0];
            int checkColumn = currentCoordinates.column + direction[1];
            Coordinates checkCoordinates = new Coordinates(checkRow, checkColumn);

            if (entities.isCoordinatesValid(checkCoordinates)) {
                Coordinates eatCoordinates = new Coordinates(checkRow, checkColumn);
                Entity checkEntity = entities.getEntity(eatCoordinates);

                if (entityTarget.isInstance(checkEntity)) {
                    if(checkEntity instanceof Eatable) {
                        ((Eatable) checkEntity).setEaten(true);
                    }
                    entities.removeEntity(eatCoordinates);
                    return true;
                }
            }
        }
        return false;
    }

    public void checkDiedOfHunger(Creature creature, GameMap entities){
        if(health <= 0){
            isCreatureDiedOfHunger();
            entities.removeEntity(creature.coordinates);
        }
    }

    protected abstract void isCreatureDiedOfHunger();

    protected static int generateStartingCreatureCount(int minimumCreature, int maximumCreature) {
        Random random = new Random();
        return random.nextInt(maximumCreature - minimumCreature + 1) + minimumCreature;
    }
}



