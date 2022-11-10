package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.exceptions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BuildablesTest {

    @Test
    @Tag("5-1")
    @DisplayName("Test IllegalArgumentException is raised when attempting to build an unknown entity - sword")
    public void buildSwordIllegalArgumentException() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        dmc.newGame(
            "d_BuildablesTest_BuildSwordIllegalArgumentException",
        "c_BuildablesTest_BuildSwordIllegalArgumentException");
        assertThrows(IllegalArgumentException.class, () ->
                dmc.build("sword")
        );
    }

    @Test
    @Tag("5-2")
    @DisplayName(
        "Test InvalidActionException is raised when the player does not have sufficient items to build a bow or shield"
    )
    public void buildInvalidActionException() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        dmc.newGame("d_BuildablesTest_BuildInvalidArgumentException", "c_BuildablesTest_BuildInvalidArgumentException");
        assertThrows(InvalidActionException.class, () ->
                dmc.build("bow")
        );

        assertThrows(InvalidActionException.class, () ->
                dmc.build("shield")
        );
    }

    @Test
    @Tag("5-3")
    @DisplayName("Test building a bow")
    public void buildBow() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_BuildablesTest_BuildBow", "c_BuildablesTest_BuildBow");

        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "arrow").size());

        // Pick up Wood
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "wood").size());

        // Pick up Arrow x3
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(3, TestUtils.getInventory(res, "arrow").size());

        // Build Bow
        assertEquals(0, TestUtils.getInventory(res, "bow").size());
        res = assertDoesNotThrow(() -> dmc.build("bow"));
        assertEquals(1, TestUtils.getInventory(res, "bow").size());

        // Materials used in construction disappear from inventory
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "arrow").size());
    }

    @Test
    @Tag("5-4")
    @DisplayName("Test building a shield with a key")
    public void buildShieldWithKey() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_BuildablesTest_BuildShieldWithKey", "c_BuildablesTest_BuildShieldWithKey");

        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "key").size());

        // Pick up Wood x2
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "wood").size());

        // Pick up Key
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "key").size());

        // Build Shield
        assertEquals(0, TestUtils.getInventory(res, "shield").size());
        res = assertDoesNotThrow(() -> dmc.build("shield"));
        assertEquals(1, TestUtils.getInventory(res, "shield").size());

        // Materials used in construction disappear from inventory
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "key").size());
    }

    @Test
    @Tag("5-5")
    @DisplayName("Test building a shield with treasure")
    public void buildShieldWithTreasure() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame(
            "d_BuildablesTest_BuildShieldWithTreasure", "c_BuildablesTest_BuildShieldWithTreasure");
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());

        // Pick up Wood x2
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "wood").size());

        // Pick up Treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        // Build Shield
        assertEquals(0, TestUtils.getInventory(res, "shield").size());
        res = assertDoesNotThrow(() -> dmc.build("shield"));
        assertEquals(1, TestUtils.getInventory(res, "shield").size());

        // Materials used in construction disappear from inventory
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
    }

    @Test
    @Tag("5-6")
    @DisplayName(
        "Test responsse buildables parameter is a list of buildables that the player can currently build"
    )
    public void dungeonResponseBuildables() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame(
            "d_BuildablesTest_DungeonResponseBuildables", "c_BuildablesTest_DungeonResponseBuildables");

        List<String> buildables = new ArrayList<>();
        assertEquals(buildables, res.getBuildables());

        // Gather entities to build bow
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        // Bow added to buildables list
        buildables.add("bow");
        assertEquals(buildables, res.getBuildables());

        // Gather entities to build shield
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        // Shield added to buildables list
        buildables.add("shield");
        assertEquals(buildables.size(), res.getBuildables().size());
        assertTrue(buildables.containsAll(res.getBuildables()));
        assertTrue(res.getBuildables().containsAll(buildables));

        // Build bow
        res = assertDoesNotThrow(() -> dmc.build("bow"));
        assertEquals(1, TestUtils.getInventory(res, "bow").size());

        // Bow disappears from buildables list
        buildables.remove("bow");
        assertEquals(buildables, res.getBuildables());

        // Build shield
        res = assertDoesNotThrow(() -> dmc.build("shield"));
        assertEquals(1, TestUtils.getInventory(res, "shield").size());

        // Shield disappears from buildables list
        buildables.remove("shield");
        assertEquals(buildables, res.getBuildables());
    }

    @Test
    @Tag("5-7")
    @DisplayName(
        "Test buildable sceptre"
    )
    public void sceptreBuild() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame(
            "d_buildableTest_build_sceptre", "c_BuildablesTest_DungeonResponseBuildables");

        List<String> buildables = new ArrayList<>();
        assertEquals(buildables, res.getBuildables());

        // Gather entities to build sceptre
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        // sceptre added to buildables list
        buildables.add("sceptre");
        assertEquals(buildables, res.getBuildables());

        // Gather entities to build midnight armour
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);

        // midnight armour added to buildables list
        buildables.add("midnight_armour");
        assertEquals(buildables.size(), res.getBuildables().size());
        assertTrue(buildables.containsAll(res.getBuildables()));
        assertTrue(res.getBuildables().containsAll(buildables));

        // Build sceptre
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        // sceptre disappears from buildables list
        buildables.remove("sceptre");
        assertEquals(buildables, res.getBuildables());

        // Build midnight shield
        res = assertDoesNotThrow(() -> dmc.build("midnight_armour"));
        assertEquals(1, TestUtils.getInventory(res, "midnight_armour").size());

        // midnight armour disappears from buildables list
        buildables.remove("midnight_armour");
        assertEquals(buildables, res.getBuildables());

        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();

        // achieve bribe
        res = assertDoesNotThrow(() -> dmc.interact(mercId));
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());

        // ensure sceptre is removed from inventory
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());

        // Check Mind Control
        // swap places with merc and no battle
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.UP);
        assertEquals(0, res.getBattles().size());
        // Down and mind control no effect
        res = dmc.tick(Direction.DOWN);
        assertEquals(0, res.getBattles().size());
        // up and battle
        res = dmc.tick(Direction.UP);
        assertEquals(1, res.getBattles().size());
    }
}
