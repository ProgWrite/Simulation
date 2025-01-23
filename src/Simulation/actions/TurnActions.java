package Simulation.actions;

import Simulation.*;
import Simulation.entities.*;
import java.util.ArrayList;
import java.util.List;


public class TurnActions{
    List<Creature> creatures = new ArrayList<>();
    List<Entity> entityPool = new ArrayList<>();
    BreadthFirstSearch finder = new BreadthFirstSearch();
    MapConsoleRenderer renderer = new MapConsoleRenderer();
    InitActions initializer = new InitActions();
    private int countTurn = 1;
    private final GameMap entities;

    public TurnActions(GameMap entities){
        this.entities = entities;
    }

    public GameState makeTurn(GameMap entities) {
        processCreatureTurn(entities);
        printInformationAboutTurn(entities);
        addEntity(entities);
        return determineGameState();
    }

    private void processCreatureTurn(GameMap entities){
        updateCreatureAndEntityLists();
        for (Creature creature : creatures) {
            if(!creature.isEntityEaten){
                performMovement(creature, entities);
            }
            creature.checkDiedOfHunger(creature, entities);
            creature.health--;
        }
    }

    private void performMovement(Creature creature, GameMap entities){
        Coordinates start = creature.coordinates;
        Coordinates end = creature.takeTargetCoordinates(creature, entities);
        int moves = (creature instanceof Predator) ? ((Predator) creature).speed : 1;
        for (int i = 0; i < moves; i++) {
            if (!start.equals(end)) {
                Coordinates nextMove = finder.findPath(start, end, entities, creature);
                if (!nextMove.equals(start)) {
                    creature.makeMove(nextMove, entities);
                    start = nextMove;
                }
            }
        }
    }

    private void printInformationAboutTurn(GameMap entities){
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

    private void updateCreatureAndEntityLists() {
        creatures.clear();
        entityPool.clear();
        entityPool.addAll(entities.getAllEntities());
        for (Entity entity : entityPool) {
            if (entity instanceof Creature) {
                creatures.add((Creature) entity);
            }
        }
    }

    private void addEntity(GameMap entities){
        if (countTurn % 3 == 0) {
            initializer.addGrass(entities);
        }
        else if (countTurn % 4 == 0) {
            initializer.addHerbivore(entities);
        }
        else if (countTurn % 10 == 0) {
            initializer.addPredator(entities);
        }
    }

    private GameState determineGameState(){
            if(Predator.startingPredatorCount == 0){
                System.out.println("Игра окончена! Сегодня зайцы смогли спастись от голодных волков!");
                return GameState.HERBIVORE_WINS;

            }else if(Herbivore.startingHerbivoreCount == 0){
                System.out.println("Игра окончена! Волки вкусно покушали и съели всех зайцев!");
                return GameState.PREDATOR_WINS;
            }
            return GameState.PLAYING;
    }

}