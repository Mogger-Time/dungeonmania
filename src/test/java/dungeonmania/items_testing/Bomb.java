package dungeonmania.items_testing;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static dungeonmania.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Bomb {
    @Test
    @DisplayName("Test surrounding entities are removed when placing a bomb next to an active switch with config file bomb radius set to 2")
    public void placeBombRadius2() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_bombTest_placeBombRadius2", "c_bombTest_placeBombRadius2");

        // Activate Switch
        res = dmc.tick(Direction.RIGHT);

        // Pick up Bomb
        res = dmc.tick(Direction.DOWN);
        assertEquals(1, getInventory(res, "bomb").size());

        // Place Cardinally Adjacent
        res = dmc.tick(Direction.RIGHT);
        String bombId = getInventory(res, "bomb").get(0).getId();
        res = assertDoesNotThrow(() -> dmc.tick(bombId));

        // Check Bomb exploded with radius 2
        //
        //              Boulder/Switch      Wall            Wall
        //              Bomb                Treasure
        //
        //              Treasure
        assertEquals(0, getEntities(res, "bomb").size());
        assertEquals(0, getEntities(res, "boulder").size());
        assertEquals(0, getEntities(res, "switch").size());
        assertEquals(0, getEntities(res, "wall").size());
        assertEquals(0, getEntities(res, "treasure").size());
        assertEquals(1, getEntities(res, "player").size());
    }

    @Test
    @DisplayName("Place bomb first and then activate switch")
    public void activateswitch() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_bombTest_placeBombRadius2", "c_bombTest_placeBombRadius2");

        //pickup bomb without triggering switch
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);

        res = dmc.tick(Direction.RIGHT);
        String bombId = getInventory(res, "bomb").get(0).getId();
        res = assertDoesNotThrow(() -> dmc.tick(bombId));

        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);

        assertEquals(0, getEntities(res, "bomb").size());
        assertEquals(0, getEntities(res, "boulder").size());
        assertEquals(0, getEntities(res, "switch").size());
        assertEquals(0, getEntities(res, "wall").size());
        assertEquals(0, getEntities(res, "treasure").size());
        assertEquals(1, getEntities(res, "player").size());

    }

    @Test
    @DisplayName("Test the player can move a boulder onto a switch and activate a Bomb")
    public void testLogicORBomb() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_bombTest_logicOR", "c_bombTest_placeBombRadius2");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
        EntityResponse initBoulder = getEntities((initDungonRes), "boulder").get(0);
        EntityResponse initFloorSwitch = getEntities((initDungonRes), "switch").get(0);
        EntityResponse initBomb = getEntities((initDungonRes), "bomb").get(0);

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(2, 1), false);
        EntityResponse expectedBoulder = new EntityResponse(initBoulder.getId(), initBoulder.getType(), new Position(3, 1), false);
        EntityResponse expectedFloorSwitch = new EntityResponse(initFloorSwitch.getId(), initFloorSwitch.getType(), new Position(3, 1), false);


        // move player right pushing the boulder onto the switch and activating adjacent Bomb
        DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        EntityResponse actualBoulder = getEntities(actualDungonRes, "boulder").get(0);
        EntityResponse actualFloorSwitch = getEntities(actualDungonRes, "switch").get(0);
        EntityResponse actualBomb = getEntities(actualDungonRes, "bomb").get(0);
        EntityResponse expectedBomb = new EntityResponse(initBomb.getId(), "bomb", initBomb.getPosition(), initBomb.isInteractable());
        // assert after movement, boulder is on switch and the bomb is activated
        assertEquals(expectedPlayer, actualPlayer);
        assertEquals(expectedBoulder, actualBoulder);
        assertEquals(expectedFloorSwitch, actualFloorSwitch);


        assertEquals(actualBomb, expectedBomb);

    }

//    @Test
//    public void test_logic_and_bomb() {
//        DungeonManiaController dmc = new DungeonManiaController();
//        DungeonResponse initDungonRes = dmc.newGame("d_bomb_logic_test", "c_bombTest_placeBombRadius2");
//
//        DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);
//        assert getEntities(actualDungonRes, "bomb").size() == 0;
//        assert getEntities(actualDungonRes, "switch").size() == 0;
//    }
}
