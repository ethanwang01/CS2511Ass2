package dungeonmania.entities.playerState;

import dungeonmania.entities.Player;

public class BaseState implements PlayerState {
    public Player player;

    public BaseState(Player player) {
        this.player = player;
    }
    // public BaseState(Player player) {
    //     super(player, false, false);
    // }

    @Override
    public void transitionBase() {
        // Do nothing
    }

    public Player getPlayer() {
        return player;
    }
}
