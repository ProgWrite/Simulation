package Simulation;


import Simulation.actions.TurnActions;

public class Main {

    public static void main(String[] args) {
            GameMap entities = new GameMap(10,10);
            TurnActions turn = new TurnActions(entities);
            Simulation creator = new Simulation(entities, turn);
            creator.simulateWorld();
    }
}
