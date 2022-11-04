package dungeonmania.goals;

import dungeonmania.Game;

public class EnemyGoal extends LeafGoal {

    public EnemyGoal(String type, Integer target) {
        super(type, target);
    }

    @Override
    public boolean achieved(Game game) {
        return game.getKilledEnemies() >= this.getTarget();
    }

    @Override
    public String toString(Game game) {
        if (this.achieved(game)) {
            return "";
        }
        return ":enemies";
    }
}
