package dungeonmania.entities;

import java.io.Serializable;
import java.util.UUID;

import dungeonmania.map.GameMap;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public abstract class MovingEntity implements Entity, Serializable {

    private Position position;
    private Position previousPosition;
    private Position previousDistinctPosition;
    private Direction facing;
    private String entityId;
    private int moveCount = 0;

    public MovingEntity(Position position) {
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
    public abstract boolean canMoveOnto(GameMap map, Entity entity);

    @Override
    public void onDestroy(GameMap map) {
        return;
    }

    @Override
    public void onMovedAway(GameMap map, Entity entity) {
        return;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        return;
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
        this.previousPosition = this.position;
        this.position = position;
        if (!previousPosition.equals(this.position)) {
            this.previousDistinctPosition = this.previousPosition;
        }
    }

    public void swampMove() {
        this.moveCount++;
    }

    public Integer getmoveCount() {
        return this.moveCount;
    }

    public void resetmoveCount() {
        this.moveCount = 0;
    }


    // // use setPosition
    // @Deprecated(forRemoval = true)
    // public void translate(Direction direction) {
    //     previousPosition = this.position;
    //     this.position = Position.translateBy(this.position, direction);
    //     if (!previousPosition.equals(this.position)) {
    //         previousDistinctPosition = previousPosition;
    //     }
    // }

    // // use setPosition
    // @Deprecated(forRemoval = true)
    // public void translate(Position offset) {
    //     previousPosition = this.position;
    //     this.position = Position.translateBy(this.position, offset);
    //     if (!previousPosition.equals(this.position)) {
    //         previousDistinctPosition = previousPosition;
    //     }
    // }
}
