package Simulation;

import Simulation.actions.Actions;
import Simulation.actions.EntitySpawnerAction;
import Simulation.actions.MoveAllCreaturesAction;
import Simulation.actions.TurnInformationAction;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        GameMap entities = new GameMap(10, 10);
        MoveAllCreaturesAction moveAction = new MoveAllCreaturesAction();
        TurnInformationAction informationAction = new TurnInformationAction();
        EntitySpawnerAction entitySpawnerAction = new EntitySpawnerAction();
        List<Actions> turnActions = List.of(moveAction, informationAction, entitySpawnerAction);
        Simulation creator = new Simulation(entities, turnActions);
        creator.simulateWorld();
    }
}
