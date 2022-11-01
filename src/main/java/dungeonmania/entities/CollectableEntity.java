package dungeonmania.entities;

import java.util.UUID;

import dungeonmania.map.GameMap;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public abstract class CollectableEntity implements Entity {
    public static final int FLOOR_LAYER = 0;
    public static final int ITEM_LAYER = 1;
    public static final int DOOR_LAYER = 2;
    public static final int CHARACTER_LAYER = 3;

    private Position position;
    private Position previousPosition;
    private Position previousDistinctPosition;
    private Direction facing;
    private String entityId;

    public CollectableEntity(Position position) {
        this.position = position;
        this.previousPosition = position;
        this.previousDistinctPosition = null;
        this.entityId = UUID.randomUUID().toString();
        this.facing = null;
    }

    @Override
    public String getId() {
        return this.entityId;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public Position getPreviousDistinctPosition() {
        return this.previousDistinctPosition;
    }

    @Override
    public Position getPreviousPosition() {
        return this.previousPosition;
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return false;
    }

    @Override
    public void onDestroy(GameMap map) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onMovedAway(GameMap map, Entity entity) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        // TODO
    }

    @Override
    public Direction getFacing() {
        return this.facing;
    }

    @Override
    public void setFacing(Direction facing) {
        this.facing = facing;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }
    // use setPosition
    @Deprecated(forRemoval = true)
    public void translate(Direction direction) {
        previousPosition = this.position;
        this.position = Position.translateBy(this.position, direction);
        if (!previousPosition.equals(this.position)) {
            previousDistinctPosition = previousPosition;
        }
    }

    // use setPosition
    @Deprecated(forRemoval = true)
    public void translate(Position offset) {
        this.position = Position.translateBy(this.position, offset);
    }
}
