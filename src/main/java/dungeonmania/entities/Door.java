package dungeonmania.entities;

import dungeonmania.map.GameMap;

import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.collectables.Sunstone;
import dungeonmania.entities.enemies.Spider;
import dungeonmania.entities.inventory.Inventory;
import dungeonmania.util.Position;

public class Door extends StaticEntity {
    private boolean open = false;
    private int number;

    public Door(Position position, int number) {
        super(position.asLayer(Entity.DOOR_LAYER));
        this.number = number;
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        if (open || entity instanceof Spider) {
            return true;
        }
        return (entity instanceof Player && hasKey((Player) entity));
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (!(entity instanceof Player))
            return;

        Player player = (Player) entity;
        Inventory inventory = player.getInventory();
        Key key = inventory.getFirst(Key.class);

        // if the player has the key, use it
        if (hasKey(player)) {
            inventory.remove(key);
            open();
        } 
        // otherwise, check if the player has a sunstone
        else {
            if (hasSunstone(player)) {
                open();
            } 
        }
    }

    private boolean hasSunstone(Player player) {
        Inventory inventory = player.getInventory();
        Sunstone sunstone = inventory.getFirst(Sunstone.class);
        return (sunstone != null);
    }

    private boolean hasKey(Player player) {
        Inventory inventory = player.getInventory();
        Key key = inventory.getFirst(Key.class);

        return ((key != null && key.getnumber() == number) || inventory.getFirst(Sunstone.class) != null);
    }

    public boolean isOpen() {
        return open;
    }

    public void open() {
        open = true;
    }
}
