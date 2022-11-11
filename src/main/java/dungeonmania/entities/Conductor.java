package dungeonmania.entities;

public interface Conductor {
    public void activate(double activationKey);
    public void deactivate();
    public boolean isActivated();
    public void subscribe(Entity e);
    public void unsubscribe(Entity e);
    public double getActivationKey();
}
