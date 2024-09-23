package dungeonmania.StaticEntities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static dungeonmania.TestUtils.getPlayer;
import static dungeonmania.TestUtils.getEntities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class TestBoulder {
    @Test
    @DisplayName("Test the player can push a boulder cardinally")
    public void testBoulderPush() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_bouldersOnlyTest", "c_boulderTest");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
        EntityResponse boulder0 = getEntities(initDungonRes, "boulder").get(0);   

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(2, 1), false);
        EntityResponse expectedBoulder = new EntityResponse(boulder0.getId(), boulder0.getType(), new Position(3, 1), false);

        // move player right, pushing the boulder
        DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        EntityResponse actualBoulder = getEntities(actualDungonRes, "boulder").get(0);

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);
        assertEquals(expectedBoulder, actualBoulder);
    }
    @Test
    @DisplayName("Test the player can't push two boulders at once")
    public void testBoulderPushTwoSimultaneously() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_bouldersOnlyTest", "c_boulderTest");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
        EntityResponse boulder2 = getEntities(initDungonRes, "boulder").get(2);   

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 1), false);
        EntityResponse expectedBoulder = new EntityResponse(boulder2.getId(), boulder2.getType(), new Position(1, 2), false);

        // move player downward, not moving and not pushing the boulder
        DungeonResponse actualDungonRes = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        EntityResponse actualBoulder = getEntities(actualDungonRes, "boulder").get(2);

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);
        assertEquals(expectedBoulder, actualBoulder);
    }
    @Test
    @DisplayName("Test the player pushing multiple boulders one after another")
    public void testBoulderPushAfterBoulder() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_bouldersOnlyTest", "c_boulderTest");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
        EntityResponse boulder1 = getEntities(initDungonRes, "boulder").get(1);   
        EntityResponse boulder2 = getEntities(initDungonRes, "boulder").get(2);  
        EntityResponse boulder3 = getEntities(initDungonRes, "boulder").get(3);   

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 2), false);
        EntityResponse expectedBoulder1 = new EntityResponse(boulder1.getId(), boulder1.getType(), new Position(0, 3), false);
        EntityResponse expectedBoulder2 = new EntityResponse(boulder2.getId(), boulder2.getType(), new Position(2, 2), false);
        EntityResponse expectedBoulder3 = new EntityResponse(boulder3.getId(), boulder3.getType(), new Position(3, 2), false);

        // move player downward, not moving and not pushing the boulder
        DungeonResponse actualDungonRes = dmc.tick(Direction.DOWN); // Does not push
        actualDungonRes = dmc.tick(Direction.LEFT);
        actualDungonRes = dmc.tick(Direction.DOWN); // Pushes boulder 1 down, onto a key
        actualDungonRes = dmc.tick(Direction.RIGHT); // Pushes boulder 2 left
        actualDungonRes = dmc.tick(Direction.RIGHT); // Does not push
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        EntityResponse actualBoulder1 = getEntities(actualDungonRes, "boulder").get(1);
        EntityResponse actualBoulder2 = getEntities(actualDungonRes, "boulder").get(2);
        EntityResponse actualBoulder3 = getEntities(actualDungonRes, "boulder").get(3);

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);
        assertEquals(expectedBoulder1, actualBoulder1);
        assertEquals(expectedBoulder2, actualBoulder2);
        assertEquals(expectedBoulder3, actualBoulder3);
    }

    @Test
    @DisplayName("Test the player can't push a boulder into wall")
    public void testBoulderPushIntoWall() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_bouldersCollision", "c_boulderTest");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
        EntityResponse boulder0 = getEntities(initDungonRes, "boulder").get(0);   

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 1), false);
        EntityResponse expectedBoulder = new EntityResponse(boulder0.getId(), boulder0.getType(), new Position(2, 1), false);

        // move player right, pushing the boulder but not moving as there is a wall
    
        DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        EntityResponse actualBoulder = getEntities(actualDungonRes, "boulder").get(0);

        // assert after movement
        assertEquals(expectedBoulder, actualBoulder);
        assertEquals(expectedPlayer, actualPlayer);
        
    }

}
