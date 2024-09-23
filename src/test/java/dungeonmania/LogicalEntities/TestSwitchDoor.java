package dungeonmania.LogicalEntities;

import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getPlayer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static dungeonmania.TestUtils.getInventory;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class TestSwitchDoor {
    @Test
    @DisplayName("Test the player can open the door with a switch")
    public void testBoulderOnSwitchActivatesSwitchDoor() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_switchDoorTest", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
        EntityResponse initBoulder = getEntities((initDungonRes), "boulder").get(0);
        EntityResponse initFloorSwitch = getEntities((initDungonRes), "switch").get(0);
        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(4, 1), false);
        EntityResponse expectedBoulder = new EntityResponse(initBoulder.getId(), initBoulder.getType(), new Position(3,1), false);
        EntityResponse expectedFloorSwitch = new EntityResponse(initFloorSwitch.getId(), initFloorSwitch.getType(), new Position(3,1), false);
        

        // move player right pushing the boulder onto the switch and activating adjacent door
        DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);
        actualDungonRes = dmc.tick(Direction.UP);
        actualDungonRes = dmc.tick(Direction.RIGHT);
        actualDungonRes = dmc.tick(Direction.RIGHT);
        // Walk to the door and attempt to go through
        actualDungonRes = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        EntityResponse actualBoulder = getEntities(actualDungonRes, "boulder").get(0);
        EntityResponse actualFloorSwitch = getEntities(actualDungonRes, "switch").get(0);

        // assert after movement, boulder is on switch and the player is in right spot
        assertEquals(expectedPlayer, actualPlayer);
        assertEquals(expectedBoulder, actualBoulder);
        assertEquals(expectedFloorSwitch, actualFloorSwitch);

        Position pos = getEntities(actualDungonRes, "player").get(0).getPosition();
        // Passes through the door
        actualDungonRes = dmc.tick(Direction.DOWN);
        
    
        assertNotEquals(pos, getEntities(actualDungonRes, "player").get(0).getPosition());

    }
    @Test
    @DisplayName("Test the player can't walk through the unactivated switch door")
    public void testCanNotWalkThroughSwitchDoor() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_switchDoorTest", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(4, 0), false);
    

        // move player up not pushing the boulder onto the switch and then moving to the door
        DungeonResponse actualDungonRes = dmc.tick(Direction.UP);
        actualDungonRes = dmc.tick(Direction.RIGHT);
        actualDungonRes = dmc.tick(Direction.RIGHT);
        actualDungonRes = dmc.tick(Direction.RIGHT);
        // Walk to the door and attempt to go through
        actualDungonRes = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
 
        // assert after movement, boulder is not on the switch and the player is in right spot
        assertEquals(expectedPlayer, actualPlayer);


        // Does not pass through the door
        actualDungonRes = dmc.tick(Direction.DOWN);
        assertEquals(expectedPlayer, actualPlayer);
        
    

    }
    @Test
    @DisplayName("Test player can use a key to open and walk through a switch door")
    public void useKeyWalkThroughOpenDoor() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_switchDoorTest", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");

        // pick up key
        res = dmc.tick(Direction.LEFT);
        assertEquals(1, getInventory(res, "key").size());

        // walk to door and check key is gone
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        Position pos = getEntities(res, "player").get(0).getPosition();
        //Go through door
        res = dmc.tick(Direction.DOWN);
        assertEquals(0, getInventory(res, "key").size());
        //Assert player went through door
        assertNotEquals(pos, getEntities(res, "player").get(0).getPosition());
    }
    @Test
    @DisplayName("Test the player can't open the and door with one switch")
    public void testBoulderOnSwitchAND() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_switchDoorTestAND", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
        EntityResponse initBoulder = getEntities((initDungonRes), "boulder").get(0);
        EntityResponse initFloorSwitch = getEntities((initDungonRes), "switch").get(0);
        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(4, 0), false);
        EntityResponse expectedBoulder = new EntityResponse(initBoulder.getId(), initBoulder.getType(), new Position(3,1), false);
        EntityResponse expectedFloorSwitch = new EntityResponse(initFloorSwitch.getId(), initFloorSwitch.getType(), new Position(3,1), false);
        

        // move player right pushing the boulder onto the switch and activating adjacent door
        DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);
        actualDungonRes = dmc.tick(Direction.UP);
        actualDungonRes = dmc.tick(Direction.RIGHT);
        actualDungonRes = dmc.tick(Direction.RIGHT);
        // Walk to the door and attempt to go through
        actualDungonRes = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        EntityResponse actualBoulder = getEntities(actualDungonRes, "boulder").get(0);
        EntityResponse actualFloorSwitch = getEntities(actualDungonRes, "switch").get(0);

        // assert after movement, boulder is on switch and the player is in right spot, doesn't go through door.
        assertEquals(expectedPlayer, actualPlayer);
        assertEquals(expectedBoulder, actualBoulder);
        assertEquals(expectedFloorSwitch, actualFloorSwitch);

        Position pos = getEntities(actualDungonRes, "player").get(0).getPosition();
        // Does not pass through the door
        actualDungonRes = dmc.tick(Direction.DOWN);
        
    
        assertEquals(pos, getEntities(actualDungonRes, "player").get(0).getPosition());

    }
    @Test
    @DisplayName("Test the player can't open the xor door when double activated")
    public void testBoulderOnSwitchXOR() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_switchDoorTestXOR", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
        EntityResponse initBoulder = getEntities((initDungonRes), "boulder").get(0);
        EntityResponse initFloorSwitch = getEntities((initDungonRes), "switch").get(0);
        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(4, 0), false);
        EntityResponse expectedBoulder = new EntityResponse(initBoulder.getId(), initBoulder.getType(), new Position(3,1), false);
        EntityResponse expectedFloorSwitch = new EntityResponse(initFloorSwitch.getId(), initFloorSwitch.getType(), new Position(3,1), false);
        

        // move player right pushing the boulder onto the switch and activating adjacent door
        DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);
        actualDungonRes = dmc.tick(Direction.UP);
        actualDungonRes = dmc.tick(Direction.RIGHT);
        actualDungonRes = dmc.tick(Direction.RIGHT);
        // Walk to the door and attempt to go through
        actualDungonRes = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        EntityResponse actualBoulder = getEntities(actualDungonRes, "boulder").get(0);
        EntityResponse actualFloorSwitch = getEntities(actualDungonRes, "switch").get(0);

        // assert after movement, boulder is on switch and the player is in right spot, doesn't go through door
        assertEquals(expectedPlayer, actualPlayer);
        assertEquals(expectedBoulder, actualBoulder);
        assertEquals(expectedFloorSwitch, actualFloorSwitch);

        Position pos = getEntities(actualDungonRes, "player").get(0).getPosition();
        // Does not pass through the door
        actualDungonRes = dmc.tick(Direction.DOWN);
        
    
        assertEquals(pos, getEntities(actualDungonRes, "player").get(0).getPosition());

    }

    @Test
    @DisplayName("Test the player can go through coand door when double activated")
    public void testBoulderOnSwitchCOAND() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_switchDoorTestCOAND", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
        EntityResponse initBoulder = getEntities((initDungonRes), "boulder").get(0);
        EntityResponse initFloorSwitch = getEntities((initDungonRes), "switch").get(0);
        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(4, 1), false);
        EntityResponse expectedBoulder = new EntityResponse(initBoulder.getId(), initBoulder.getType(), new Position(3,1), false);
        EntityResponse expectedFloorSwitch = new EntityResponse(initFloorSwitch.getId(), initFloorSwitch.getType(), new Position(3,1), false);
        

        // move player right pushing the boulder onto the switch and activating adjacent door
        DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);
        actualDungonRes = dmc.tick(Direction.UP);
        actualDungonRes = dmc.tick(Direction.RIGHT);
        actualDungonRes = dmc.tick(Direction.RIGHT);
        // Walk to the door and attempt to go through
        actualDungonRes = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        EntityResponse actualBoulder = getEntities(actualDungonRes, "boulder").get(0);
        EntityResponse actualFloorSwitch = getEntities(actualDungonRes, "switch").get(0);

        // assert after movement, boulder is on switch and the player is in right spot, doesn't go through door
        assertEquals(expectedPlayer, actualPlayer);
        assertEquals(expectedBoulder, actualBoulder);
        assertEquals(expectedFloorSwitch, actualFloorSwitch);

        Position pos = getEntities(actualDungonRes, "player").get(0).getPosition();
        // Does not pass through the door
        actualDungonRes = dmc.tick(Direction.DOWN);
        
    
        assertNotEquals(pos, getEntities(actualDungonRes, "player").get(0).getPosition());

    }


   
}