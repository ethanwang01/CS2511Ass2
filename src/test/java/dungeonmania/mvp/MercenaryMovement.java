package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MercenaryMovement {

    @Test
    @Tag("15-1")
    @DisplayName("Test follows Dijkstra path when bribed")
    public void dijkstraPathTestBribed() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("mercenary_no_walls", "mercenaryDijkstraMovementTest");
        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();
        assertEquals(new Position(4, 1), getMercPos(res));
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(3, 1), getMercPos(res));
        res = assertDoesNotThrow(() -> dmc.interact(mercId));
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        res = dmc.tick(Direction.RIGHT);
        assertTrue(new Position(3, 2).equals(getMercPos(res)) || new Position(2, 1)
            .equals(getMercPos(res)));
        res = dmc.tick(Direction.RIGHT);
        assertTrue(new Position(2, 2).equals(getMercPos(res)) || new Position(3, 1)
            .equals(getMercPos(res)) || new Position(3, 3).equals(getMercPos(res)));
    }

    @Test
    @Tag("15-2")
    @DisplayName("Testing an allied mercenary swaps positions")
    public void swaps() {
        //                                  Wall    Wall    Wall
        // P1       P2/Treasure      .      M2      M1      Wall
        //                                  Wall    Wall    Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest_allyBattle", "c_mercenaryTest_allyBattle");

        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();

        // pick up treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        // achieve bribe
        res = assertDoesNotThrow(() -> dmc.interact(mercId));
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(3, 1), getMercPos(res));

        // player walks, no battle occurs, mercenary swaps places
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, res.getBattles().size());
        assertEquals(new Position(2, 1), getMercPos(res));
    }

    @Test
    @Tag("15-3")
    @DisplayName("Testing an allied mercenary stays when dijkstra path move is to player's position after moves")
    public void mercenaryStays() {
        //                                  Wall    Wall    Wall
        // P1       P2/Treasure      .      M2      M1      Wall
        //                                  Wall    Wall    Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest_allyBattle", "c_mercenaryTest_allyBattle");

        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();

        // pick up treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        // player moves right, merc stays
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(4, 1), getMercPos(res));

        // achieve bribe
        res = assertDoesNotThrow(() -> dmc.interact(mercId));
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(4, 1), getMercPos(res));
    }

    @Test
    @Tag("15-3")
    @DisplayName("Testing an allied mercenary follows based on player's previous distinct position")
    public void mercenaryFollows() {
        //                                  Wall    Wall    Wall
        // P1       P2/Treasure      .      M2      M1      Wall
        //                                  Wall    Wall    Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest_allyBattle", "c_mercenaryTest_allyBattle");

        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();

        // pick up treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        // player moves right, merc stays
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(4, 1), getMercPos(res));

        // achieve bribe
        res = assertDoesNotThrow(() -> dmc.interact(mercId));
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(4, 1), getMercPos(res));

        // player moves left, mercenary moves left
        res = dmc.tick(Direction.LEFT);
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(3, 1), getMercPos(res));

        // player moves down, mercenary moves left
        res = dmc.tick(Direction.DOWN);
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(2, 1), getMercPos(res));
    }

    @Test
    @Tag("15-4")
    @DisplayName("Test bribe does not affect treasure goal")
    public void treasureGoalAfterBribe() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("mercenary_no_walls", "mercenaryDijkstraMovementTest");
        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();
        assertEquals(new Position(4, 1), getMercPos(res));
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(3, 1), getMercPos(res));
        res = assertDoesNotThrow(() -> dmc.interact(mercId));
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        // assert goal not met
        assertEquals(":treasure", TestUtils.getGoals(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        // assert treasure goal met
        assertEquals("", TestUtils.getGoals(res));
    }

    private Position getMercPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "mercenary").get(0).getPosition();
    }
}
