package dungeonmania.entities.buildables;

import dungeonmania.entities.BattleItem;
import dungeonmania.entities.CollectableEntity;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.util.Position;

public abstract class Buildable extends CollectableEntity implements InventoryItem, BattleItem {

    public Buildable(Position position) {
        super(position);
    }
}
