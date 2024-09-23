package dungeonmania.LogicalEntities;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static dungeonmania.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestLightBulb {
    @Test
    @DisplayName("Test the player can move a boulder onto a switch and activate a lightbulb")
    public void testBoulderOnSwitchActivatesLightbulb() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_testLightBulb", "c_floorSwitchTest_basic");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
        EntityResponse initBoulder = getEntities((initDungonRes), "boulder").get(0);
        EntityResponse initFloorSwitch = getEntities((initDungonRes), "switch").get(0);
        EntityResponse initLightBulb = getEntities((initDungonRes), "light_bulb_off").get(0);

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(2, 1), false);
        EntityResponse expectedBoulder = new EntityResponse(initBoulder.getId(), initBoulder.getType(), new Position(3, 1), false);
        EntityResponse expectedFloorSwitch = new EntityResponse(initFloorSwitch.getId(), initFloorSwitch.getType(), new Position(3, 1), false);


        // move player right pushing the boulder onto the switch and activating adjacent lightbulb
        DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        EntityResponse actualBoulder = getEntities(actualDungonRes, "boulder").get(0);
        EntityResponse actualFloorSwitch = getEntities(actualDungonRes, "switch").get(0);
        EntityResponse actualLightBulb = getEntities(actualDungonRes, "light_bulb_on").get(0);
        EntityResponse expectedLightBulb = new EntityResponse(initLightBulb.getId(), "light_bulb_on", initLightBulb.getPosition(), initLightBulb.isInteractable());
        // assert after movement, boulder is on switch and the light bulb is activated
        assertEquals(expectedPlayer, actualPlayer);
        assertEquals(expectedBoulder, actualBoulder);
        assertEquals(expectedFloorSwitch, actualFloorSwitch);


        assertEquals(actualLightBulb, expectedLightBulb);

    }

    @Test
    @DisplayName("Test the player can move a boulder onto a switch to activate both lights")
    public void testBoulderOnSwitchTriggersWiredLightbulb() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_testLightBulb", "c_floorSwitchTest_basic");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
        EntityResponse initBoulder = getEntities((initDungonRes), "boulder").get(0);
        EntityResponse initFloorSwitch = getEntities((initDungonRes), "switch").get(0);
        EntityResponse initLightBulb = getEntities((initDungonRes), "light_bulb_off").get(1);

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(2, 1), false);
        EntityResponse expectedBoulder = new EntityResponse(initBoulder.getId(), initBoulder.getType(), new Position(3, 1), false);
        EntityResponse expectedFloorSwitch = new EntityResponse(initFloorSwitch.getId(), initFloorSwitch.getType(), new Position(3, 1), false);


        // move player right pushing the boulder onto the switch and activating wired Lightbulb
        DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        EntityResponse actualBoulder = getEntities(actualDungonRes, "boulder").get(0);
        EntityResponse actualFloorSwitch = getEntities(actualDungonRes, "switch").get(0);
        EntityResponse actualLightBulb = getEntities(actualDungonRes, "light_bulb_on").get(1);
        EntityResponse expectedLightBulb = new EntityResponse(initLightBulb.getId(), "light_bulb_on", initLightBulb.getPosition(), initLightBulb.isInteractable());
        // assert after movement, boulder is on switch and the light bulb is activated
        assertEquals(expectedPlayer, actualPlayer);
        assertEquals(expectedBoulder, actualBoulder);
        assertEquals(expectedFloorSwitch, actualFloorSwitch);
        // Two on
        int onCount = countEntityOfType(actualDungonRes, "light_bulb_on");
        assertEquals(onCount, 2);
        assertEquals(actualLightBulb, expectedLightBulb);
    }

    @Test
    @DisplayName("Test the player can move a boulder off a switch to deactivate both lights")
    public void testBoulderOffSwitch() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_testLightBulb", "c_floorSwitchTest_basic");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
        EntityResponse initBoulder = getEntities((initDungonRes), "boulder").get(0);
        EntityResponse initFloorSwitch = getEntities((initDungonRes), "switch").get(0);
        EntityResponse initLightBulb = getEntities((initDungonRes), "light_bulb_off").get(1);

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(2, 1), false);
        EntityResponse expectedBoulder = new EntityResponse(initBoulder.getId(), initBoulder.getType(), new Position(3, 1), false);
        EntityResponse expectedFloorSwitch = new EntityResponse(initFloorSwitch.getId(), initFloorSwitch.getType(), new Position(3, 1), false);


        // move player right pushing the boulder onto the switch and activating wired Lightbulb
        DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        EntityResponse actualBoulder = getEntities(actualDungonRes, "boulder").get(0);
        EntityResponse actualFloorSwitch = getEntities(actualDungonRes, "switch").get(0);
        EntityResponse actualLightBulb = getEntities(actualDungonRes, "light_bulb_on").get(1);
        EntityResponse expectedLightBulb = new EntityResponse(initLightBulb.getId(), "light_bulb_on", initLightBulb.getPosition(), initLightBulb.isInteractable());
        // assert after movement, boulder is on switch and the light bulb is activated
        assertEquals(expectedPlayer, actualPlayer);
        assertEquals(expectedBoulder, actualBoulder);
        assertEquals(expectedFloorSwitch, actualFloorSwitch);
        // Two on
        int onCount = countEntityOfType(actualDungonRes, "light_bulb_on");
        assertEquals(onCount, 2);
        assertEquals(actualLightBulb, expectedLightBulb);
        // Get the boulder off the switch
        actualDungonRes = dmc.tick(Direction.DOWN);
        actualDungonRes = dmc.tick(Direction.RIGHT);
        actualDungonRes = dmc.tick(Direction.UP);

        int offCount = countEntityOfType(actualDungonRes, "light_bulb_off");
        onCount = countEntityOfType(actualDungonRes, "light_bulb_on");

        assertEquals(onCount, 0);
        assertEquals(offCount, 2);
    }

    @Test
    @DisplayName("Test the player can move a boulder onto a switch not activating and bulb")
    public void testBoulderOnSwitchDoesNotTriggersANDLightBulb() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_testLightBulbAND", "c_floorSwitchTest_basic");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
        EntityResponse initBoulder = getEntities((initDungonRes), "boulder").get(0);
        EntityResponse initFloorSwitch = getEntities((initDungonRes), "switch").get(0);

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(2, 1), false);
        EntityResponse expectedBoulder = new EntityResponse(initBoulder.getId(), initBoulder.getType(), new Position(3, 1), false);
        EntityResponse expectedFloorSwitch = new EntityResponse(initFloorSwitch.getId(), initFloorSwitch.getType(), new Position(3, 1), false);


        // move player right pushing the boulder onto the switch and activating wired Lightbulb
        DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        EntityResponse actualBoulder = getEntities(actualDungonRes, "boulder").get(0);
        EntityResponse actualFloorSwitch = getEntities(actualDungonRes, "switch").get(0);

        // assert after movement, boulder is on switch and the light bulb is activated
        assertEquals(expectedPlayer, actualPlayer);
        assertEquals(expectedBoulder, actualBoulder);
        assertEquals(expectedFloorSwitch, actualFloorSwitch);
        // Two off
        int onCount = countEntityOfType(actualDungonRes, "light_bulb_on");
        int offCount = countEntityOfType(actualDungonRes, "light_bulb_off");
        assertEquals(offCount, 1);
        assertEquals(onCount, 1);

    }

    @Test
    @DisplayName("Test the player can move a boulder onto a switch activating xor lightbulb")
    public void testBoulderOnXORLightbulb() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_testLightBulbXOR", "c_floorSwitchTest_basic");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
        EntityResponse initBoulder = getEntities((initDungonRes), "boulder").get(0);
        EntityResponse initFloorSwitch = getEntities((initDungonRes), "switch").get(0);
        EntityResponse initLightBulb = getEntities((initDungonRes), "light_bulb_off").get(1);

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(2, 1), false);
        EntityResponse expectedBoulder = new EntityResponse(initBoulder.getId(), initBoulder.getType(), new Position(3, 1), false);
        EntityResponse expectedFloorSwitch = new EntityResponse(initFloorSwitch.getId(), initFloorSwitch.getType(), new Position(3, 1), false);


        // move player right pushing the boulder onto the switch and activating wired Lightbulb
        DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        EntityResponse actualBoulder = getEntities(actualDungonRes, "boulder").get(0);
        EntityResponse actualFloorSwitch = getEntities(actualDungonRes, "switch").get(0);


        // assert after movement, boulder is on switch and the light bulb is activated
        assertEquals(expectedPlayer, actualPlayer);
        assertEquals(expectedBoulder, actualBoulder);
        assertEquals(expectedFloorSwitch, actualFloorSwitch);
        // One off
        int onCount = countEntityOfType(actualDungonRes, "light_bulb_off");
        int offCount = countEntityOfType(actualDungonRes, "light_bulb_on");
        assertEquals(offCount, 1);
        assertEquals(onCount, 1);
    }

    @Test
    @DisplayName("Test the player can move a boulder onto a switch activating co_and lightbulb")
    public void testBoulderOnCOANDLightbulb() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_testLightBulbCOAND", "c_floorSwitchTest_basic");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
        EntityResponse initBoulder = getEntities((initDungonRes), "boulder").get(0);
        EntityResponse initFloorSwitch = getEntities((initDungonRes), "switch").get(0);


        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(2, 1), false);
        EntityResponse expectedBoulder = new EntityResponse(initBoulder.getId(), initBoulder.getType(), new Position(3, 1), false);
        EntityResponse expectedFloorSwitch = new EntityResponse(initFloorSwitch.getId(), initFloorSwitch.getType(), new Position(3, 1), false);


        // move player right pushing the boulder onto the switch and activating wired Lightbulb
        DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        EntityResponse actualBoulder = getEntities(actualDungonRes, "boulder").get(0);
        EntityResponse actualFloorSwitch = getEntities(actualDungonRes, "switch").get(0);


        // assert after movement, boulder is on switch and the light bulb is activated
        assertEquals(expectedPlayer, actualPlayer);
        assertEquals(expectedBoulder, actualBoulder);
        assertEquals(expectedFloorSwitch, actualFloorSwitch);
        // Zero off
        int offCount = countEntityOfType(actualDungonRes, "light_bulb_off");
        int onCount = countEntityOfType(actualDungonRes, "light_bulb_on");
        assertEquals(offCount, 0);
        assertEquals(onCount, 2);
    }
}
