package Simulation.entities;
import Simulation.Coordinates;
import Simulation.GameMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Creature<T> extends Entity {
    protected int health;
    private final static int[] MOVE_RIGHT = new int[]{0,1};
    private final static int[] MOVE_LEFT = new int[]{0,-1};
    private final static int[] MOVE_UP = new int[]{1,0};
    private final static int[] MOVE_DOWN = new int[]{-1,0};
    private final static int[][] MOVEMENT_DIRECTIONS = {
            MOVE_RIGHT, MOVE_LEFT, MOVE_UP, MOVE_DOWN
    };
    List<Entity> entityPool = new ArrayList<>();

    public Creature(Coordinates coordinates, int health) {
        super(coordinates);
        this.health = health;
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

    protected Coordinates getTargetCoordinates(Creature creature, Class<T> entityTarget, GameMap entities){
        entityPool.clear();
        entityPool.addAll(entities.getAllEntities());
        int minimumDistance = Integer.MAX_VALUE;
        Coordinates targetCoordinates = null;
        for (Entity entity : entityPool) {
            if(entityTarget.isInstance(entity)){
                int distance = Math.abs(creature.coordinates.row - entity.coordinates.row) + Math.abs(creature.coordinates.column - entity.coordinates.column);
                if (distance < minimumDistance){
                    minimumDistance = distance;
                    targetCoordinates = entity.coordinates;
                }
            }
        }
        return targetCoordinates != null ? targetCoordinates : coordinates;
    }

    protected abstract boolean eat(Coordinates currentCoordinates, GameMap entities);

    protected boolean eatEntity(Coordinates currentCoordinates, GameMap entities, Class<T> entityTarget){
        for (int[] direction : MOVEMENT_DIRECTIONS) {
            int checkRow = currentCoordinates.row + direction[0];
            int checkColumn = currentCoordinates.column + direction[1];
            Coordinates checkCoordinates = new Coordinates(checkRow, checkColumn);
            if (entities.isCoordinatesValid(checkCoordinates)) {
                Coordinates eatCoordinates = new Coordinates(checkRow, checkColumn);
                if (entityTarget.isInstance(entities.getEntity(eatCoordinates))) {
                    entities.getEntity(eatCoordinates).isEntityEaten = true;
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
