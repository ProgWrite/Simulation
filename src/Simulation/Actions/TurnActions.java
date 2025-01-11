package Simulation.Actions;

import Simulation.*;
import Simulation.Entities.*;
import java.util.ArrayList;
import java.util.List;


public class TurnActions{
    List<Creature> listWithCreatures = new ArrayList<>();
    List<Entity> listWithEntities = new ArrayList<>();
    BreadthFirstSearch finder = new BreadthFirstSearch();
    MapConsoleRenderer renderer = new MapConsoleRenderer();
    InitActions initializer = new InitActions();
    private int countTurn = 1;

    public GameState makeTurn(GameMap gameMap) {
        processCreatureTurn(gameMap);
        printInformationAboutTurn(gameMap);
        addEntity(gameMap);
        return determineGameState();
    }

    private void processCreatureTurn(GameMap gameMap){
        updateCreatureAndEntityLists();
        for (Creature creature : listWithCreatures) {
            if(!creature.isEntityEaten){
                performMovement(creature, gameMap);
            }
            creature.checkDiedOfHunger(creature, gameMap);
            creature.health--;
        }
    }

    private void performMovement(Creature creature, GameMap gameMap){
        Coordinates start = creature.coordinates;
        Coordinates end = creature.takeTargetCoordinates(creature);
        int moves = (creature instanceof Predator) ? ((Predator) creature).speed : 1;
        for (int i = 0; i < moves; i++) {
            if (!start.equals(end)) {
                Coordinates nextMove = finder.findPath(start, end, gameMap, creature);
                if (!nextMove.equals(start)) {
                    creature.makeMove(nextMove, gameMap);
                    start = nextMove;
                }
            }
        }
    }

    private void printInformationAboutTurn(GameMap gameMap){
        try {
            Thread.sleep(1000);
            System.out.println();
            System.out.println("------------------------------------------------");
            System.out.println("Turn " + countTurn);
            System.out.println("Количество зайцев " + Herbivore.startingHerbivoreCount);
            System.out.println("Количество волков " + Predator.startingPredatorCount);
            renderer.render(gameMap);
            countTurn++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void updateCreatureAndEntityLists() {
        listWithCreatures.clear();
        listWithEntities.clear();
        listWithEntities.addAll(GameMap.gameMap.values());
        for (Entity entity : GameMap.gameMap.values()) {
            if (entity instanceof Creature) {
                listWithCreatures.add((Creature) entity);
            }
        }
    }

    private void addEntity(GameMap gameMap){
        if (countTurn % 3 == 0) {
            initializer.addGrass(gameMap);
        }
        else if (countTurn % 4 == 0) {
            initializer.addHerbivore(gameMap);
        }
        else if (countTurn % 10 == 0) {
            initializer.addPredator(gameMap);
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