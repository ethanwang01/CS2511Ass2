package dungeonmania.entities.collectables;

import dungeonmania.entities.CollectableEntity;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Key extends CollectableEntity implements InventoryItem {
    private int number;

    public Key(Position position, int number) {
        super(position);
        this.number = number;
    }

    // @Override
    // public boolean canMoveOnto(GameMap map, Entity entity) {
    //     return true;
    // }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Player) {
            if (((Player) entity).getInventory().getFirst(Key.class) != null) return;
            if (!((Player) entity).pickUp(this)) return;
            map.destroyEntity(this);
        }
    }

    public int getnumber() {
        return number;
    }

}
