package dungeonmania.entities.playerState;

import java.io.Serializable;

import dungeonmania.entities.Player;

public class InvincibleState implements PlayerState, Serializable {
    private Player player;

    public InvincibleState(Player player) {
        this.player = player;
    }

    public boolean isInvincible() {
        return true;
    }

    public Player getPlayer() {
        return player;
    }
}
