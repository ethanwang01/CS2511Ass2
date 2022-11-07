package dungeonmania.mvp;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SwampTest {
    @Test
    @Tag("1-1")
    @DisplayName("Test Swamp movement of unallied mercenary")
    public void TestSwampStraightLineUnallied() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("swampMovementUnallied", "mercenaryDijkstraMovementTest");
        assertEquals(new Position(4, 3), getMercPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(3, 3), getMercPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(3, 3), getMercPos(res));
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(3, 3).equals(getMercPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(2, 3).equals(getMercPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(1, 3).equals(getMercPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(0, 3).equals(getMercPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(-1, 3).equals(getMercPos(res)));
    }
    @Test
    @Tag("1-2")
    @DisplayName("Test Swamp movement of non-adj allied mercenary")
    public void TestSwampStraightLineAlliedNonAdj() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("swampMovementAllied", "mercenaryDijkstraMovementTest");
        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();
        assertEquals(new Position(4, 3), getMercPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(3, 3), getMercPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(2, 3), getMercPos(res));
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        res = assertDoesNotThrow(() -> dmc.interact(mercId));
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertTrue(new Position(1, 3).equals(getMercPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(0, 3).equals(getMercPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(0, 3).equals(getMercPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(0, 3).equals(getMercPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(-1, 3).equals(getMercPos(res)));
    }
    @Test
    @Tag("1-3")
    @DisplayName("Test Swamp movement of adj allied mercenary")
    public void TestSwampStraightLineAlliedAdj() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("swampMovementAllied", "mercenaryDijkstraMovementTest");
        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();
        assertEquals(new Position(4, 3), getMercPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(3, 3), getMercPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(2, 3), getMercPos(res));
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        res = assertDoesNotThrow(() -> dmc.interact(mercId));
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        res = dmc.tick(Direction.RIGHT);
        assertTrue(new Position(1, 3).equals(getMercPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(0, 3).equals(getMercPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(-1, 3).equals(getMercPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(-2, 3).equals(getMercPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(-3, 3).equals(getMercPos(res)));
    }
    @Test
    @Tag("1-4")
    @DisplayName("Test Dijkstra weighting")
    public void TestDijkstra() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("swampDijkstra", "mercenaryDijkstraMovementTest");
        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();
        assertEquals(new Position(4, 3), getMercPos(res));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(4, 4).equals(getMercPos(res)) || new Position(4, 2).equals(getMercPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(4, 5).equals(getMercPos(res)) || new Position(4, 1).equals(getMercPos(res)));
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        res = assertDoesNotThrow(() -> dmc.interact(mercId));
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertTrue(new Position(3, 5).equals(getMercPos(res)) || new Position(3, 1).equals(getMercPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(2, 5).equals(getMercPos(res)) || new Position(2, 1).equals(getMercPos(res)));
        res = dmc.tick(Direction.DOWN);
        assertTrue(new Position(1, 5).equals(getMercPos(res)) || new Position(1, 1).equals(getMercPos(res)));
        res = dmc.tick(Direction.DOWN);
        assertTrue(new Position(0, 5).equals(getMercPos(res)) || new Position(0, 1).equals(getMercPos(res)));
        
    }
    @Test
    @Tag("1-5")
    @DisplayName("Test spider movement through swamp")
    public void testSpiderMpvement() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("swampSpiderMovement", "mercenaryDijkstraMovementTest");
        assertEquals(new Position(3, 3), getSpiderPos(res));
        for (int i = 0; i <= 2; i++) {
            res = dmc.tick(Direction.LEFT);
            assertEquals(new Position(3, 2), (getSpiderPos(res)));
        }
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(4, 2).equals(getSpiderPos(res)));
        for (int i = 0; i <= 2; i++) {
            res = dmc.tick(Direction.LEFT);
            assertEquals(new Position(4, 3), (getSpiderPos(res)));
        }
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(4, 4), getSpiderPos(res));
        for (int i = 0; i <= 2; i++) {
            res = dmc.tick(Direction.LEFT);
            assertEquals(new Position(3, 4), (getSpiderPos(res)));
        }
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(2, 4), getSpiderPos(res));
        for (int i = 0; i <= 2; i++) {
            res = dmc.tick(Direction.LEFT);
            assertEquals(new Position(2, 3), (getSpiderPos(res)));
        }
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(2, 2), getSpiderPos(res));
    }

    private Position getSpiderPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "spider").get(0).getPosition();
    }
    private Position getMercPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "mercenary").get(0).getPosition();
    }
}
