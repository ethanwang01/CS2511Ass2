package dungeonmania.goals;

import dungeonmania.Game;
import dungeonmania.entities.enemies.Enemy;

public class EnemyGoal extends LeafGoal {

    public EnemyGoal(String type, Integer target) {
        super(type, target);
    }

    @Override
    public boolean achieved(Game game) {
        return game.getInitialEnemyCount() - game.getMap().getEntities(Enemy.class).size() >= this.getTarget();
    }

    @Override
    public String toString(Game game) {
        if (this.achieved(game)) {
            return "";
        }
        return ":enemies";
    }
}
