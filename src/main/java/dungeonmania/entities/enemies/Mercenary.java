package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.Player;
import dungeonmania.entities.buildables.Sceptre;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Mercenary extends Enemy implements Interactable {
    public static final int DEFAULT_BRIBE_AMOUNT = 1;
    public static final int DEFAULT_BRIBE_RADIUS = 1;
    public static final double DEFAULT_ATTACK = 5.0;
    public static final double DEFAULT_HEALTH = 10.0;

    private int bribeAmount = Mercenary.DEFAULT_BRIBE_AMOUNT;
    private int bribeRadius = Mercenary.DEFAULT_BRIBE_RADIUS;
    private boolean allied = false;

    public Mercenary(Position position, double health, double attack, int bribeAmount, int bribeRadius) {
        super(position, health, attack);
        this.bribeAmount = bribeAmount;
        this.bribeRadius = bribeRadius;
    }

    public boolean isAllied() {
        return allied;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (allied) return;
        super.onOverlap(map, entity);
    }

    /**
     * check whether the current merc can be bribed
     * @param player
     * @return
     */
    private boolean canBeBribed(Player player) {
        return bribeRadius >= 0 && (player.countEntityOfType(Treasure.class) >= bribeAmount || 
            player.countEntityOfType(Sceptre.class) > 0);
    }

    /**
     * bribe the merc
     */
    private void bribe(Player player) {
        if (player.countEntityOfType(Treasure.class) >= bribeAmount) {
            for (int i = 0; i < bribeAmount; i++) {
                player.use(Treasure.class);
            }
        } else {
            player.use(Sceptre.class);
        }
        System.out.println("bribed");
    }

    @Override
    public void interact(Player player, Game game) {
        allied = true;
        bribe(player);
    }

    @Override
    public void move(Game game) {
        Position nextPos;
        GameMap map = game.getMap();
        // if !allied
        if (!allied) {
             // if dijkstra path is same as position player moves to, stay
            if (map.dijkstraPathFind(getPosition(), map.getPlayer().getPosition(), this)
                .equals(map.getPlayer().getPosition())) {
                System.out.println("Cannot move \n");
                map.moveTo(this, this.getPosition());
                return;
            } else {
                System.out.print("Dijk move \n");
                nextPos = map.dijkstraPathFind(getPosition(), map.getPlayer().getPosition(), this);
                map.moveTo(this, nextPos);
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
                    System.out.println("dijk move ally\n");
                    map.moveTo(this, map.dijkstraPathFind(this.getPosition(), map.getPlayer().getPosition(), this));
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
    public boolean isInteractable(Player player) {
        return !allied && canBeBribed(player);
    }
}
