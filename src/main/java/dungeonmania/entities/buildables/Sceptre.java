package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;

public class Sceptre extends Buildable {

    private int durability = 1;
    private int mindControlDuration;

    public Sceptre(int mindControlDuration) {
        super(null);
        this.mindControlDuration = mindControlDuration;
    }

    public int getMindControlDuration() {
        return mindControlDuration;
    }

    @Override
    public void use(Game game) {
        durability--;
        if (durability > 0) {
            game.getPlayer().remove(this);
        }
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return BattleStatistics.applyBuff(origin, new BattleStatistics(
            0,
            0,
            0,
            1,
            1));
    }

    @Override
    public int getDurability() {
        return durability;
    }
}
