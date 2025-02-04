package Simulation.actions;

import Simulation.GameMap;
import Simulation.MapConsoleRenderer;
import Simulation.entities.Herbivore;
import Simulation.entities.Predator;

public class TurnInformationAction extends Actions {
    protected static int countTurn = 1;
    private final MapConsoleRenderer renderer = new MapConsoleRenderer();

    public void execute(GameMap entities) {
        try {
            Thread.sleep(1000);
            System.out.println();
            System.out.println("------------------------------------------------");
            System.out.println("Turn " + countTurn);
            System.out.println("Количество зайцев " + Herbivore.startingHerbivoreCount);
            System.out.println("Количество волков " + Predator.startingPredatorCount);
            renderer.render(entities);
            countTurn++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
