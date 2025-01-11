package Simulation;
import Simulation.Entities.Entity;

public class MapConsoleRenderer{
    private final static int MAP_SIZE = 10;

    public void render(GameMap gameMap){
        for(int rows = 0; rows < MAP_SIZE; rows++){
            StringBuilder line = new StringBuilder();
            for(int columns = 0 ; columns < MAP_SIZE; columns++){
                Coordinates coordinates = new Coordinates(columns, rows);
                if (gameMap.isSquareEmpty(coordinates)) {
                    line.append(renderEmptySquare(coordinates));
                }
                else{
                    line.append(getEntitySquare(gameMap.getEntity(coordinates)));
                }
            }
            System.out.println(line);
        }
    }

    private String renderEmptySquare(Coordinates coordinates){
        return (" " + " ." +"  ");
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
        }
        return "?";
    }

    private String getEntitySquare(Entity entity) {
        return (" " + selectPictureForEntity(entity) + " ");
    }
}


