package dungeonmania.entities;

import dungeonmania.map.GameMap;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public interface EntityInterface {
    Position getPosition();
    Position getPreviousPosition();
    Position getPreviousDistinctPosition();
    boolean canMoveOnto(GameMap map, Entity entity);
    void onOverlap(GameMap map, Entity entity);
    void onDestroy(GameMap map);
    void onMovedAway(GameMap map, Entity entity);
    String getId();
    void setPosition(Position position);
    void setFacing(Direction facing);
    Direction getFacing();
}
