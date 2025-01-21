package Simulation.entities;
import Simulation.Coordinates;
import Simulation.GameMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Creature<T> extends Entity {
    public int health;
    private final static int[] MOVE_RIGHT = new int[]{0,1};
    private final static int[] MOVE_LEFT = new int[]{0,-1};
    private final static int[] MOVE_UP = new int[]{1,0};
    private final static int[] MOVE_DOWN = new int[]{-1,0};
    private final static int[][] MOVEMENT_DIRECTIONS = {
            MOVE_RIGHT, MOVE_LEFT, MOVE_UP, MOVE_DOWN
    };
    protected final static int MAP_SIZE = 10;
    List<Entity> entities = new ArrayList<>();

    public Creature(Coordinates coordinates, int health) {
        super(coordinates);
        this.health = health;
    }

    public void makeMove(Coordinates coordinatesToMove, GameMap gameMap){
        if(this.health <= 0){
            return;
        }
        if(eat(coordinates, gameMap)){
            increaseHealth();

        } else if (gameMap.isSquareEmpty(coordinatesToMove)){
            if(this.health > 0) {
                gameMap.removeEntity(coordinates);
                gameMap.setEntity(coordinatesToMove, this);
                this.coordinates = coordinatesToMove;
            }
        }
    }

    public void checkDiedOfHunger(Creature creature, GameMap gameMap){
        if(this.health <= 0){
            isCreatureDiedOfHunger();
            gameMap.removeEntity(creature.coordinates);
        }
    }

    public abstract Coordinates takeTargetCoordinates(Creature creature);

    protected abstract void isCreatureDiedOfHunger();

    protected static int generateStartingCreatureCount(int minimumCreature, int maximumCreature) {
        Random random = new Random();
        return random.nextInt(maximumCreature - minimumCreature + 1) + minimumCreature;
    }


    protected Coordinates getTargetCoordinates(Creature creature, Class<T> entityTarget){
        entities.addAll(GameMap.entities.values());
        int minimumDistance = Integer.MAX_VALUE;
        Coordinates targetCoordinates = null;
        for (Entity entity : GameMap.entities.values()) {
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

    protected abstract boolean eat(Coordinates currentCoordinates, GameMap gameMap);

    protected boolean eatEntity(Coordinates currentCoordinates, GameMap gameMap, Class<T> entityTarget){
        for (int[] direction : MOVEMENT_DIRECTIONS) {
            int checkRow = currentCoordinates.row + direction[0];
            int checkColumn = currentCoordinates.column + direction[1];
            if (isValidCell(checkRow, checkColumn)) {
                Coordinates eatCoordinates = new Coordinates(checkRow, checkColumn);
                if (entityTarget.isInstance(gameMap.getEntity(eatCoordinates))) {
                    gameMap.getEntity(eatCoordinates).isEntityEaten = true;
                    gameMap.removeEntity(eatCoordinates);
                    return true;
                }
            }
        }
        return false;
    }

    protected abstract void increaseHealth();

    private boolean isValidCell(int row, int column) {
        return row >= 0 && row < MAP_SIZE && column >= 0 && column < MAP_SIZE;
    }

}
