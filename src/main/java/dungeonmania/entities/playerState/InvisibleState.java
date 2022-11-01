package dungeonmania.entities.playerState;

import dungeonmania.entities.Player;

public class InvisibleState implements PlayerState {
    public Player player;
    public boolean isInvisible = true;

    public InvisibleState(Player player) {
        this.player = player;
    }

    // public InvisibleState(Player player) {
    //     super(player, false, true);
    // }
    
    @Override
    public boolean isInvisible() {
        return this.isInvisible;
    }

    @Override
    public void transitionBase() {
        Player player = getPlayer();
        player.changeState(new BaseState(player));
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
