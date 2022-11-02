package dungeonmania.entities.playerState;

import dungeonmania.entities.Player;

public class InvisibleState implements PlayerState {
    public Player player;

    public InvisibleState(Player player) {
        this.player = player;
    }
    
    public boolean isInvisible() {
        return true;
    }

    public Player getPlayer() {
        return player;
    }

}
