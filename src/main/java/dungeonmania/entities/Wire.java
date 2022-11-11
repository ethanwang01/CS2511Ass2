package dungeonmania.entities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.logical.LogicalEntity;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Wire extends StaticEntity implements Conductor, Subscribe {
    private boolean activated;
    private double activationKey;
    private List<LogicalEntity> logicEnts = new ArrayList<>();
    private List<Conductor> conductors = new ArrayList<>();

    public Wire(Position position) {
        super(position.asLayer(Entity.ITEM_LAYER));
        this.activated = false;
    }

    public void subscribe(Entity e) {
        if (e instanceof LogicalEntity) logicEnts.add((LogicalEntity) e);
        if (e instanceof Conductor) conductors.add((Conductor) e);
    }

    public void unsubscribe(Entity e) {
        if (e instanceof LogicalEntity) logicEnts.remove((LogicalEntity) e);
        if (e instanceof Conductor) conductors.remove((Conductor) e);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    public boolean isActivated() {
        return activated;
    }

    public void activate(double activationKey) {
        if (!isActivated()) {
            this.activated = true;
            this.activationKey = activationKey;
            logicEnts.stream().forEach(b -> b.activate());
            conductors.stream().forEach(b -> b.activate(this.getActivationKey()));
            System.out.println("Wire Activated");
        }
    }

    public void deactivate() {
        if (isActivated()) {
            this.activated = false;
            logicEnts.stream().forEach(b -> b.deactivate());
            conductors.stream().forEach(b -> b.deactivate());
            System.out.println("Wire Deactivated");
        }
    }

    public double getActivationKey() {
        return activationKey;
    }
}
