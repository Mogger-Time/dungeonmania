package dungeonmania.MovingEntities;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;
import static dungeonmania.TestUtils.getPlayer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class TestMercenary {
    @Test
    @DisplayName("Test basic Mercenary movements")
    public void testBasicMercenaryMovement() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest_testBasicMercenaryMovement", "c_mercenaryTest");
        
        // when the player moves up, the mercenary should move down or left 
        res = dmc.tick(Direction.UP);
        Position mercenaryPos = getEntities(res, "mercenary").get(0).getPosition();
        assertTrue(
            (mercenaryPos.getX() == 3 && mercenaryPos.getY() == 7) || 
            (mercenaryPos.getX() == 2 && mercenaryPos.getY() == 8)
        );
    }

    @Test
    @DisplayName("Test Mercenary collides with a wall")
    public void testMercenaryCollidesWall() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest_testMercenaryCollidesWall", "c_mercenaryTest");
        // when the mercenary collides with a wall, they do not move from their current position
        res = dmc.tick(Direction.UP);
        Position newMercenaryPos = getEntities(res, "mercenary").get(0).getPosition();

        // assert mercenary moves left
        assertTrue(newMercenaryPos.getX() == 2 && newMercenaryPos.getY() == 7);
    }

    @Test
    @DisplayName("Test Player is not within bribing radius of the Mercenary")
    public void testPlayerOutOfBribeRadius() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest_testPlayerOutOfBribeRadius", "c_mercenaryTest");
        EntityResponse initMercenary = getEntities(res, "mercenary").get(0);
        EntityResponse initPlayer = getPlayer(res).get();

        // player starts at (1,1) and collects coins
        // mercenary moves towards player
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        EntityResponse actualPlayer = getPlayer(res).get();
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(4, 3), initPlayer.isInteractable());
        assertEquals(expectedPlayer, actualPlayer);

        EntityResponse actualMercenary = getEntities(res, "mercenary").get(0);
        Position mercenaryPos = actualMercenary.getPosition();
        Position playerPos = actualPlayer.getPosition();
        if (Math.abs(mercenaryPos.getX() - playerPos.getX()) != 1 && Math.abs(mercenaryPos.getY() - playerPos.getY()) != 1) {
            assertThrows(InvalidActionException.class, () -> {dmc.interact(initMercenary.getId());}, 
            "Mercenary out of range");
        }
    }

    @Test
    @DisplayName("Test Mercenary has been bribed")
    public void testMercenaryBribed() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest_testMercenaryBribed", "c_mercenaryTest");
        EntityResponse initMercenary = getEntities(res, "mercenary").get(0);
        EntityResponse initPlayer = getPlayer(res).get();

        // player starts at (1,1), collects coins and moves towards mercenary to bribe
        // mercenary moves towards player
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        int treasure = getInventory(res, "treasure").size();
        assert treasure == 2;
        res = dmc.tick(Direction.LEFT);

        EntityResponse actualPlayer = getPlayer(res).get();
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(3, 3), initPlayer.isInteractable());
        assertEquals(expectedPlayer, actualPlayer);
        
        EntityResponse actualMercenary = getEntities(res, "mercenary").get(0);
        EntityResponse expectedMercenary = new EntityResponse(initMercenary.getId(), initMercenary.getType(), new Position(3, 4), initMercenary.isInteractable());
        assertEquals(expectedMercenary, actualMercenary);

        res = assertDoesNotThrow(() -> dmc.interact(initMercenary.getId()));
    }

    @Test
    @DisplayName("Test mercenary battle with player - player loses")
    public void testMercenaryBattlePlayerLoses() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_battleTest_basicMercenary", "c_battleTests_basicMercenaryPlayerDies");

        res = dmc.tick(Direction.DOWN);

        // assert there are no player in the game
        assertEquals(getEntities(res, "player").size(), 0);
        assertEquals(getEntities(res, "mercenary").size(), 1);
    }

    @Test
    @DisplayName("Test mercenary battle with player - player wins")
    public void testMercenaryBattlePlayerWins() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_battleTest_basicMercenary", "c_battleTests_basicMercenaryMercenaryDies");

        res = dmc.tick(Direction.DOWN);

        // assert there is no mercenary in the game
        assertEquals(getEntities(res, "mercenary").size(), 0);
        assertEquals(getEntities(res, "player").size(), 1);
    }
}
