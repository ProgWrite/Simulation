package Simulation;

import Simulation.entities.Creature;
import Simulation.entities.Grass;
import Simulation.entities.Herbivore;
import Simulation.entities.Predator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class BreadthFirstSearch {
    private final static int[] MOVE_RIGHT = new int[]{0,1};
    private final static int[] MOVE_LEFT = new int[]{0,-1};
    private final static int[] MOVE_UP = new int[]{1,0};
    private final static int[] MOVE_DOWN = new int[]{-1,0};
    private final static int[][] MOVEMENT_DIRECTIONS = {
            MOVE_RIGHT, MOVE_LEFT, MOVE_UP, MOVE_DOWN
    };


    public List<Coordinates> findPath(Coordinates start, GameMap entities, Creature creature) {
        Queue<Coordinates> bfsQueue = new LinkedList<>();
        HashMap<Coordinates, Coordinates> parentMap = new HashMap<>();
        Coordinates target = creature.takeTargetCoordinates(creature,entities);
        boolean[][] visitedCells = new boolean[entities.getHeight()][entities.getWidth()];
        bfsQueue.add(start);
        visitedCells[start.row][start.column] = true;

        while (!bfsQueue.isEmpty()) {
            Coordinates currentCoordinates = bfsQueue.poll();

            if (currentCoordinates.equals(target)) {
                return getPathToTarget(parentMap, target);
            }

              for (int[] direction : MOVEMENT_DIRECTIONS) {
                  int newRow = currentCoordinates.row + direction[0];
                  int newColumn = currentCoordinates.column + direction[1];
                  Coordinates newCoordinates = new Coordinates(newRow, newColumn);
                  boolean checkForGrass = (entities.isSquareEmpty(new Coordinates(newRow, newColumn)) || entities.getEntity(new Coordinates(newRow, newColumn)) instanceof Grass);
                  boolean checkForHerbivore = (entities.isSquareEmpty(new Coordinates(newRow, newColumn)) || entities.getEntity(new Coordinates(newRow, newColumn)) instanceof Herbivore);

                  if(creature instanceof Herbivore){
                      if (entities.isCoordinatesValid(newCoordinates) && !visitedCells[newRow][newColumn] && checkForGrass) {
                          addToQueueAndParentMap(newRow,newColumn,bfsQueue,parentMap,currentCoordinates,visitedCells);
                      }
                  }
                  else if(creature instanceof Predator){
                      if (entities.isCoordinatesValid(newCoordinates) && !visitedCells[newRow][newColumn] && checkForHerbivore) {
                          addToQueueAndParentMap(newRow,newColumn,bfsQueue,parentMap,currentCoordinates,visitedCells);
                      }
                  }
              }
        }
        return Collections.emptyList();
    }

    private List<Coordinates> getPathToTarget(Map<Coordinates, Coordinates> parentMap, Coordinates target) {
        List<Coordinates> way = new ArrayList<>();
        Coordinates currentCoordinates = target;

        while(currentCoordinates != null) {
            way.add(currentCoordinates);
            currentCoordinates = parentMap.get(currentCoordinates);
        }
        Collections.reverse(way);
        return way;
    }

    private void addToQueueAndParentMap(int newRow, int newColumn, Queue<Coordinates> bfsQueue, HashMap<Coordinates, Coordinates> parentMap, Coordinates currentCoordinates, boolean[][] visitedCells ){
        visitedCells[newRow][newColumn] = true;
        Coordinates nextCoordinates = new Coordinates(newRow, newColumn);
        bfsQueue.add(nextCoordinates);
        parentMap.put(nextCoordinates, currentCoordinates);
    }
}




