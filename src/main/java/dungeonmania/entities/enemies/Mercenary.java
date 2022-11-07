package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Mercenary extends MercenaryParent {
    public static final int DEFAULT_BRIBE_AMOUNT = 1;
    public static final int DEFAULT_BRIBE_RADIUS = 1;
    public static final double DEFAULT_ATTACK = 5.0;
    public static final double DEFAULT_HEALTH = 10.0;

    private int bribeAmount = Mercenary.DEFAULT_BRIBE_AMOUNT;
    private int bribeRadius = Mercenary.DEFAULT_BRIBE_RADIUS;
    private boolean allied = false;

    public Mercenary(Position position, double health, double attack, int bribeAmount, int bribeRadius) {
        super(position, health, attack, bribeAmount, bribeRadius);
        this.bribeAmount = bribeAmount;
        this.bribeRadius = bribeRadius;
    }

    public boolean isAllied() {
        return allied;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (isAllied()) return;
        super.onOverlap(map, entity);
    }

    /**
     * check whether the current merc can be bribed
     * @param player
     * @return
     */
    private boolean canBeBribed(Player player) {
        return bribeRadius >= 0 && player.countEntityOfType(Treasure.class) >= bribeAmount;
    }

    /**
     * bribe the merc
     */
    private boolean bribe(Player player) {
        for (int i = 0; i < bribeAmount; i++) {
            player.use(Treasure.class);
        }
        return true;
    }

    @Override
    public void interact(Player player, Game game) {
        if (bribe(player)) {
            allied = true;
        }
    }

    @Override
    public void move(Game game) {
        Position nextPos;
        GameMap map = game.getMap();
        // if !allied
        if (!isAllied()) {
             // if dijkstra path is same as position player moves to, stay
            if (map.dijkstraPathFind(getPosition(), map.getPlayer().getPosition(), this)
                .equals(map.getPlayer().getPosition())) {
                System.out.println("Cannot move \n");
                map.moveTo(this, this.getPosition());
                return;
            } else {
                System.out.print("Dijk move \n");
                if (this.getMoveCount() == 0) {
                    nextPos = map.dijkstraPathFind(getPosition(), map.getPlayer().getPosition(), this);
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
                if (map.dijkstraPathFind(this.getPosition(), map.getPlayer().getPosition(), this)
                    .equals(map.getPlayer().getPosition())) {
                    System.out.println("Stay\n");
                    map.moveTo(this, this.getPosition());
                } else {
                    if (this.getMoveCount() == 0) {
                        System.out.println("dijk move ally\n");
                        map.moveTo(this, map.dijkstraPathFind(this.getPosition(), map.getPlayer().getPosition(), this));
                    } else {
                        System.out.println("swamp move\n");
                        map.moveTo(this, this.getPosition());
                    }
                }
            // if curr position is adjacent to player before they move, follow player's last distinct position
            } else {
                System.out.println("follow player");
                map.moveTo(this, map.getPlayer().getPreviousDistinctPosition());
            }
            System.out.print("Player prev: " + map.getPlayer().getPreviousPosition() + "\n");
            System.out.print("Player curr: " + map.getPlayer().getPosition() + "\n");
            System.out.print("Player prevdist: " + map.getPlayer().getPreviousDistinctPosition() + "\n");
            System.out.print("merc curr: " + this.getPosition() + "\n");
        }
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    @Override
    public boolean isInteractable(Player player) {
        return !isAllied() && canBeBribed(player);
    }
}
