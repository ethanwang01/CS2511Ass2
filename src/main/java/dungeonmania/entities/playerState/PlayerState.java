package dungeonmania.entities.playerState;

import dungeonmania.entities.Player;

public interface PlayerState {
    Player getPlayer();
    // void transitionBase();
    default void transitionBase() {
        Player player = getPlayer();
        player.changeState(new BaseState(player));
    }
    // void transitionInvincible();
    default void transitionInvincible() {
        Player player = getPlayer();
        player.changeState(new InvincibleState(player));
    }
    // void transitionInvisible();
    default void transitionInvisible() {
        Player player = getPlayer();
        player.changeState(new InvisibleState(player));
    }
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