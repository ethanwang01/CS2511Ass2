package dungeonmania.goals;

import dungeonmania.Game;
import dungeonmania.entities.collectables.Treasure;

public class TreasureGoal extends LeafGoal {

    public TreasureGoal(String type, Integer target) {
        super(type, target);
    }

    @Override
    public boolean achieved(Game game) {
        return game.getInitialTreasureCount() - game.getMap().getEntities(Treasure.class).size() >= this.getTarget();
    }
    
}
