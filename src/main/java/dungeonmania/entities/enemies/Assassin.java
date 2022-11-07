package dungeonmania.entities.enemies;

import java.util.Random;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Assassin extends MercenaryParent {
    public static final int DEFAULT_BRIBE_AMOUNT = 1;
    public static final int DEFAULT_BRIBE_RADIUS = 1;
    public static final double DEFAULT_ATTACK = 5.0;
    public static final double DEFAULT_HEALTH = 10.0;
    public static final long DEFAULT_SEED = 21;
    public static final double DEFAULT_BRIBE_RATE = 0.5;

    private int bribeAmount;
    private int bribeRadius;
    private boolean allied = false;
    private double bribeFailRate;

    private Random random;

    public Assassin(Position position, double health, double attack, int bribeAmount,
        int bribeRadius, double bribeFailRate) {
        super(position, health, attack, bribeAmount, bribeRadius);
        this.bribeAmount = bribeAmount;
        this.bribeRadius = bribeRadius;
        random = new Random(DEFAULT_SEED);
    }

    public Assassin(Position position, double health, double attack, int bribeAmount, int bribeRadius,
        double bribeFailRate, long seed) {
        super(position, health, attack, bribeAmount, bribeRadius);
        random = new Random(seed);
    }

    private boolean bribe(Player player) {
        for (int i = 0; i < bribeAmount; i++) {
            player.use(Treasure.class);
        }
        if (random.nextFloat() > bribeFailRate) {
            return true;
        }
        return false;
    }

     /**
     * check whether the current merc can be bribed
     * @param player
     * @return
     */
    private boolean canBeBribed(Player player) {
        return bribeRadius >= 0 && player.countEntityOfType(Treasure.class) >= bribeAmount;
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
                if (this.getMoveCount() == 0) {
                    nextPos = map.dijkstraPathFind(getPosition(), map.getPlayer().getPosition(), this);
                    map.moveTo(this, nextPos);
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
                    System.out.println("dijk move ally\n");
                    if (this.getMoveCount() == 0) {
                        map.moveTo(this, map.dijkstraPathFind(this.getPosition(), map.getPlayer().getPosition(), this));
                    }
                }
            // if curr position is adjacent to player before they move, follow player's last distinct position
            } else {
                System.out.println("follow player");
                if (this.getMoveCount() == 0) {
                    map.moveTo(this, map.getPlayer().getPreviousDistinctPosition());
                }
            }
            System.out.print("Player prev: " + map.getPlayer().getPreviousPosition() + "\n");
            System.out.print("Player curr: " + map.getPlayer().getPosition() + "\n");
            System.out.print("Player prevdist: " + map.getPlayer().getPreviousDistinctPosition() + "\n");
            System.out.print("merc curr: " + this.getPosition() + "\n");
        }
    }

    @Override
    public void interact(Player player, Game game) {
        System.out.println(allied);
        if (bribe(player)) {
            allied = true;
        }
        System.out.println(allied);
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (this.allied) return;
        super.onOverlap(map, entity);
    }
    
    @Override
    public boolean isInteractable(Player player) {
        return !allied && canBeBribed(player);
    }
}
