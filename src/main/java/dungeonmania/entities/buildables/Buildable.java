package dungeonmania.entities.buildables;

import java.io.Serializable;

import dungeonmania.entities.BattleItem;
import dungeonmania.entities.CollectableEntity;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.util.Position;

public abstract class Buildable extends CollectableEntity implements InventoryItem, BattleItem, Serializable {

    public Buildable(Position position) {
        super(position);
    }
}
