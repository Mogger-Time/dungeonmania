package dungeonmania.collectables;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCollectables {
    @Test
    @DisplayName("Test Entity is on a specified square position")
    public void testEntityOnSquare() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_collectableTest_testEntityOnSquare", "c_collectableTest_testEntityOnSquare");
        Position pos = getEntities(res, "treasure").get(0).getPosition();
        assertEquals(pos.getX(), 4);
        assertEquals(pos.getY(), 2);
    }

    @Test
    @DisplayName("Test Player can collect or pick up entity")
    public void testPickUpCollectable() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_collectableTest_testPickUpCollectable", "c_collectableTest_testPickUpCollectable");

        // pick up sword
        res = dmc.tick(Direction.DOWN);
        Position pos4 = getEntities(res, "player").get(0).getPosition();
        assertEquals(pos4.getX(), 1);
        assertEquals(pos4.getY(), 2);
        assertEquals(1, getInventory(res, "sword").size());

        // pick up wood
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        Position pos = getEntities(res, "player").get(0).getPosition();
        assertEquals(pos.getX(), 3);
        assertEquals(pos.getY(), 3);
        assertEquals(1, getInventory(res, "wood").size());

        // pick up treasure
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
        Position pos3 = getEntities(res, "player").get(0).getPosition();
        assertEquals(pos3.getX(), 4);
        assertEquals(pos3.getY(), 2);
        assertEquals(1, getInventory(res, "treasure").size());

        // pick up key
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        Position pos2 = getEntities(res, "player").get(0).getPosition();
        assertEquals(pos2.getX(), 4);
        assertEquals(pos2.getY(), 5);
        assertEquals(1, getInventory(res, "key").size());

        // pick up invincibility potion
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        Position pos6 = getEntities(res, "player").get(0).getPosition();
        assertEquals(pos6.getX(), 5);
        assertEquals(pos6.getY(), 8);
        assertEquals(1, getInventory(res, "invincibility_potion").size());

        // pick up bomb
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        Position pos5 = getEntities(res, "player").get(0).getPosition();
        assertEquals(pos5.getX(), 6);
        assertEquals(pos5.getY(), 3);
        assertEquals(1, getInventory(res, "bomb").size());

        // pick up arrows
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        Position pos1 = getEntities(res, "player").get(0).getPosition();
        assertEquals(pos1.getX(), 7);
        assertEquals(pos1.getY(), 4);
        assertEquals(1, getInventory(res, "arrow").size());

        // pick up invisibility potion
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        Position pos7 = getEntities(res, "player").get(0).getPosition();
        assertEquals(pos7.getX(), 8);
        assertEquals(pos7.getY(), 1);
        assertEquals(1, getInventory(res, "invisibility_potion").size());
    }

    @Test
    @DisplayName("Can only have one key at a time in inventory")
    public void testOneKeyInInventory() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_collectableTest_twoKeys", "c_collectableTest_testEntityOnSquare");

        // Pick up key 1
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "key").size());

        // Try to pick up key 2, but inventory should only have key 1
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "key").size());
    }

}