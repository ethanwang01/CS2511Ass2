package dungeonmania.entities.playerState;

import dungeonmania.entities.Player;

public class InvincibleState implements PlayerState {
    private Player player;

    
    public InvincibleState(Player player) {
        this.player = player;
    }

    // public InvincibleState(Player player) {
    //     super(player, true, false);
    // }

    public boolean isInvincible() {
        return true;
        // return this.isInvincible;
    }
    
    public Player getPlayer() {
        return player;
    }
}
