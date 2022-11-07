package dungeonmania.goals;

import dungeonmania.Game;
import dungeonmania.entities.collectables.Sunstone;
import dungeonmania.entities.collectables.Treasure;

public class TreasureGoal extends LeafGoal {

    public TreasureGoal(String type, Integer target) {
        super(type, target);
    }

    @Override
    public boolean achieved(Game game) {
        return (game.getInitialTreasureCount() - game.getMap().getEntities(Treasure.class).size()
            + game.getPlayer().getInventory().getEntities(Sunstone.class).size()) >= this.getTarget();
    }

    @Override
    public String toString(Game game) {
        if (this.achieved(game)) {
            return "";
        }
        return ":treasure";
    }

}
