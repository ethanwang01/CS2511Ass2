package dungeonmania.entities.collectables;

import dungeonmania.entities.CollectableEntity;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.StaticEntity;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Wood extends CollectableEntity implements InventoryItem {
    public Wood(Position position) {
        super(position);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Player) {
            if (!((Player) entity).pickUp(this)) return;
            map.destroyEntity(this);
        }
    }
}
