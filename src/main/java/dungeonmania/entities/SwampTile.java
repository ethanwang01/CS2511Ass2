package dungeonmania.entities;

import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SwampTile extends StaticEntity {
    private int movementFactor;
    public SwampTile(Position position, Integer movementFactor) {
        super(position);
        this.movementFactor = movementFactor;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof MovingEntity) {
            if (((MovingEntity) entity).getmoveCount() < this.movementFactor) {
                ((MovingEntity) entity).swampMove();
                System.out.println("movecount: " + ((MovingEntity) entity).getmoveCount());
            } else {
                ((MovingEntity) entity).resetmoveCount();
                System.out.println("reset movecount: " + ((MovingEntity) entity).getmoveCount());
            }
        }
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }
}
