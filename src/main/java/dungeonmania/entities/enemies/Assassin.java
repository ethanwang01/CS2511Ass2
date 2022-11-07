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
            int bribeRadius, double bribeFailRate, int mindControlDuration) {
        super(position, health, attack, bribeAmount, bribeRadius, mindControlDuration);
        this.bribeAmount = bribeAmount;
        this.bribeRadius = bribeRadius;
        random = new Random(DEFAULT_SEED);
    }

    public Assassin(Position position, double health, double attack, int bribeAmount, int bribeRadius,
            double bribeFailRate, long seed, int mindControlDuration) {
        super(position, health, attack, bribeAmount, bribeRadius, mindControlDuration);
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
}
