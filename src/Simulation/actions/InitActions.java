package Simulation.actions;

import Simulation.Coordinates;
import Simulation.GameMap;
import Simulation.entities.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class InitActions extends Actions{
        private final static Random RANDOM = new Random();
        private final List<EntityFactory> entityPool = new ArrayList<>();

        public void setupRandomStartEntitiesPositions(GameMap entities){
            entityPool.add(new EntityFactory(Herbivore::new, Herbivore.startingHerbivoreCount));
            entityPool.add(new EntityFactory(Grass::new, 15));
            entityPool.add(new EntityFactory(predator -> new Predator(predator,2),Predator.startingPredatorCount));
            entityPool.add(new EntityFactory(Tree::new, 7));
            entityPool.add(new EntityFactory(Mushroom::new, 7));

            for(EntityFactory entityFactory : entityPool){
                for (int i = 0; i < entityFactory.total; i++) {
                    Coordinates coordinates = generateEntityCoordinates(entities);
                    entities.setEntity(coordinates, entityFactory.createEntity(coordinates));
                }
            }
        }

    protected Entity generateEntity(Supplier<Entity> supplier) {
        return supplier.get();
    }


    protected Coordinates generateEntityCoordinates(GameMap entities) {
        Coordinates randomCoordinates;
        do {
            randomCoordinates = new Coordinates(RANDOM.nextInt(entities.getHeight()), RANDOM.nextInt(entities.getWidth()));
        } while (!isFreeCoordinates(entities, randomCoordinates));
        return randomCoordinates;
    }

    private boolean isFreeCoordinates(GameMap entities, Coordinates randomCoordinates) {
        return entities.isSquareEmpty(randomCoordinates);
    }

}














