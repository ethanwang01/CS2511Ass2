package dungeonmania.entities.playerState;

import java.io.Serializable;

import dungeonmania.entities.Player;

public class BaseState implements PlayerState, Serializable {
    private Player player;

    public BaseState(Player player) {
        this.player = player;
    }

    @Override
    public void transitionBase() {
        // Do nothing
    }

    public Player getPlayer() {
        return player;
    }
}
