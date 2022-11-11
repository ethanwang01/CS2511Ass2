package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.Player;
import dungeonmania.entities.buildables.Sceptre;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public abstract class MercenaryParent extends Enemy implements Interactable {
    public static final int DEFAULT_BRIBE_AMOUNT = 1;
    public static final int DEFAULT_BRIBE_RADIUS = 1;
    public static final double DEFAULT_ATTACK = 5.0;
    public static final double DEFAULT_HEALTH = 10.0;
    public static final int DEFAULT_MIND_CONTROL_DURATION = 10;

    private int bribeAmount = MercenaryParent.DEFAULT_BRIBE_AMOUNT;
    private int bribeRadius = MercenaryParent.DEFAULT_BRIBE_RADIUS;
    private boolean allied = false;
    private boolean mindContolled = false;
    private int mindControlDuration = MercenaryParent.DEFAULT_MIND_CONTROL_DURATION;

    public MercenaryParent(Position position, double health, double attack, int bribeAmount,
        int bribeRadius, int mindControlDuration) {
        super(position, health, attack);
        this.bribeAmount = bribeAmount;
        this.bribeRadius = bribeRadius;
        this.mindControlDuration = mindControlDuration;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (this.allied) return;
        super.onOverlap(map, entity);
    }

    public boolean isAllied() {
        return allied;
    }
    /**
     * check whether the current merc can be bribed
     * @param player
     * @return
     */
    protected boolean canBeBribed(Player player) {
        boolean canBribe = false;
        if (this.bribeRadius >= 0 && (player.countEntityOfType(Treasure.class) >= this.bribeAmount
            || player.countEntityOfType(Sceptre.class) >= 1)) {
                canBribe = true;
            }
        return canBribe;
    }

    /**
     * bribe the merc
     */
    public boolean bribe(Player player) {
        for (int i = 0; i < this.bribeAmount; i++) {
            player.use(Treasure.class);
        }
        return true;
    }

    private boolean mindControl(Player player) {
        // mindControlDuration = player.getInventory().getEntities(Sceptre.class).get(0).getMindControlDuration();
        player.use(Sceptre.class);
        return true;
    }

    @Override
    public void interact(Player player, Game game) {
        if (player.countEntityOfType(Sceptre.class) >= 1) {
            mindControl(player);
            this.allied = true;
            this.mindContolled = true;
        } else if (bribe(player)) {
            this.allied = true;
        }
    }

    @Override
    public void move(Game game) {
        Position nextPos;
        GameMap map = game.getMap();
        System.out.println("LETS GO");
        // if !allied
        if (!isAllied()) {
             // if dijkstra path is same as position player moves to, stay
            if (map.dijkstraPathFind(getPosition(), map.getPlayerPosition(), this)
                .equals(map.getPlayerPosition())) {
                // System.out.println("Cannot move \n");
                map.moveTo(this, this.getPosition());
                return;
            } else {
                // System.out.print("Dijk move \n");
                if (this.getmoveCount() == 0) {
                    nextPos = map.dijkstraPathFind(getPosition(), map.getPlayerPosition(), this);
                    map.moveTo(this, nextPos);
                } else {
                    map.moveTo(this, this.getPosition());
                }
            }
        // if allied
        } else {
            // if curr position is not adjacent to player before they move
            if (!Position.isAdjacent(this.getPosition(), map.getPlayer().getPreviousPosition())) {
                // if dijkstra path is same as position player moves to, stay
                if (map.dijkstraPathFind(this.getPosition(), map.getPlayerPosition(), this)
                    .equals(map.getPlayer().getPosition())) {
                    // System.out.println("Stay\n");
                    map.moveTo(this, this.getPosition());
                } else {
                    if (this.getmoveCount() == 0) {
                        // System.out.println("dijk move ally\n");
                        map.moveTo(this, map.dijkstraPathFind(this.getPosition(), map.getPlayerPosition(), this));
                    } else {
                        // System.out.println("swamp move\n");
                        map.moveTo(this, this.getPosition());
                    }
                }
            // if curr position is adjacent to player before they move, follow player's last distinct position
            } else {
                // System.out.println("follow player");
                map.moveTo(this, map.getPlayer().getPreviousDistinctPosition());
            }
            // System.out.print("Allied: " + allied + "\n");
            // System.out.print("Turns Merc: " + mindControlDuration + "\n");
            // System.out.print("Player curr: " + map.getPlayerPosition() + "\n");
            // System.out.print("Player prev: " + map.getPlayer().getPreviousPosition() + "\n");
            // System.out.print("merc curr:   " + this.getPosition() + "\n");
            // System.out.print("Player prevdist: " + map.getPlayer().getPreviousDistinctPosition() + "\n");
        }

        updateMindControl();
    }

    private void updateMindControl() {
        if (!this.mindContolled) return;

        this.mindControlDuration -= 1;
        if (this.mindControlDuration < 0) {
            this.allied = false;
            this.mindContolled = false;
        } else {
            this.allied = true;
            this.mindContolled = true;
        }
    }

    @Override
    public boolean isInteractable(Player player) {
        return !this.allied && canBeBribed(player);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

}
