package dungeonmania.goals;

import dungeonmania.Game;

public interface GoalNode {
    boolean achieved(Game game);
    String getType();
}
