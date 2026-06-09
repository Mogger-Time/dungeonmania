package dungeonmania.StaticEntities;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static dungeonmania.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;


public class TestFloorSwitch {
    @Test
    @DisplayName("Test the player can move a boulder onto a switch")
    public void testBoulderOnSwitch() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_floorSwitchTest_basic", "c_floorSwitchTest_basic");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
        EntityResponse initBoulder = getEntities((initDungonRes), "boulder").get(1);
        EntityResponse initFloorSwitch = getEntities((initDungonRes), "switch").get(0);

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 2), false);
        EntityResponse expectedBoulder = new EntityResponse(initBoulder.getId(), initBoulder.getType(), new Position(1, 3), false);
        EntityResponse expectedFloorSwitch = new EntityResponse(initFloorSwitch.getId(), initFloorSwitch.getType(), new Position(1, 3), false);

        // move player downward pushing the boulder onto the switch
        DungeonResponse actualDungonRes = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        EntityResponse actualBoulder = getEntities(actualDungonRes, "boulder").get(1);
        EntityResponse actualFloorSwitch = getEntities(actualDungonRes, "switch").get(0);

        // assert after movement, boulder is on switch and there is still one unactive switch
        assertEquals(expectedPlayer, actualPlayer);
        assertEquals(expectedBoulder, actualBoulder);
        assertEquals(expectedFloorSwitch, actualFloorSwitch);
        assertTrue(getGoals(actualDungonRes).contains(":boulders"));
    }

    @Test
    @DisplayName("Test the player can move both boulders onto a switch with entities, completing the goal")
    public void testBouldersAcheiveGoal() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_floorSwitchTest_basic", "c_floorSwitchTest_basic");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
        EntityResponse initBoulder1 = getEntities((initDungonRes), "boulder").get(1);
        EntityResponse initBoulder2 = getEntities((initDungonRes), "boulder").get(0);
        EntityResponse initFloorSwitch1 = getEntities((initDungonRes), "switch").get(0);
        EntityResponse initFloorSwitch2 = getEntities((initDungonRes), "switch").get(1);

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 2), false);
        EntityResponse expectedBoulder1 = new EntityResponse(initBoulder1.getId(), initBoulder1.getType(), new Position(1, 3), false);
        EntityResponse expectedFloorSwitch1 = new EntityResponse(initFloorSwitch1.getId(), initFloorSwitch1.getType(), new Position(1, 3), false);

        // move player downward pushing the boulder onto the switch
        DungeonResponse actualDungonRes = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        EntityResponse actualBoulder1 = getEntities(actualDungonRes, "boulder").get(1);

        // assert after movement, boulder is on switch and there is still one unactive switch
        assertEquals(expectedPlayer, actualPlayer);
        assertEquals(expectedBoulder1, actualBoulder1);
        assertEquals(expectedFloorSwitch1, initFloorSwitch1);

        //assertTrue(getGoals(actualDungonRes).contains(":boulders"));

        // expected conditions for after pushing the remaining boulder
        expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(2, 1), false);
        EntityResponse expectedFloorSwitch2 = new EntityResponse(initFloorSwitch2.getId(), initFloorSwitch2.getType(), new Position(3, 1), false);
        EntityResponse expectedBoulder2 = new EntityResponse(initBoulder2.getId(), initBoulder2.getType(), new Position(3, 1), false);

        // move player to push remaining boulder onto switch
        actualDungonRes = dmc.tick(Direction.UP);
        actualDungonRes = dmc.tick(Direction.RIGHT);

        actualPlayer = getPlayer(actualDungonRes).get();

        assertEquals(expectedPlayer, actualPlayer);
        assertEquals(expectedBoulder2, expectedBoulder2);
        assertEquals(expectedFloorSwitch2, initFloorSwitch2);

        assertFalse(getGoals(actualDungonRes).contains(":boulders"));

    }

    @Test
    @DisplayName("Test that collectables can spawn on top of a switch")
    public void testCollectablesOnSwitch() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse dungeonRes = dmc.newGame("d_floorSwitchTest_collectables", "c_floorSwitchTest_basic");

        // Assert that there are no arrows in inventory
        int arrowsInInv = getInventory(dungeonRes, "arrow").size();
        assertEquals(0, arrowsInInv);

        // Assert that the arrow is on switch 1
        Position arrowPos = getEntities((dungeonRes), "arrow").get(0).getPosition();
        Position floorSwitchPos1 = getEntities((dungeonRes), "switch").get(0).getPosition();
        assertEquals(arrowPos, floorSwitchPos1);

        dungeonRes = dmc.tick(Direction.DOWN);
        dungeonRes = dmc.tick(Direction.DOWN);

        // Assert that there is now an arrow in inventory
        arrowsInInv = getInventory(dungeonRes, "arrow").size();
        assertEquals(1, arrowsInInv);

        // Assert that there is no wood in inventory
        int woodInInv = getInventory(dungeonRes, "wood").size();
        assertEquals(0, woodInInv);

        // Assert that the wood is on switch 2
        Position woodPos = getEntities((dungeonRes), "wood").get(0).getPosition();
        Position floorSwitchPos2 = getEntities((dungeonRes), "switch").get(1).getPosition();
        assertEquals(woodPos, floorSwitchPos2);

        dungeonRes = dmc.tick(Direction.RIGHT);
        dungeonRes = dmc.tick(Direction.RIGHT);
        dungeonRes = dmc.tick(Direction.UP);
        dungeonRes = dmc.tick(Direction.UP);

        // Assert that there is now a piece of wood in inventory
        woodInInv = getInventory(dungeonRes, "wood").size();
        assertEquals(1, woodInInv);
    }
}
