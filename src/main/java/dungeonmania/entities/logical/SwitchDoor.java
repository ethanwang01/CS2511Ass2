package dungeonmania.entities.logical;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SwitchDoor extends LogicalEntity {

    public SwitchDoor(Position position, String logic) {
        super(position, logic);
        this.setType("switch_door_closed");
    }

    @Override
    public void activate() {
        super.activate();
        if (isActivated()) {
            this.setType("switch_door_open");
            System.out.println("switchdoor opened");
        }
    }

    @Override
    public void deactivate() {
        super.deactivate();
        if (!isActivated()) {
            this.setType("switch_door_closed");
            System.out.println("switchdoor closed");
        }
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return isActivated();
    }
}
