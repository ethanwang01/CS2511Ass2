package dungeonmania.entities.enemies;

import java.util.Random;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Assassin extends Mercenary {
    public static final int DEFAULT_BRIBE_AMOUNT = 1;
    public static final int DEFAULT_BRIBE_RADIUS = 1;
    public static final double DEFAULT_ATTACK = 5.0;
    public static final double DEFAULT_HEALTH = 10.0;
    private long DEFAULT_SEED = 21;
    

    private int bribeAmount = Mercenary.DEFAULT_BRIBE_AMOUNT;
    private int bribeRadius = Mercenary.DEFAULT_BRIBE_RADIUS;
    private boolean allied = false;
    private double bribeFailRate = 0.5;

    private Random random;

    public Assassin(Position position, double health, double attack, int bribeAmount, int bribeRadius) {
        super(position, health, attack, bribeAmount, bribeRadius);
        random = new Random(DEFAULT_SEED);
    }

    public Assassin(Position position, double health, double attack, int bribeAmount, int bribeRadius,
        long seed) {
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

    @Override
    public void interact(Player player, Game game) {
        if (bribe(player)) {
            allied = true;
        }
    }
}
