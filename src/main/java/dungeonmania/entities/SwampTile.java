package dungeonmania.entities;

import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SwampTile extends StaticEntity {
    private int movementFactor;
    public SwampTile(Position position, Integer movementFactor) {
        super(position);
        this.movementFactor = movementFactor;
    }

    public int getMovementFactor() {
        return movementFactor;
    }
    
    public void setMovementFactor(int movementFactor) {
        this.movementFactor = movementFactor;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof MovingEntity) {
            if (((MovingEntity) entity).getMoveCount() < movementFactor) {
                ((MovingEntity) entity).swampMove();
                System.out.println("movecount: " + ((MovingEntity) entity).getMoveCount());
            } else {
                ((MovingEntity) entity).resetMoveCount();
                System.out.println("reset movecount: " + ((MovingEntity) entity).getMoveCount());
            }
        }
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }
}
