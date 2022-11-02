package dungeonmania.goals;

import dungeonmania.Game;

public abstract class CompoundGoal implements Goal {
    private Goal n1;
    private Goal n2;
    private String type;

    public CompoundGoal(String type, Goal n1, Goal n2) {
        this.n1 = n1;
        this.n2 = n2;
        this.type = type;
    }

    public Goal getN1() {
        return n1;
    }

    public Goal getN2() {
        return n2;
    }

    public String toString(Game game) {
        return "(" + this.getN1().toString(game) + this.type + this.getN2().toString(game) + ")";
    };
}
