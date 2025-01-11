package Simulation;

import Simulation.Entities.*;
import java.util.HashMap;

    public class GameMap {

        public static HashMap<Coordinates, Entity> gameMap = new HashMap<>();

        public void setEntity(Coordinates coordinates, Entity entity) {
                gameMap.put(coordinates, entity);
        }

        public Entity getEntity(Coordinates coordinates) {
                return gameMap.get(coordinates);
            }

        public void removeEntity(Coordinates coordinates) {
            gameMap.remove(coordinates);
        }

        public boolean isSquareEmpty(Coordinates coordinates) {
            return !gameMap.containsKey(coordinates);
        }
    }






