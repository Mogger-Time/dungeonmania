package dungeonmania.MovingEntities;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;
import static dungeonmania.TestUtils.getPlayer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class TestZombieToast {
    @Test
    @DisplayName("Test basic movement of ZombieToast")
    public void testBasicMovement() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieTest_portalInteraction", "c_genericTest");
        Position initPos = getEntities(res, "zombie_toast").get(0).getPosition();
        
        // Tick the game
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        
        // assert after movement
        assertNotEquals(initPos.getX(), getEntities(res, "zombie_toast").get(0).getPosition().getX());
        assertEquals(initPos.getY(), getEntities(res, "zombie_toast").get(0).getPosition().getY());
    }

    @Test
    @DisplayName("Test ZombieToast cannot move through a closed door")
    public void testZombieToastCannotMoveThroughClosedDoor() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieTest_doorInteraction", "c_genericTest");
        Position initPos = getEntities(res, "zombie_toast").get(0).getPosition();
        
        // Tick the game
        res = dmc.tick(Direction.UP);
        Position newPos = getEntities(res, "zombie_toast").get(0).getPosition();
        
        // assert after movement
        assert(newPos.getX() >= 4);
        assertEquals(initPos.getY(), newPos.getY());
    }

    @Test
    @DisplayName("Test ZombieToast can move through a door if it is open")
    public void testZombieToastCanMoveThroughOpenDoor() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieTest_doorInteraction", "c_genericTest");
        Position initPos = getEntities(res, "zombie_toast").get(0).getPosition();
        
        // Move the player right to collect key
        res = dmc.tick(Direction.RIGHT);
        // Move the player right again to open door
        res = dmc.tick(Direction.RIGHT);
        // Move the player left to get out of zombie's way
        res = dmc.tick(Direction.DOWN);
        Position newPos = getEntities(res, "zombie_toast").get(0).getPosition();
        
        // assert after movement
        // moved pass the door or on the door
        assertNotEquals(initPos.getX(), newPos.getX());
        assertEquals(initPos.getY(), newPos.getY());
    }


    @Test
    @DisplayName("Test portals have no effect on ZombieToast")

    public void testZombieToastPortalInteraction() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieTest_portalInteraction", "c_genericTest");

        // Get original position of the zombie
        Position initPos = getEntities(res, "zombie_toast").get(0).getPosition();
        // Tick the game to move the player up
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        // Get the new position of the zombie
        Position newPos = getEntities(res, "zombie_toast").get(0).getPosition();
        // Assert that the zombie has not teleported
        assertEquals(initPos.getY(), newPos.getY());
    }

    @Test
    @DisplayName("Test ZombieToast cannot move a boulder")
    public void testZombieToastCannotMoveBoulder() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieTest_boulderInteraction", "c_genericTest");
        Position initPos = getEntities(res, "zombie_toast").get(0).getPosition();
        
        // Tick the game
        res = dmc.tick(Direction.UP);
        Position newPos = getEntities(res, "zombie_toast").get(0).getPosition();
        
        // assert zombie in the same position
        assertEquals(initPos, newPos);
    }

    @Test
    @DisplayName("Test ZombieToast runs away when player are invincible")
    public void testZombieToastRunsAwayWhenPlayerIsInvincible() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieTest_invinPotionInteraction", "c_genericTest");
        EntityResponse initZombie = getEntities(res, "zombie_toast").get(0);
        EntityResponse initPlayer = getPlayer(res).get();

        // Move player right to collect invincibility potion
        res = dmc.tick(Direction.RIGHT);
        // Use invincibility potion to make player invincible
        List<ItemResponse> inventory = getInventory(res, "invincibility_potion");
        assert inventory.size() == 1;
        assertEquals("invincibility_potion", inventory.get(0).getType());
        assertDoesNotThrow(() -> dmc.tick(inventory.get(0).getId()));

        EntityResponse actualPlayer = getPlayer(res).get();
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(2, 1), initPlayer.isInteractable());
        assertEquals(expectedPlayer, actualPlayer);
        
        EntityResponse actualZombie = getEntities(res, "zombie_toast").get(0);
        EntityResponse expectedZombie = new EntityResponse(initZombie.getId(), initZombie.getType(), new Position(5, 1), initZombie.isInteractable());
        assertEquals(expectedZombie, actualZombie);

        // Move player towards zombie
        res = dmc.tick(Direction.RIGHT);
        
        actualZombie = getEntities(res, "zombie_toast").get(0);
        
        
        // Assert that zombie is "Running away" from player (Keeping the same distance from player)
       
    }
}
