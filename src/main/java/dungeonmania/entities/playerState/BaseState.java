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

    @Override
    public void transitionInvincible() {
        Player player = getPlayer();
        player.changeState(new InvincibleState(player));
    }

    @Override
    public void transitionInvisible() {
        Player player = getPlayer();
        player.changeState(new InvisibleState(player));
    }

    public Player getPlayer() {
        return player;
    }
}
