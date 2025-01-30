package Simulation;
import Simulation.entities.Entity;

public class MapConsoleRenderer{

    public void render(GameMap entities){
        for(int row = 0; row < entities.getHeight(); row++){
            StringBuilder line = new StringBuilder();
            for(int column = 0; column < entities.getWidth(); column++){
                Coordinates coordinates = new Coordinates(column, row);
                if (entities.isSquareEmpty(coordinates)) {
                    line.append(renderEmptySquare(coordinates));
                }
                else{
                    line.append(getEntitySquare(entities.getEntity(coordinates)));
                }
            }
            System.out.println(line);
        }
    }

    private String renderEmptySquare(Coordinates coordinates){
        return ("  .  ");
    }

    private String selectPictureForEntity(Entity entity){
        switch (entity.getClass().getSimpleName()){
            case "Grass":
                return "\uD83C\uDF3F ";
            case "Mushroom":
                return "\uD83C\uDF44 ";
            case "Tree":
                return "\uD83C\uDF32 ";
            case "Herbivore":
                return "\uD83D\uDC07 ";
            case "Predator":
                return "\uD83D\uDC3A ";
            default:
                return "?";
        }
    }

    private String getEntitySquare(Entity entity) {
        return (" " + selectPictureForEntity(entity) + " ");
    }
}


