package Simulation;

import Simulation.Entities.Creature;
import Simulation.Entities.Grass;
import Simulation.Entities.Herbivore;
import Simulation.Entities.Predator;
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
    private final static int MAP_SIZE = 10;

    public Coordinates findPath(Coordinates start, Coordinates target, GameMap gameMap, Creature creature) {
        Queue<Coordinates> bfsQueue = new LinkedList<>();
        HashMap<Coordinates, Coordinates> parentMap = new HashMap<>();
        boolean[][] visitedCells = new boolean[MAP_SIZE][MAP_SIZE];
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
                  boolean checkForGrass = (gameMap.isSquareEmpty(new Coordinates(newRow, newColumn)) || gameMap.getEntity(new Coordinates(newRow, newColumn)) instanceof Grass);
                  boolean checkForHerbivore = (gameMap.isSquareEmpty(new Coordinates(newRow, newColumn)) || gameMap.getEntity(new Coordinates(newRow, newColumn)) instanceof Herbivore);

                  if(creature instanceof Herbivore){
                      if (isValidCell(newRow, newColumn) && !visitedCells[newRow][newColumn] && checkForGrass) {
                          addToQueueAndParentMap(newRow,newColumn,bfsQueue,parentMap,currentCoordinates,visitedCells);
                      }
                  }
                  else if(creature instanceof Predator){
                      if (isValidCell(newRow, newColumn) && !visitedCells[newRow][newColumn] && checkForHerbivore) {
                          addToQueueAndParentMap(newRow,newColumn,bfsQueue,parentMap,currentCoordinates,visitedCells);
                      }
                  }
              }
        }
        return new Coordinates(0, 0);
    }

    private Coordinates getPathToTarget(Map<Coordinates, Coordinates> parentMap, Coordinates target) {
        List<Coordinates> way = new ArrayList<>();
        Coordinates currentCoordinates = target;

        while(currentCoordinates != null) {
            way.add(currentCoordinates);
            currentCoordinates = parentMap.get(currentCoordinates);
        }
        Collections.reverse(way);
        Coordinates coordinatesToMove = way.get(1);
        return coordinatesToMove;
    }

    private void addToQueueAndParentMap(int newRow, int newColumn, Queue<Coordinates> bfsQueue, HashMap<Coordinates, Coordinates> parentMap, Coordinates currentCoordinates, boolean[][] visitedCells ){
        visitedCells[newRow][newColumn] = true;
        Coordinates nextCoordinates = new Coordinates(newRow, newColumn);
        bfsQueue.add(nextCoordinates);
        parentMap.put(nextCoordinates, currentCoordinates);
    }

    private boolean isValidCell(int row, int column) {
        return row >= 0 && row < MAP_SIZE && column >= 0 && column < MAP_SIZE;
    }
}




