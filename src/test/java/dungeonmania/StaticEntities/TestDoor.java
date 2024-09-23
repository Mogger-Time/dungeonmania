package dungeonmania.StaticEntities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getPlayer;
import static dungeonmania.TestUtils.getInventory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class TestDoor {
    // Provided test from ExampleTests.java
    @Test
    @DisplayName("Test player can use a key to open and walk through a door")
    public void useKeyWalkThroughOpenDoor() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_DoorsKeysTest_useKeyWalkThroughOpenDoor", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");

        // pick up key
        res = dmc.tick(Direction.RIGHT);
        Position pos = getEntities(res, "player").get(0).getPosition();
        assertEquals(1, getInventory(res, "key").size());

        // walk through door and check key is gone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, getInventory(res, "key").size());
        assertNotEquals(pos, getEntities(res, "player").get(0).getPosition());
    }

    @Test
    @DisplayName("Test player cannot go through door")
    public void testCannotMoveThroughClosedDoor() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_doorTest_oneDoor", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        EntityResponse initPlayer = getPlayer(initDungeonRes).get();
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 2), false);

        DungeonResponse actualDungeonRes = dmc.tick(Direction.DOWN);
        actualDungeonRes = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer = getPlayer(actualDungeonRes).get();

        assertEquals(expectedPlayer, actualPlayer); 
    }

    @Test
    @DisplayName("Test multiple doors")
    public void testOpenMultipleDoorsWalkThrough() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_doorTest_multipleDoors", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");

        // Get key 1
        res = dmc.tick(Direction.RIGHT);
        Position pos = getEntities(res, "player").get(0).getPosition();
        assertEquals(1, getInventory(res, "key").size());

        // Try door 2 with key 1, player in same position as before
        res = dmc.tick(Direction.DOWN);
        assertEquals(1, getInventory(res, "key").size());
        assertEquals(pos, getEntities(res, "player").get(0).getPosition());

        // Open door 1
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, getInventory(res, "key").size());
        assertNotEquals(pos, getEntities(res, "player").get(0).getPosition());

        // Get key 2
        res = dmc.tick(Direction.DOWN);

        // Open door 2
        pos = getEntities(res, "player").get(0).getPosition();
        assertEquals(1, getInventory(res, "key").size());

        res = dmc.tick(Direction.LEFT);
        assertEquals(0, getInventory(res, "key").size());
        assertNotEquals(pos, getEntities(res, "player").get(0).getPosition());
    }
}
