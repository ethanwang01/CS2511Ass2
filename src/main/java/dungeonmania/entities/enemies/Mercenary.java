package dungeonmania.entities.enemies;

import dungeonmania.entities.Entity;
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
    private boolean mindContolled = false;
    private int mindControlDuration = MercenaryParent.DEFAULT_MIND_CONTROL_DURATION;

    public Mercenary(Position position, double health, double attack, int bribeAmount, int bribeRadius,
        int mindControlDuration) {
        super(position, health, attack, bribeAmount, bribeRadius, mindControlDuration);
        this.bribeAmount = bribeAmount;
        this.bribeRadius = bribeRadius;
        this.mindControlDuration = -1;
    }

    public boolean isAllied() {
        return allied;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (this.allied) return;
        super.onOverlap(map, entity);
    }


    /**
     * check whether the current merc can be bribed
     * @param player
     * @return
     */
    // protected boolean canBeBribed(Player player) {
    //     boolean canBribe = false;
    //     if (bribeRadius >= 0 && ( player.countEntityOfType(Treasure.class) >= bribeAmount
    //         || player.countEntityOfType(Sceptre.class) >= 1)) {
    //             canBribe = true;
    //         }
    //     return canBribe;
    // }

    /**
     * bribe the merc
     */
    // private boolean bribe(Player player) {
    //     for (int i = 0; i < bribeAmount; i++) {
    //         player.use(Treasure.class);
    //     }
    //     return true;
    // }

    // private boolean mindControl(Player player) {
    //     mindControlDuration = player.getInventory().getEntities(Sceptre.class).get(0).getMindControlDuration();
    //     player.use(Sceptre.class);
    //     return true;
    // }

    // @Override
    // public void interact(Player player, Game game) {
    //     if (player.countEntityOfType(Sceptre.class) >= 1) {
    //         mindControl(player);
    //         this.allied = true;
    //         this.mindContolled = true;
    //     } else if (bribe(player)) {
    //         this.allied = true;
    //     }
    // }

    // @Override
    // public void move(Game game) {
    //     Position nextPos;
    //     GameMap map = game.getMap();

    //     // System.out.print("Pre  MERC: " + this.allied + " " + this.mindContolled);
    //     // if (this.allied || this.mindContolled) {
    //     //     System.out.println(" Allied");
    //     // } else {
    //     //     System.out.println(" Unallied");
    //     // }

    //     // if !allied
    //     if (!this.allied) {
    //          // if dijkstra path is same as position player moves to, stay
    //         if (map.dijkstraPathFind(getPosition(), map.getPlayer().getPosition(), this)
    //             .equals(map.getPlayer().getPosition())) {
    //             // System.out.println("Cannot move \n");
    //             map.moveTo(this, this.getPosition());
    //             return;
    //         } else {
    //             // System.out.print("Dijk move \n");
    //             nextPos = map.dijkstraPathFind(getPosition(), map.getPlayer().getPosition(), this);
    //             map.moveTo(this, nextPos);
    //         }
    //     // if allied
    //     } else {
    //         // if curr position is not adjacent to player before they move
    //         if (!Position.isAdjacent(this.getPosition(), map.getPlayer().getPreviousPosition())) {
    //             // if dijkstra path is same as position player moves to, stay
    //             if (map.dijkstraPathFind(this.getPosition(), map.getPlayer().getPosition(), this)
    //                 .equals(map.getPlayer().getPosition())) {
    //                 // System.out.println("Stay\n");
    //                 map.moveTo(this, this.getPosition());
    //             } else {
    //                 // System.out.println("dijk move ally\n");
    //                 map.moveTo(this, map.dijkstraPathFind(this.getPosition(), map.getPlayer().getPosition(), this));
    //             }
    //         // if curr position is adjacent to player before they move, follow player's last distinct position
    //         } else {
    //             // System.out.println("follow player");
    //             map.moveTo(this, map.getPlayer().getPreviousDistinctPosition());
    //         }
    //         // System.out.print("Player prev: " + map.getPlayer().getPreviousPosition() + "\n");
    //         // System.out.print("Player curr: " + map.getPlayer().getPosition() + "\n");
    //         // System.out.print("Player prevdist: " + map.getPlayer().getPreviousDistinctPosition() + "\n");
    //         // System.out.print("merc curr: " + this.getPosition() + "\n");
    //     }


    //     updateMindControl();
    // }

    // @Override
    // public void updateMindControl() {
    //     // System.out.println(this.mindControlDuration);

    //     if (!this.mindContolled) return;

    //     this.mindControlDuration -= 1;
    //     if (this.mindControlDuration < 0) {
    //         this.allied = false;
    //         this.mindContolled = false;
    //     } else {
    //         this.allied = true;
    //         this.mindContolled = true;
    //     }
    // }

    // @Override
    // public boolean isInteractable(Player player) {
    //     return !this.allied && canBeBribed(player);
    // }
}
