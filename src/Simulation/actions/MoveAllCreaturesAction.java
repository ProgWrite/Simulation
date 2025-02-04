package Simulation.actions;

import Simulation.BreadthFirstSearch;
import Simulation.Coordinates;
import Simulation.GameMap;
import Simulation.entities.Creature;
import Simulation.entities.Entity;
import Simulation.entities.Predator;

import java.util.ArrayList;
import java.util.List;

public class MoveAllCreaturesAction extends Actions {
    private final List<Creature> creatures = new ArrayList<>();
    private final List<Entity> entityPool = new ArrayList<>();
    private final BreadthFirstSearch finder = new BreadthFirstSearch();


    public void execute(GameMap entities){
        updateCreatureAndEntityLists(entities);
        for (Creature creature : creatures) {
            if (!creature.isEaten) {
                performMovement(creature, entities);
            }
            creature.checkDiedOfHunger(creature, entities);
            int startHealth = creature.getHealth();
            creature.setHealth(creature.decreaseHealthByOne(startHealth));
        }
    }

    private void performMovement(Creature creature, GameMap entities) {
        Coordinates start = creature.coordinates;
        int moves = (creature instanceof Predator) ? ((Predator) creature).speed : 1;
        for (int i = 0; i < moves; i++) {
            List<Coordinates> pathToTarget = finder.findPath(start,entities,creature);
            if(pathToTarget.size() > 1) {
                Coordinates nextMove = pathToTarget.get(1);
                creature.makeMove(nextMove, entities);
                start = nextMove;
            }
        }
    }

    private void updateCreatureAndEntityLists(GameMap entities) {
        creatures.clear();
        entityPool.clear();
        entityPool.addAll(entities.getAllEntities());
        for (Entity entity : entityPool) {
            if (entity instanceof Creature) {
                creatures.add((Creature) entity);
            }
        }
    }
}
