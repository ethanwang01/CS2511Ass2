package dungeonmania.mvp;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    @Tag("17-1")
    @DisplayName("Test Swamp movement of non-adj unallied mercenary")
    public void testMercenarySwampMovementUnalliedNonAdj() {
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
    @Tag("17-2")
    @DisplayName("Test Swamp movement of non-adj allied mercenary")
    public void testMercenarySwampMovementAlliedNonAdj() {
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
        assertEquals(new Position(0, 3), (getMercPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(0, 3).equals(getMercPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(-1, 3).equals(getMercPos(res)));
    }
    @Test
    @Tag("17-3")
    @DisplayName("Test Swamp movement of adj allied mercenary")
    public void testMercenarySwampMovementAlliedAdj() {
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
    @Tag("17-4")
    @DisplayName("Test Dijkstra weighting")
    public void testDijkstra() {
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
    @Tag("17-5")
    @DisplayName("Test spider movement through swamp")
    public void testSpiderMovement() {
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
    @Test
    @Tag("17-5")
    @DisplayName("Test zombie movement through swamp")
    public void testZombieSwampMovement() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("swampZombieMovement", "mercenaryDijkstraMovementTest");
        assertEquals(new Position(3, 3), getZombiePos(res));
        for (int i = 0; i <= 2; i++) {
            res = dmc.tick(Direction.LEFT);
            assertEquals(new Position(2, 3), (getZombiePos(res)));
        }
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(1, 3).equals(getZombiePos(res)) || new Position(3, 3).equals(getZombiePos(res)));
    }
    @Test
    @Tag("17-6")
    @DisplayName("Test Swamp movement of unallied assassin through swamp")
    public void testAssassinSwampMovementUnallied() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("swampAssassinUnallied", "mercenaryDijkstraMovementTest");
        assertEquals(new Position(4, 3), getAssPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(3, 3), getAssPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(3, 3), getAssPos(res));
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(3, 3).equals(getAssPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(2, 3), (getAssPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(1, 3).equals(getAssPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(0, 3).equals(getAssPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(-1, 3).equals(getAssPos(res)));
    }
    @Test
    @Tag("17-7")
    @DisplayName("Test Swamp movement of non-adj allied assassin")
    public void testAssassinSwampMovementAlliedNonAdj() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("swampAssassinAllied", "mercenaryDijkstraMovementTest");
        String assId = TestUtils.getEntitiesStream(res, "assassin").findFirst().get().getId();
        assertEquals(new Position(4, 3), getAssPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(3, 3), getAssPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(2, 3), getAssPos(res));
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        res = assertDoesNotThrow(() -> dmc.interact(assId));
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertTrue(new Position(1, 3).equals(getAssPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(0, 3).equals(getAssPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(0, 3).equals(getAssPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(0, 3).equals(getAssPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(-1, 3).equals(getAssPos(res)));
    }
    @Test
    @Tag("17-8")
    @DisplayName("Test Swamp movement of adj allied assassin")
    public void testAssassinSwampMovementAlliedAdj() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("swampAssassinAllied", "mercenaryDijkstraMovementTest");
        String assId = TestUtils.getEntitiesStream(res, "assassin").findFirst().get().getId();
        assertEquals(new Position(4, 3), getAssPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(3, 3), getAssPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(2, 3), getAssPos(res));
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        res = assertDoesNotThrow(() -> dmc.interact(assId));
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        res = dmc.tick(Direction.RIGHT);
        assertTrue(new Position(1, 3).equals(getAssPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(0, 3).equals(getAssPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(-1, 3).equals(getAssPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(-2, 3).equals(getAssPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(-3, 3).equals(getAssPos(res)));
    }
    @Test
    @Tag("17-9")
    @DisplayName("Test Swamp movement of adj unallied mercenary")
    public void testMercenarySwampMovementUnalliedAdj() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("swampMercAdj", "mercenaryDijkstraMovementTest");
        assertEquals(new Position(4, 3), getMercPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(3, 3), getMercPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(2, 3), getMercPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(1, 3), getMercPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(1, 3), getMercPos(res));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(1, 3).equals(getMercPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(0, 3).equals(getMercPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(-1, 3).equals(getMercPos(res)));
    }
    @Test
    @Tag("17-10")
    @DisplayName("Test Swamp movement of adj unallied assassin")
    public void testAssassinSwampMovementUnalliedAdj() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("swampAssassinAdj", "mercenaryDijkstraMovementTest");
        assertEquals(new Position(4, 3), getAssPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(3, 3), getAssPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(2, 3), getAssPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(1, 3), getAssPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(1, 3), getAssPos(res));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(1, 3).equals(getAssPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(0, 3).equals(getAssPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(-1, 3).equals(getAssPos(res)));
    }
    @Test
    @Tag("17-4")
    @DisplayName("Test Dijkstra weighting Assassin")
    public void testDijkstraAssassin() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("swampDijkstraAssassin", "mercenaryDijkstraMovementTest");
        String assId = TestUtils.getEntitiesStream(res, "assassin").findFirst().get().getId();
        assertEquals(new Position(4, 3), getAssPos(res));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(4, 4).equals(getAssPos(res)) || new Position(4, 2).equals(getAssPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(4, 5).equals(getAssPos(res)) || new Position(4, 1).equals(getAssPos(res)));
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        res = assertDoesNotThrow(() -> dmc.interact(assId));
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertTrue(new Position(3, 5).equals(getAssPos(res)) || new Position(3, 1).equals(getAssPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(2, 5).equals(getAssPos(res)) || new Position(2, 1).equals(getAssPos(res)));
        res = dmc.tick(Direction.DOWN);
        assertTrue(new Position(1, 5).equals(getAssPos(res)) || new Position(1, 1).equals(getAssPos(res)));
        res = dmc.tick(Direction.DOWN);
        assertTrue(new Position(0, 5).equals(getAssPos(res)) || new Position(0, 1).equals(getAssPos(res)));
    }

    private Position getSpiderPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "spider").get(0).getPosition();
    }
    private Position getZombiePos(DungeonResponse res) {
        return TestUtils.getEntities(res, "zombie_toast").get(0).getPosition();
    }
    private Position getMercPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "mercenary").get(0).getPosition();
    }
    private Position getAssPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "assassin").get(0).getPosition();
    }
}
