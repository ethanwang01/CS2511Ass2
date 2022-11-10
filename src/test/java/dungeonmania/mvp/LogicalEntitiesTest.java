package dungeonmania.mvp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class LogicalEntitiesTest {
    @Test
    @Tag("20-1")
    @DisplayName("OR Test player cannot walk through an unactivated switch_door")
    public void cannotWalkClosedSwitchDoor() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame(
            "logic_OR_switch_door_light_bulb", "simple");
        Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        assertEquals(new Position(0, 0), pos);
        // try to walk through switch door and fail
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        res = dmc.tick(Direction.RIGHT);
        assertEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_off", new Position(3, 2)));
    }
    @Test
    @Tag("20-2")
    @DisplayName("OR Test player can walk through an activated switch_door, switch turns light on")
    public void canWalkActivatedSwitchDoorLightOn() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame(
            "logic_OR_switch_door_light_bulb", "simple");
        Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        assertEquals(new Position(0, 0), pos);
        assertEquals(true, TestUtils.entityAtPosition(res, "switch_door", new Position(4, 0)));
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_off", new Position(3, 2)));
        // walk activate switch
        res = dmc.tick(Direction.RIGHT);
        assertEquals(true, TestUtils.entityAtPosition(res, "switch_door_open", new Position(4, 0)));
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_on", new Position(3, 2)));
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        // walk on wire
        res = dmc.tick(Direction.DOWN);
        // walk through door
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        // move boulder off of switch
        res = dmc.tick(Direction.LEFT);
        // expect switchDoor closed, light turns off
        assertEquals(true, TestUtils.entityAtPosition(res, "switch_door", new Position(4, 0)));
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_off", new Position(3, 2)));
    }
    @Test
    @Tag("20-3")
    @DisplayName("AND Test SwitchDoor")
    public void doorOpensIfBOTHIncomingWiresActive() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame(
            "logic_AND_switch_door", "simple");
        Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        assertEquals(new Position(0, 0), pos);
        assertEquals(true, TestUtils.entityAtPosition(res, "switch_door", new Position(4, 0)));
        // walk activate switch
        res = dmc.tick(Direction.RIGHT);
        // door remains closed
        assertEquals(true, TestUtils.entityAtPosition(res, "switch_door", new Position(4, 0)));
        res = dmc.tick(Direction.LEFT);
        // activate second switch
        res = dmc.tick(Direction.DOWN);
        assertEquals(true, TestUtils.entityAtPosition(res, "switch_door_open", new Position(4, 0)));
        // test can walk through door
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        res = dmc.tick(Direction.UP);
        assertNotEquals(new Position(4, 0), pos);
        // deactivate one switch
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        assertEquals(true, TestUtils.entityAtPosition(res, "switch_door", new Position(4, 0)));
    }
    @Test
    @Tag("20-4")
    @DisplayName("AND Test LightBulb")
    public void lightOnIfBOTHIncomingWiresActive() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame(
            "logic_AND_light_bulb", "simple");
        Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        assertEquals(new Position(0, 0), pos);
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_off", new Position(4, 0)));
        // walk activate switch
        res = dmc.tick(Direction.RIGHT);
        // light remains off
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_off", new Position(4, 0)));
        res = dmc.tick(Direction.LEFT);
        // activate second switch
        res = dmc.tick(Direction.DOWN);
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_on", new Position(4, 0)));
        // deactivate one switch
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_off", new Position(4, 0)));
    }
    @Test
    @Tag("20-5")
    @DisplayName("AND Test LightBulb ALL active")
    public void lightOnIfALLIncomingWiresActive() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame(
            "logic_AND_light_bulb_3_adj", "simple");
        Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        assertEquals(new Position(0, 0), pos);
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_off", new Position(4, 0)));
        // walk activate switch
        res = dmc.tick(Direction.RIGHT);
        // light remains off
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_off", new Position(4, 0)));
        res = dmc.tick(Direction.LEFT);
        // activate second switch
        res = dmc.tick(Direction.DOWN);
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_off", new Position(4, 0)));
        // activate third switch, light turns on
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_on", new Position(4, 0)));
        // deactivate one switch
        res = dmc.tick(Direction.UP);
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_off", new Position(4, 0)));
    }
    @Test
    @Tag("20-6")
    @DisplayName("XOR Test LightBulb")
    public void lightOnIfOnly1IncomingWiresActive() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame(
            "logic_XOR_light_bulb", "simple");
        Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        assertEquals(new Position(0, 0), pos);
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_off", new Position(4, 0)));
        // walk activate switch
        res = dmc.tick(Direction.RIGHT);
        // light turns on
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_on", new Position(4, 0)));
        res = dmc.tick(Direction.LEFT);
        // activate second switch
        res = dmc.tick(Direction.DOWN);
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_off", new Position(4, 0)));
        // deactivate one switch
        res = dmc.tick(Direction.DOWN);
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_on", new Position(4, 0)));
        // deactivate other switch
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_off", new Position(4, 0)));
    }
    @Test
    @Tag("20-7")
    @DisplayName("CO_AND Test LightBulb")
    public void coAndTestDifferentSwitches() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame(
            "logic_CO_AND_lightbulb", "simple");
        Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        assertEquals(new Position(0, 0), pos);
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_off", new Position(4, 0)));
        // walk activate switch
        res = dmc.tick(Direction.RIGHT);
        // light stays off
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_off", new Position(4, 0)));
        res = dmc.tick(Direction.LEFT);
        // activate second switch
        res = dmc.tick(Direction.DOWN);
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_off", new Position(4, 0)));
        // deactivate one switch
        res = dmc.tick(Direction.DOWN);
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_off", new Position(4, 0)));
        // deactivate other switch
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_off", new Position(4, 0)));
        // activate switch connected to all bulb-adjacent wires
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.LEFT);
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_on", new Position(4, 0)));
    }
    @Test
    @Tag("20-8")
    @DisplayName("CO_AND Test LightBulb 'reactivation'")
    public void coAndTestReactivateWireFromOtherSwitch() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame(
            "logic_CO_AND_lightbulb", "simple");
        Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        assertEquals(new Position(0, 0), pos);
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_off", new Position(4, 0)));
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.LEFT);
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_on", new Position(4, 0)));
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        // assert "reactivating" conductor from another switch doesn't affect the light_bulb
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_on", new Position(4, 0)));
    } 
    @Test
    @Tag("20-9")
    @DisplayName("Mixed Logic Entities Test")
    public void logic_mixed() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame(
            "logic_mixed", "simple");
        Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        assertEquals(new Position(4, 2), pos);
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_off", new Position(6, 0)));
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_off", new Position(6, 7)));
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_off", new Position(2, 4)));
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_off", new Position(1, 7)));
        // activate first switch, expect co_and and xor light bulbs to activate
        res = dmc.tick(Direction.DOWN);
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_on", new Position(6, 0)));
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_on", new Position(6, 7)));
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.LEFT);
        // activate second switch, expect or and and lightbulbs to turn on, expect co_and lightbulb to stay on, expect 
        // xor lightbulb to turn off
        res = dmc.tick(Direction.DOWN);
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_on", new Position(6, 0)));
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_off", new Position(6, 7)));
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_on", new Position(2, 4)));
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_on", new Position(1, 7)));
        res = dmc.tick(Direction.UP);
        // deactivate first switch, expect and and co_and to turn off, expect others to stay on
        res = dmc.tick(Direction.UP);
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_off", new Position(6, 0)));
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_on", new Position(6, 7)));
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_off", new Position(2, 4)));
        assertEquals(true, TestUtils.entityAtPosition(res, "light_bulb_on", new Position(1, 7)));
    } 
}
