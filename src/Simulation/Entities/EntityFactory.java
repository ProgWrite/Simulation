package Simulation.Entities;

import Simulation.Coordinates;

public class EntityFactory {
    final EntityCreator creator;
    public final int total;

    public EntityFactory(EntityCreator creator, int total) {
        this.creator = creator;
        this.total = total;
    }

    public Entity createEntity(Coordinates coordinates) {
        return creator.create(coordinates);
    }

    public interface EntityCreator {
        Entity create(Coordinates coordinates);
    }

}
