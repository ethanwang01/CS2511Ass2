package dungeonmania.goals;

import dungeonmania.Game;

public class AndGoal extends CompoundGoal {

    public AndGoal(String type, Goal n1, Goal n2) {
        super(" AND ", n1, n2);
    }

    @Override
    public boolean achieved(Game game) {
        return getN1().achieved(game) && getN2().achieved(game);
    }

    @Override
    public String toString(Game game) {
        return "(" + this.getN1().toString(game) + " AND " + this.getN2().toString(game) + ")";
    }
}
