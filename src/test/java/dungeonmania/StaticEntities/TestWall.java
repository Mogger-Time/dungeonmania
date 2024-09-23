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


public class TestWall {
    @Test
    @DisplayName("Test the player can't move down through the wall")
    public void testWallCollisionDown() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_wallTest_basicCollision", "c_staticEntity_testGeneric");
        EntityResponse initPlayer = getPlayer(initDungeonRes).get();

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 1), false);

        // move player downward
        DungeonResponse actualDungeonRes = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer = getPlayer(actualDungeonRes).get();

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);
    }
    
    @Test
    @DisplayName("Test the player can't move up through the wall")
    public void testWallCollisionUp() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_wallTest_basicCollision", "c_staticEntity_testGeneric");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 1), false);

        // move player upward
        DungeonResponse actualDungonRes = dmc.tick(Direction.UP);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);
    }

    @Test
    @DisplayName("Test the player can't move through the wall after a short path")
    public void testWallCollisionBasicMovement() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_wallTest_basicCollision", "c_staticEntity_testGeneric");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(0, 2), false);

        // move player left, down then right into a wall
        DungeonResponse actualDungonRes = dmc.tick(Direction.LEFT);
        actualDungonRes = dmc.tick(Direction.DOWN);
        actualDungonRes = dmc.tick(Direction.RIGHT);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);
    }

    @Test
    @DisplayName("Test the enemies can't move through the wall")
    public void testWallCollisionEnemy() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_wallTest_basicCollision", "c_staticEntity_testGeneric");
        EntityResponse zombie_toast = getEntities(initDungonRes, "zombie_toast").get(0);   
        //Position pos = getEntities(initDungonRes, "zombie_toast").get(0).getPosition();

        // create the expected result
        EntityResponse expectedZombie = new EntityResponse(zombie_toast.getId(), zombie_toast.getType(), new Position(3, 1), true);

        // move player left to tick the game once
        DungeonResponse actualDungonRes = dmc.tick(Direction.LEFT);
        EntityResponse actualZombie = getEntities(actualDungonRes, "zombie_toast").get(0);

        // assert after movement, zombie remains between walls
        assertEquals(expectedZombie, actualZombie);
    }

    @Test
    @DisplayName("Test Boulder's can't be pushed through a wall")
    public void testWallCollisionBoulder() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_wallTest_basicCollision", "c_staticEntity_testGeneric");
        EntityResponse initPlayer = getPlayer(initDungeonRes).get();
        EntityResponse boulder = getEntities(initDungeonRes, "boulder").get(0);   

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(0, 3), false);
        EntityResponse expectedBoulder = new EntityResponse(boulder.getId(), boulder.getType(), new Position(0, 4), false);

        // move player left to tick the game once
        DungeonResponse actualDungeonRes = dmc.tick(Direction.LEFT);
        actualDungeonRes = dmc.tick(Direction.DOWN);
        actualDungeonRes = dmc.tick(Direction.DOWN);
        actualDungeonRes = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer = getPlayer(actualDungeonRes).get();
        EntityResponse actualBoulder = getEntities(actualDungeonRes, "boulder").get(0);

        // assert after movement, player did not push the boulder
        assertEquals(expectedPlayer, actualPlayer);
        assertEquals(expectedBoulder, actualBoulder);
    }

}  