package dungeonmania.mvp;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SunstoneTest {
        @Test
        @Tag("16-1")
        @DisplayName("Test player cannot walk through a closed door without key/sunstone")
        public void closedDoorNoKeys() {
            DungeonManiaController dmc;
            dmc = new DungeonManiaController();
            DungeonResponse res = dmc.newGame(
                "2_doors_no_keys_1_sunstone", "c_basicGoalsTest_exit");

            res = dmc.tick(Direction.RIGHT);
            assertEquals(new Position(3, 2), TestUtils.getEntities(res, "player").get(0).getPosition());
            res = dmc.tick(Direction.DOWN);
            Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();
            // try to walk through door and fail
            res = dmc.tick(Direction.DOWN);
            assertEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());
            // try to walk through door and fail
            res = dmc.tick(Direction.RIGHT);
            assertEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());
        }

        @Test
        @Tag("16-2")
        @DisplayName("Test player cannot walk through a closed door, but can use sunstone to unlock")
        public void closedDoorSunstone() {
            DungeonManiaController dmc;
            dmc = new DungeonManiaController();
            DungeonResponse res = dmc.newGame(
                "2_doors_no_keys_1_sunstone", "c_basicGoalsTest_exit");

            res = dmc.tick(Direction.RIGHT);
            assertEquals(new Position(3, 2), TestUtils.getEntities(res, "player").get(0).getPosition());
            res = dmc.tick(Direction.DOWN);
            // try to walk through door and fail
            res = dmc.tick(Direction.DOWN);
            assertEquals(new Position(3, 3), TestUtils.getEntities(res, "player").get(0).getPosition());
            // try to walk through door and fail
            res = dmc.tick(Direction.RIGHT);
            assertEquals(new Position(3, 3), TestUtils.getEntities(res, "player").get(0).getPosition());
            // pickup sunstone
            res = dmc.tick(Direction.LEFT);
            assertEquals(new Position(2, 3), TestUtils.getEntities(res, "player").get(0).getPosition());
            assertEquals(1, TestUtils.getInventory(res, "sunstone").size());

            res = dmc.tick(Direction.DOWN);
            assertEquals(new Position(2, 4), TestUtils.getEntities(res, "player").get(0).getPosition());
            // unlock door 1
            res = dmc.tick(Direction.RIGHT);
            assertEquals(new Position(3, 4), TestUtils.getEntities(res, "player").get(0).getPosition());
            // check sunstone still in inventory
            assertEquals(1, TestUtils.getInventory(res, "sunstone").size());
            res = dmc.tick(Direction.UP);
            assertEquals(new Position(3, 3), TestUtils.getEntities(res, "player").get(0).getPosition());
            // unlock door 2
            res = dmc.tick(Direction.RIGHT);
            assertEquals(new Position(4, 3), TestUtils.getEntities(res, "player").get(0).getPosition());
            // check sunstone still in inventory
            assertEquals(1, TestUtils.getInventory(res, "sunstone").size());
            // reach exit goal
            res = dmc.tick(Direction.DOWN);
            assertEquals("", TestUtils.getGoals(res));
        }
        @Test
        @Tag("16-3")
        @DisplayName("Test player cannot walk through a closed door without key/sunstone")
        public void treasureSunstone() {
            DungeonManiaController dmc;
            dmc = new DungeonManiaController();
            DungeonResponse res = dmc.newGame(
                "2_doors_1_sunstone_1_coin", "SunstoneTreasure");
            // pickup treasure
            res = dmc.tick(Direction.RIGHT);
            assertEquals(new Position(3, 2), TestUtils.getEntities(res, "player").get(0).getPosition());
            // check inventory contains treasure
            assertEquals(1, TestUtils.getInventory(res, "treasure").size());
            // assert goal not met
            assertEquals(":treasure", TestUtils.getGoals(res));
            res = dmc.tick(Direction.DOWN);
            // pickup sunstone
            res = dmc.tick(Direction.LEFT);
            // check inventory contains treasure
            assertEquals(1, TestUtils.getInventory(res, "treasure").size());
            // check inventory contains sunstone
            assertEquals(1, TestUtils.getInventory(res, "sunstone").size());
            // assert goal met
            assertEquals("", TestUtils.getGoals(res));
        }
        @Test
        @Tag("16-4")
        @DisplayName("Test player can build shield without key but with sunstone")
        public void shieldSunstone() {
            DungeonManiaController dmc;
            dmc = new DungeonManiaController();
            DungeonResponse res = dmc.newGame(
                "build_shield_no_key_1_sunstone", "simple");
            // pickup wood
            res = dmc.tick(Direction.RIGHT);
            assertEquals(1, TestUtils.getInventory(res, "wood").size());
            // pickup wood
            res = dmc.tick(Direction.RIGHT);
            assertEquals(2, TestUtils.getInventory(res, "wood").size());
            // pickup sunstone
            res = dmc.tick(Direction.RIGHT);
            assertEquals(2, TestUtils.getInventory(res, "wood").size());
            assertEquals(1, TestUtils.getInventory(res, "sunstone").size());
            // Build Shield
            assertEquals(0, TestUtils.getInventory(res, "shield").size());
            res = assertDoesNotThrow(() -> dmc.build("shield"));
            assertEquals(1, TestUtils.getInventory(res, "shield").size());

            // Materials used in construction disappear from inventory except sunstone
            assertEquals(0, TestUtils.getInventory(res, "wood").size());
            assertEquals(1, TestUtils.getInventory(res, "sunstone").size());
        }
        @Test
        @Tag("16-5")
        @DisplayName("Test player can build shield without key but with sunstone, can use sunstone to open door")
        public void shieldSunstoneDoor() {
            DungeonManiaController dmc;
            dmc = new DungeonManiaController();
            DungeonResponse res = dmc.newGame(
                "build_shield_no_key_1_sunstone", "simple");
            // pickup wood
            res = dmc.tick(Direction.RIGHT);
            assertEquals(1, TestUtils.getInventory(res, "wood").size());
            // pickup wood
            res = dmc.tick(Direction.RIGHT);
            assertEquals(2, TestUtils.getInventory(res, "wood").size());
            // pickup sunstone
            res = dmc.tick(Direction.RIGHT);
            assertEquals(2, TestUtils.getInventory(res, "wood").size());
            assertEquals(1, TestUtils.getInventory(res, "sunstone").size());
            // Build Shield
            assertEquals(0, TestUtils.getInventory(res, "shield").size());
            res = assertDoesNotThrow(() -> dmc.build("shield"));
            assertEquals(1, TestUtils.getInventory(res, "shield").size());

            // Materials used in construction disappear from inventory except sunstone
            assertEquals(0, TestUtils.getInventory(res, "wood").size());
            assertEquals(1, TestUtils.getInventory(res, "sunstone").size());

            res = dmc.tick(Direction.LEFT);
            res = dmc.tick(Direction.DOWN);
            Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();
            // open door
            res = dmc.tick(Direction.DOWN);
            assertNotEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());
            // inventory still has sunstone
            assertEquals(1, TestUtils.getInventory(res, "sunstone").size());
        }
}
