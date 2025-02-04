package Simulation.actions;

import Simulation.Coordinates;
import Simulation.GameMap;
import Simulation.entities.Grass;
import Simulation.entities.Herbivore;
import Simulation.entities.Predator;

import static Simulation.actions.TurnInformationAction.*;

public class EntitySpawnerAction extends Actions {
    private final InitialSpawnAction initializer = new InitialSpawnAction();

    public void execute(GameMap entities) {
        if (countTurn % 3 == 0) {
            Coordinates coordinatesForGrass = initializer.generateEntityCoordinates(entities);
            Grass addGrass = (Grass) initializer.generateEntity(() -> new Grass(coordinatesForGrass));
            entities.setEntity(coordinatesForGrass, addGrass);
        } else if (countTurn % 4 == 0) {
            Coordinates coordinatesForHerbivore = initializer.generateEntityCoordinates(entities);
            Herbivore addHerbivore = (Herbivore) initializer.generateEntity(() -> new Herbivore(coordinatesForHerbivore));
            entities.setEntity(coordinatesForHerbivore, addHerbivore);
            Herbivore.startingHerbivoreCount++;
        } else if (countTurn % 10 == 0) {
            Coordinates coordinatesForPredator = initializer.generateEntityCoordinates(entities);
            Predator addPredator = (Predator) initializer.generateEntity(() -> new Predator(coordinatesForPredator, 2));
            entities.setEntity(coordinatesForPredator, addPredator);
            Predator.startingPredatorCount++;
        }
    }
}
