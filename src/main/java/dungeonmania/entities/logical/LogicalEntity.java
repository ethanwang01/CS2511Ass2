package dungeonmania.entities.logical;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.Conductor;
import dungeonmania.entities.Entity;
import dungeonmania.entities.StaticEntity;
import dungeonmania.entities.Subscribe;
import dungeonmania.util.Position;

public abstract class LogicalEntity extends StaticEntity implements Subscribe {
    private boolean activated;
    private String type;
    private String logic;
    private List<Conductor> adjConductors = new ArrayList<>();

    public LogicalEntity(Position position, String logic) {
        super(position);
        this.activated = false;
        this.logic = logic;
    }

    public void subscribe(Entity e) {
        if (e instanceof Conductor) adjConductors.add((Conductor) e);
    }

    public void unsubscribe(Entity e) {
        if (e instanceof Conductor) adjConductors.remove((Conductor) e);
    }

    public boolean isActivated() {
        return activated;
    }

    public String getLogic() {
        return logic;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void activate() {
        if (getLogic().equals("AND")) {
            if (adjConductors.size() < 2) return;
            int count = 0;
            for (Conductor c : adjConductors) {
                if (c.isActivated()) {
                    count++;
                }
            }
            if (adjConductors.size() == count) this.activated = true;
        }
        if (getLogic().equals("OR")) {
            int count = 0;
            for (Conductor c : adjConductors) {
                if (c.isActivated()) {
                    count++;
                }
            }
            if (count > 0) this.activated = true;
        }
        if (getLogic().equals("XOR")) {
            int count = 0;
            for (Conductor c : adjConductors) {
                if (c.isActivated()) {
                    count++;
                }
            }
            if (count == 1) this.activated = true;
            else this.activated = false;
        }
        if (getLogic().equals("CO_AND")) {
            if (adjConductors.size() < 2) return;
            int count = 0;
            double key = adjConductors.get(1).getActivationKey();
            boolean matchingKeys = true;
            for (Conductor c : adjConductors) {
                if (c.isActivated()) {
                    count++;
                    if (key != c.getActivationKey()) {
                        matchingKeys = false;
                    }
                    System.out.println("key: " + key);
                    System.out.println("activationKey: " + c.getActivationKey());
                }
            }
            if (adjConductors.size() == count && matchingKeys && count >= 2) this.activated = true;
            else this.activated = false;
        }
    }

    public void deactivate() {
        if (getLogic().equals("AND")) {
            if (adjConductors.size() < 2) {
                System.out.println("adj < 2");
                return;
            }
            int count = 0;
            for (Conductor c : adjConductors) {
                if (c.isActivated()) {
                    count++;
                }
            }
            if (adjConductors.size() == count) {
                this.activated = true;
                System.out.println("activated still");
            } else this.activated = false;
        }
        if (getLogic().equals("OR")) {
            int count = 0;
            for (Conductor c : adjConductors) {
                if (c.isActivated()) {
                    count++;
                }
            }
            if (count > 0) {
                this.activated = true;
            } else this.activated = false;
        }
        if (getLogic().equals("XOR")) {
            int count = 0;
            for (Conductor c : adjConductors) {
                if (c.isActivated()) {
                    count++;
                }
            }
            if (count == 1) {
                this.activated = true;
            } else {
                this.activated = false;
            }
        }
        if (getLogic().equals("CO_AND")) {
            if (adjConductors.size() < 2) return;
            int count = 0;
            double key = adjConductors.get(1).getActivationKey();
            boolean matchingKeys = true;
            for (Conductor c : adjConductors) {
                if (c.isActivated()) {
                    count++;
                    if (key != c.getActivationKey()) {
                        matchingKeys = false;
                    }
                    System.out.println("key: " + key);
                    System.out.println("activationKey: " + c.getActivationKey());
                }
            }
            if (adjConductors.size() == count && matchingKeys && count >= 2) this.activated = true;
            else this.activated = false;
        }
    }
}
