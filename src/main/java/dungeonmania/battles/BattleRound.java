package dungeonmania.battles;

public class BattleRound {
    private double deltaSelfHealth;
    private double deltaTargetHealth;

    public BattleRound(double deltaSelfHealth, double deltaTargetHealth) {
        this.deltaSelfHealth = deltaSelfHealth;
        this.deltaTargetHealth = deltaTargetHealth;
        System.out.println("Health: " + this.getDeltaSelfHealth());
    }

    public double getDeltaSelfHealth() {
        return deltaSelfHealth;
    }

    public double getDeltaTargetHealth() {
        return deltaTargetHealth;
    }
}
