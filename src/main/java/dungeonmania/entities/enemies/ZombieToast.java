package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.SwampTile;
import dungeonmania.entities.Wall;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class ZombieToast extends Enemy {
    public static final double DEFAULT_HEALTH = 5.0;
    public static final double DEFAULT_ATTACK = 6.0;

    public ZombieToast(Position position, double health, double attack) {
        super(position, health, attack);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return entity instanceof Wall || entity instanceof Player
        || entity instanceof SwampTile || entity instanceof ZombieToast;
    }
    
    @Override
    public void move(Game game) {
        GameMap map = game.getMap();
        if (this.getMoveCount() == 0) {
            System.out.println("reg move:   ");
            System.out.println("zombie pos: " + this.getPosition());
            moveRandom(game);
        } else {
            map.moveTo(this, this.getPosition());
            System.out.println("swamp move: ");
            System.out.println("zombie pos: " + this.getPosition());
        }
    }

}
