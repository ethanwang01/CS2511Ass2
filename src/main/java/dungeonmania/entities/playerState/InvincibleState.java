package dungeonmania.entities.playerState;

import dungeonmania.entities.Player;

public class InvincibleState implements PlayerState {
    private Player player;
    private boolean isInvincible = true;

    
    public InvincibleState(Player player) {
        this.player = player;
    }

    // public InvincibleState(Player player) {
    //     super(player, true, false);
    // }

    @Override
    public void transitionBase() {
        Player player = getPlayer();
        player.changeState(new BaseState(player));
    }

    @Override
    public boolean isInvincible() {
        return this.isInvincible;
    }
    
    public Player getPlayer() {
        return player;
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
}
