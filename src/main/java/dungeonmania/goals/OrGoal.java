package dungeonmania.goals;

import dungeonmania.Game;

public class OrGoal extends CompoundGoal {
    public OrGoal(String type, Goal n1, Goal n2) {
        super(" OR ", n1, n2);
    }

    @Override
    public boolean achieved(Game game) {
        return getN1().achieved(game) || getN2().achieved(game);
    }
}
