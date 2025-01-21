package Simulation.actions;

import Simulation.Coordinates;
import Simulation.GameMap;
import Simulation.entities.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class InitActions{
        private final static int MAP_SIZE = 10;
        private final static Random RANDOM = new Random();
        List<EntityFactory> entities = new ArrayList<>();


        public void setupRandomStartEntitiesPositions(GameMap gameMap){
            entities.add(new EntityFactory(Herbivore::new, Herbivore.startingHerbivoreCount));
            entities.add(new EntityFactory(Grass::new, 15));
            entities.add(new EntityFactory(predator -> new Predator(predator,2),Predator.startingPredatorCount));
            entities.add(new EntityFactory(Tree::new, 7));
            entities.add(new EntityFactory(Mushroom::new, 7));

            for(EntityFactory entityFactory : entities){
                for (int i = 0; i < entityFactory.total; i++) {
                    Coordinates coordinates = generateEntityCoordinates(gameMap);
                    gameMap.setEntity(coordinates, entityFactory.createEntity(coordinates));
                }
            }
        }

    public void addGrass(GameMap gameMap){
        Coordinates randomCoordinates = generateEntityCoordinates(gameMap);
        gameMap.setEntity(randomCoordinates, new Grass(randomCoordinates));
    }

    public void addHerbivore(GameMap gameMap){
        Coordinates randomCoordinates = generateEntityCoordinates(gameMap);
        gameMap.setEntity(randomCoordinates, new Herbivore(randomCoordinates));
        Herbivore.startingHerbivoreCount++;
    }

    public void addPredator(GameMap gameMap){
        Coordinates randomCoordinates = generateEntityCoordinates(gameMap);
        gameMap.setEntity(randomCoordinates, new Predator(randomCoordinates,2));
        Predator.startingPredatorCount++;
    }

        private Coordinates generateEntityCoordinates(GameMap gameMap){
            Coordinates randomCoordinates;
                do{
                    randomCoordinates = new Coordinates(RANDOM.nextInt(MAP_SIZE), RANDOM.nextInt(MAP_SIZE));
                } while(!isFreeCoordinates(gameMap, randomCoordinates));
                return randomCoordinates;
            }

        private boolean isFreeCoordinates(GameMap gameMap,Coordinates randomCoordinates){
            return gameMap.isSquareEmpty(randomCoordinates);
        }

    }










