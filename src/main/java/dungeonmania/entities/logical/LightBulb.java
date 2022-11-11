package dungeonmania.entities.logical;

import dungeonmania.util.Position;

public class LightBulb extends LogicalEntity {

    public LightBulb(Position position, String type) {
        super(position, type);
        this.setType("light_bulb_off");
    }

    @Override
    public void activate() {
        super.activate();
        if (isActivated()) {
            this.setType("light_bulb_on");
        }
        System.out.println(this.getType());
    }

    @Override
    public void deactivate() {
        super.deactivate();
        if (!isActivated()) {
            this.setType("light_bulb_off");
        }
        System.out.println(this.getType());
    }

}
