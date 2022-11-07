package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;

public class MidnightArmour extends Buildable {
    private int durability = 1; // Durabulity will never go down
    private double attack;
    private double defence;

    public MidnightArmour(double attack, double defence) {
        super(null);
        this.attack = defence;
        this.defence = defence;
    }

    @Override
    public void use(Game game) {
        if (durability <= 0) {
            game.getPlayer().remove(this);
        }
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return BattleStatistics.applyBuff(origin, new BattleStatistics(
            0,
            attack,
            defence,
            1,
            1));
    }

    public int getDurability() {
        return durability;
    }
}
