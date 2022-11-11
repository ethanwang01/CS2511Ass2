package dungeonmania.entities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.collectables.Bomb;
import dungeonmania.entities.logical.LogicalEntity;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Switch extends StaticEntity implements Subscribe, Conductor {
    private boolean activated;
    private double activationKey;
    private List<Bomb> bombs = new ArrayList<>();
    private List<LogicalEntity> logicEnts = new ArrayList<>();
    private List<Conductor> conductors = new ArrayList<>();

    public Switch(Position position) {
        super(position.asLayer(Entity.ITEM_LAYER));
        activationKey = Math.random();
    }

    public void subscribe(Entity e) {
        if (e instanceof Bomb) bombs.add((Bomb) e);
        if (e instanceof LogicalEntity) logicEnts.add((LogicalEntity) e);
        if (e instanceof Conductor) conductors.add((Conductor) e);
    }

    public void subscribe(Bomb bomb, GameMap map) {
        bombs.add(bomb);
        if (activated) {
            bombs.stream().forEach(b -> b.notify(map));
        }
    }

    public void unsubscribe(Entity e) {
        if (e instanceof Bomb) bombs.remove((Bomb) e);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Boulder) {
            activated = true;
            activationKey++;
            bombs.stream().forEach(b -> b.notify(map));
            logicEnts.stream().forEach(b -> b.activate());
            System.out.print(getActivationKey());
            conductors.stream().forEach(b -> b.activate(this.getActivationKey()));
        }
    }

    @Override
    public void onMovedAway(GameMap map, Entity entity) {
        if (entity instanceof Boulder) {
            activated = false;
            logicEnts.stream().forEach(b -> b.deactivate());
            conductors.stream().forEach(b -> b.deactivate());
        }
    }

    public boolean isActivated() {
        return activated;
    }

    public void activate() {
        this.activated = true;
    }
    public void deactivate() {
        this.activated = false;
    }

    public void activate(double activationKey) {
    }

    public double getActivationKey() {
        return activationKey;
    }
}
