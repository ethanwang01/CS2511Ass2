package dungeonmania.goals;

import dungeonmania.Game;

public class LeafGoal implements Goal {
    private String type;
    private Integer target;

    public LeafGoal(String type) {
        this.type = type;
    }

    public LeafGoal(String type, Integer target) {
        this.type = type;
        this.target = target;
    }

    public Integer getTarget() {
        return target;
    }

    @Override
    public boolean achieved(Game game) {
        return false;
    }

    @Override
    public String toString(Game game) {
        return "";
    }
}
