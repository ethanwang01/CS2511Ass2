package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PersistanceTest {
    @Test
    @Tag("21-1")
    @DisplayName("Test the player position preserved")
    public void testMovementDown() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame(
            "d_movementTest_testMovementDown", "c_movementTest_testMovementDown");
        EntityResponse initPlayer = TestUtils.getPlayer(initDungonRes).get();

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(),
        new Position(1, 2), false);

        // move player downward
        DungeonResponse actualDungonRes = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer = TestUtils.getPlayer(actualDungonRes).get();

        // assert after movement
        assertTrue(TestUtils.entityResponsesEqual(expectedPlayer, actualPlayer));

        // Save Game
        assertDoesNotThrow(() -> dmc.saveGame("persistTest"));

        // Start a new Game
        initDungonRes = dmc.newGame(
            "d_movementTest_testMovementDown", "c_movementTest_testMovementDown");
        initPlayer = TestUtils.getPlayer(initDungonRes).get();

        // create the expected result
        expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(),
        new Position(1, 1), false);
        actualPlayer = TestUtils.getPlayer(initDungonRes).get();

        // Check Position is Correct
        assertTrue(TestUtils.entityResponsesEqual(expectedPlayer, actualPlayer));

        // Load the old save at the old position
        initDungonRes = dmc.loadGame("src/main/java/dungeonmania/SaveFiles/persistTest.ser");
        // DungeonResponse initDungonRes = dmc.newGame(
        //     "d_movementTest_testMovementDown", "c_movementTest_testMovementDown");
        initPlayer = TestUtils.getPlayer(initDungonRes).get();

        // create the expected result
        expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(),
        new Position(1, 2), false);


        actualPlayer = TestUtils.getPlayer(initDungonRes).get();
        // assert after movement
        assertTrue(TestUtils.entityResponsesEqual(expectedPlayer, actualPlayer));

        // check if movement still works
        actualDungonRes = dmc.tick(Direction.LEFT);

        initPlayer = TestUtils.getPlayer(actualDungonRes).get();

        // create the expected result
        expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(),
        new Position(0, 2), false);


        actualPlayer = TestUtils.getPlayer(actualDungonRes).get();
        // assert after movement
        assertTrue(TestUtils.entityResponsesEqual(expectedPlayer, actualPlayer));
    }

    @Test
    @Tag("21-2")
    @DisplayName("Test the entities and inventory preserved")
    public void testEntitesPreserved() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_BuildablesTest_BuildBow", "c_BuildablesTest_BuildBow");

        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "arrow").size());

        // Pick up Wood
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "wood").size());

        // Pick up Arrow x2
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        assertDoesNotThrow(() -> dmc.saveGame("entitySave"));

        // make a new game
        res = dmc.newGame("d_BuildablesTest_BuildBow", "c_BuildablesTest_BuildBow");
        // check empty inventory
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "arrow").size());
        // check all entity on the ground
        assertEquals(1, TestUtils.getEntities(res, "wood").size());
        assertEquals(3, TestUtils.getEntities(res, "arrow").size());

        // Load Game
        res = dmc.loadGame("src/main/java/dungeonmania/SaveFiles/entitySave.ser");

        // Pick up arrow x1
        res = dmc.tick(Direction.RIGHT);
        assertEquals(3, TestUtils.getInventory(res, "arrow").size());

        // Build Bow
        assertEquals(0, TestUtils.getInventory(res, "bow").size());
        res = assertDoesNotThrow(() -> dmc.build("bow"));
        assertEquals(1, TestUtils.getInventory(res, "bow").size());

        // Materials used in construction disappear from inventory
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "arrow").size());

        assertDoesNotThrow(() -> dmc.saveGame("entitySave2"));


        // make a new game
        res = dmc.newGame("d_BuildablesTest_BuildBow", "c_BuildablesTest_BuildBow");
        // check empty inventory
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "arrow").size());
        // check all entity on the ground
        assertEquals(1, TestUtils.getEntities(res, "wood").size());
        assertEquals(3, TestUtils.getEntities(res, "arrow").size());

        res = dmc.loadGame("src/main/java/dungeonmania/SaveFiles/entitySave2.ser");

        // check inventory state
        assertEquals(1, TestUtils.getInventory(res, "bow").size());
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "arrow").size());
    }

    @Test
    @Tag("21-3")
    @DisplayName("Test the effects of the invincibility potion only last for a limited time")
    public void invincibilityDuration() throws InvalidActionException {
        //   S1_2   S1_3       P_1
        //   S1_1   S1_4/P_4   P_2/POT/P_3
        //          P_5        S2_2         S2_3
        //          P_6        S2_1         S2_4
        //          P_7/S2_7   S2_6         S2_5
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_potionsTest_invincibilityDuration", "c_potionsTest_invincibilityDuration");

        assertEquals(1, TestUtils.getEntities(res, "invincibility_potion").size());
        assertEquals(0, TestUtils.getInventory(res, "invincibility_potion").size());
        assertEquals(2, TestUtils.getEntities(res, "spider").size());

        // pick up invincibility_potion
        res = dmc.tick(Direction.DOWN);
        assertEquals(0, TestUtils.getEntities(res, "invincibility_potion").size());
        assertEquals(1, TestUtils.getInventory(res, "invincibility_potion").size());

        // consume invincibility_potion
        res = dmc.tick(TestUtils.getFirstItemId(res, "invincibility_potion"));

        // Save game
        assertDoesNotThrow(() -> dmc.saveGame("savePotions"));
        // newGame
        res = dmc.newGame("d_BuildablesTest_BuildBow", "c_potionsTest_invincibilityDuration");

        // Relaod game
        res = dmc.loadGame("src/main/java/dungeonmania/SaveFiles/savePotions.ser");
        // System.out.println("NUM ENTITIES: " + TestUtils.getEntities(res).size());

        // meet first spider, battle won immediately using invincibility_potion
        // we need to check that the effects exist before they are worn off,
        // otherwise teams which don't implement potions will pass
        res = dmc.tick(Direction.LEFT);

        assertEquals(1, res.getBattles().size());
        assertEquals(1, res.getBattles().get(0).getRounds().size());
        assertEquals(1, TestUtils.getEntities(res, "spider").size());

        // meet second spider and battle without effects of potion
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        assertEquals(0, TestUtils.getEntities(res, "spider").size());
        assertEquals(2, res.getBattles().size());
        assertTrue(res.getBattles().get(1).getRounds().size() >= 1);
        assertEquals(0, res.getBattles().get(1).getBattleItems().size());
    }
}
