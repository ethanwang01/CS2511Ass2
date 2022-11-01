package dungeonmania.entities.playerState;

import dungeonmania.entities.Player;

public interface PlayerState {
    void transitionBase();
    void transitionInvincible();
    void transitionInvisible();
    default boolean isInvincible() {
        return false;
    }
    default boolean isInvisible() {
        return false;
    }
}
/*public abstract class PlayerState {
    private Player player;
    private boolean isInvincible = false;
    private boolean isInvisible = false;

    PlayerState(Player player, boolean isInvincible, boolean isInvisible) {
        this.player = player;
        this.isInvincible = isInvincible;
        this.isInvisible = isInvisible;
    }

    public boolean isInvincible() {
        return isInvincible;
    };

    public boolean isInvisible() {
        return isInvisible;
    };

    public Player getPlayer() {
        return player;
    }

    public abstract void transitionInvisible();
    public abstract void transitionInvincible();
    public abstract void transitionBase();
}
*/