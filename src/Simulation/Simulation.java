package Simulation;

import Simulation.actions.Actions;
import Simulation.actions.InitialSpawnAction;
import Simulation.entities.Herbivore;
import Simulation.entities.Predator;

import java.util.List;
import java.util.Scanner;

public class Simulation {
    private final static Scanner SCANNER = new Scanner(System.in);
    private final GameMap entities;

    private GameState state = GameState.PLAYING;
    private boolean isPaused = false;
    private final static int OPTION_START_SIMULATION = 1;
    private final static int OPTION_ONE_MOVE = 2;
    private final static int OPTION_PAUSE = 3;
    private final static int RESUME_GAME = 4;
    private final MapConsoleRenderer mapConsoleRenderer = new MapConsoleRenderer();
    private final InitialSpawnAction initialize = new InitialSpawnAction();
    private final List<Actions> turnActions ;

    public Simulation(GameMap entities, List<Actions> turnActions) {
        this.entities = entities;
        this.turnActions = turnActions;
    }

    public void simulateWorld(){
        startMenu();
    }

    private void startMenu(){
        String welcomeMessage = String.format("Добро пожаловать в симуляцию! Перед вами случайно сгенерированный мир! %n" +
               "Введите %d чтобы спокойно наблюдать за миром пока зайцы или волки не одержат победу. %n" +
                       "Введите %d чтобы сделать один ход. %n" +
                       "Вы можете ввести %d в процессе симуляции чтобы поставить её на паузу ", OPTION_START_SIMULATION, OPTION_ONE_MOVE, OPTION_PAUSE
        );
        System.out.println(welcomeMessage);
        showStartGameMapState();
        int input = SCANNER.nextInt();
        checkInput(input);
    }

    private void showStartGameMapState(){
        initialize.execute(entities);
        System.out.println("------------------------------------------------");
        System.out.println("Начальное состояние мира:");
        System.out.println("Количество зайцев " + Herbivore.startingHerbivoreCount);
        System.out.println("Количество волков " + Predator.startingPredatorCount);
        mapConsoleRenderer.render(entities);
    }

    private void checkInput(int input){
        if (input == OPTION_START_SIMULATION){
            startSimulation(entities);
            System.out.println();
        }else if (input == OPTION_ONE_MOVE){
            nextTurn(entities);
        }else if(input == OPTION_PAUSE){
            System.out.printf("Вы не можете поставить симуляцию на паузу, так как игра еще не началась. Введите %d или %d чтобы начать игру ", OPTION_START_SIMULATION, OPTION_ONE_MOVE);
            System.out.println();
            int inputAgain = SCANNER.nextInt();
            checkInput(inputAgain);
        }
        else{
            System.out.printf("Вы ввели неверный символ! Введите %d или %d чтобы начать игру", OPTION_START_SIMULATION, OPTION_ONE_MOVE);
            System.out.println();
            int inputAgain = SCANNER.nextInt();
            checkInput(inputAgain);
        }
    }

    private void startSimulation(GameMap entities) {
        new Thread(this::handleUserInput).start();

        while (state == GameState.PLAYING) {
            for(Actions action : turnActions){
                action.execute(entities);
            }
            state = determineGameState();
            pauseSimulation();
        }
    }

    private void pauseSimulation() {
        synchronized (this) {
            while (isPaused) {
                try {
                    System.out.printf("Игра на паузе. Нажми %d чтобы возобновить игру", RESUME_GAME);
                    System.out.println();
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void nextTurn(GameMap entities) {
        state = determineGameState();
        for(Actions action : turnActions){
            state = determineGameState();
            action.execute(entities);
        }
        if (state != GameState.PLAYING) {
            return;
        }
        getUserDecision(entities);
    }

    private void getUserDecision(GameMap entities) {
        System.out.printf("Вновь сделайте выбор. %d - наблюдать за миром, %d - сделать один ход", OPTION_START_SIMULATION, OPTION_ONE_MOVE);
        System.out.println();
        int input = SCANNER.nextInt();
        System.out.println();
        if (input == OPTION_START_SIMULATION) {
            startSimulation(entities);
        } else if (input == OPTION_ONE_MOVE) {
            nextTurn(entities);
        }else{
            System.out.println("Вы ввели неверный символ!");
            getUserDecision(entities);
        }
    }

    private GameState determineGameState() {
        if (Predator.startingPredatorCount == 0) {
            System.out.println("Игра окончена! Сегодня зайцы смогли спастись от голодных волков!");
            return GameState.HERBIVORE_WINS;

        } else if (Herbivore.startingHerbivoreCount == 0) {
            System.out.println("Игра окончена! Волки вкусно покушали и съели всех зайцев!");
            return GameState.PREDATOR_WINS;
        }
        return GameState.PLAYING;
    }

    private synchronized void pauseGame(){
        isPaused = true;
    }

    private synchronized void resumeGame() {
        isPaused = false;
        synchronized (this) {
            notifyAll();
        }
    }

    private void handleUserInput() {
        while (state == GameState.PLAYING) {
            int input = SCANNER.nextInt();
            if (input == OPTION_PAUSE) {
                pauseGame();
            } else if (input == RESUME_GAME) {
                resumeGame();
            }
        }
    }

}
