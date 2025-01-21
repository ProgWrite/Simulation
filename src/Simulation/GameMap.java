package Simulation;

import Simulation.Entities.*;
import java.util.HashMap;

    public class GameMap {

        public static HashMap<Coordinates, Entity> entities = new HashMap<>();

        public void setEntity(Coordinates coordinates, Entity entity) {
                entities.put(coordinates, entity);
        }

        public Entity getEntity(Coordinates coordinates) {
                return entities.get(coordinates);
            }

        public void removeEntity(Coordinates coordinates) {
            entities.remove(coordinates);
        }

        public boolean isSquareEmpty(Coordinates coordinates) {
            return !entities.containsKey(coordinates);
        }
    }






