package dungeonmania.items_testing;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;
import static dungeonmania.TestUtils.getPlayer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class TestSceptre {

    @Test
    @DisplayName("Can craft sceptre with 1 wood, 1 key and 1 sunstone") 
    public void testCraftSceptreWKS() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptreTest_crafting", "c_sceptreTest_basic");
        
        res = dmc.tick(Direction.LEFT);
        assertEquals(getInventory(res, "wood").size(), 1);
        res = dmc.tick(Direction.LEFT);
        assertEquals(getInventory(res, "key").size(), 1);
        res = dmc.tick(Direction.LEFT);
        assertEquals(getInventory(res, "sun_stone").size(), 1);
        
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(getInventory(res, "sceptre").size(), 1);
        
        assertEquals(getInventory(res, "wood").size(), 0);
        assertEquals(getInventory(res, "key").size(), 0);
        assertEquals(getInventory(res, "sun_stone").size(), 0);
    }

    @Test
    @DisplayName("Can craft sceptre with 1 wood, 1 treasure and 1 sun stone") 
    public void testCraftSceptreWTS() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptreTest_crafting", "c_sceptreTest_basic");
        
        res = dmc.tick(Direction.LEFT);
        assertEquals(getInventory(res, "wood").size(), 1);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.LEFT);
        assertEquals(getInventory(res, "treasure").size(), 1);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.UP);
        assertEquals(getInventory(res, "sun_stone").size(), 1);
        
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(getInventory(res, "sceptre").size(), 1);
        
        assertEquals(getInventory(res, "wood").size(), 0);
        assertEquals(getInventory(res, "treasure").size(), 0);
        assertEquals(getInventory(res, "sun_stone").size(), 0);
    }

    @Test
    @DisplayName("Can craft sceptre with 2 arrows, 1 key and 1 sun stone") 
    public void testCraftSceptreAKS() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptreTest_crafting", "c_sceptreTest_basic");
        
        res = dmc.tick(Direction.UP);
        assertEquals(getInventory(res, "arrow").size(), 1);
        res = dmc.tick(Direction.LEFT);
        assertEquals(getInventory(res, "arrow").size(), 2);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.DOWN);
        assertEquals(getInventory(res, "key").size(), 1);
        res = dmc.tick(Direction.LEFT);
        assertEquals(getInventory(res, "sun_stone").size(), 1);
        
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(getInventory(res, "sceptre").size(), 1);
        
        assertEquals(getInventory(res, "arrows").size(), 0);
        assertEquals(getInventory(res, "key").size(), 0);
        assertEquals(getInventory(res, "sun_stone").size(), 0);
    }

    @Test
    @DisplayName("Can craft sceptre with 2 arrows, 1 treasure and 1 sun_stone") 
    public void testCraftSceptreATS() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptreTest_crafting", "c_sceptreTest_basic");
        
        res = dmc.tick(Direction.UP);
        assertEquals(getInventory(res, "arrow").size(), 1);
        res = dmc.tick(Direction.LEFT);
        assertEquals(getInventory(res, "arrow").size(), 2);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(getInventory(res, "treasure").size(), 1);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.UP);
        assertEquals(getInventory(res, "sun_stone").size(), 1);
        
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(getInventory(res, "sceptre").size(), 1);
        
        assertEquals(getInventory(res, "arrows").size(), 0);
        assertEquals(getInventory(res, "treasure").size(), 0);
        assertEquals(getInventory(res, "sun_stone").size(), 0);
    }

    @Test
    @DisplayName("Use sceptre to mind control mercenary") 
    public void testSceptreMindControlMercenary() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptreTest_mindControlMerc", "c_sceptreTest_basic");
        EntityResponse initPlayer = getPlayer(res).get();
        EntityResponse initMercenary = getEntities(res, "mercenary").get(0);

        // Collect items and build sceptre
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(getInventory(res, "sceptre").size(), 1);

        // Check if mercenary is 1 tile away from player
        EntityResponse actualPlayer = getPlayer(res).get();
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(3, 1), initPlayer.isInteractable());
        assertEquals(actualPlayer, expectedPlayer);
        Position mercPos = getEntities(res, "mercenary").get(0).getPosition();
        int playerX = actualPlayer.getPosition().getX();
        int playerY = actualPlayer.getPosition().getY();
        assertEquals(playerX + 1, mercPos.getX());
        assertEquals(playerY, mercPos.getY());

        // Interact with mercenary to mind control 
        EntityResponse actualMercenary = getEntities(res, "mercenary").get(0);
        String mercenaryId = actualMercenary.getId();
        res = assertDoesNotThrow(() -> dmc.interact(mercenaryId));
        actualMercenary = getEntities(res, "mercenary").get(0);

        Boolean interactable = actualMercenary.isInteractable();
        assertEquals(false, interactable);

        // Mercenary follows player
        res = dmc.tick(Direction.LEFT);
        expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(2, 1), initPlayer.isInteractable());
        actualPlayer = getPlayer(res).get();
        assertEquals(expectedPlayer, actualPlayer);

        EntityResponse expectedMercenary = new EntityResponse(initMercenary.getId(), initMercenary.getType(), new Position(3, 1), false);
        actualMercenary = getEntities(res, "mercenary").get(0);
        assertEquals(expectedMercenary, actualMercenary);

        res = dmc.tick(Direction.DOWN);
        expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(2, 2), initPlayer.isInteractable());
        actualPlayer = getPlayer(res).get();
        assertEquals(expectedPlayer, actualPlayer);

        expectedMercenary = new EntityResponse(initMercenary.getId(), initMercenary.getType(), new Position(2, 1), false);
        actualMercenary = getEntities(res, "mercenary").get(0);
        assertEquals(expectedMercenary, actualMercenary);

        // Player try to move to mercenary, but they just swap positions
        res = dmc.tick(Direction.UP);
        expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(2, 1), initPlayer.isInteractable());
        actualPlayer = getPlayer(res).get();
        assertEquals(expectedPlayer, actualPlayer);

        expectedMercenary = new EntityResponse(initMercenary.getId(), initMercenary.getType(), new Position(2, 2), false);
        actualMercenary = getEntities(res, "mercenary").get(0);
        assertEquals(expectedMercenary, actualMercenary);

        // Mind control effects worn off from mercenary
        res = dmc.tick(Direction.UP);
        actualMercenary = getEntities(res, "mercenary").get(0);
        interactable = actualMercenary.isInteractable();
        assertEquals(true, interactable);
    }

    @Test
    @DisplayName("Use sceptre to mind control assassin") 
    public void testSceptreMindControlAssassin() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptreTest_mindControlAss", "c_sceptreTest_basic");
        EntityResponse initPlayer = getPlayer(res).get();
        EntityResponse initAssassin = getEntities(res, "assassin").get(0);

        // Build sceptre
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(getInventory(res, "sceptre").size(), 1);

        // Check if assassin is 2 tiles away from player
        EntityResponse actualPlayer = getPlayer(res).get();
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(3, 1), initPlayer.isInteractable());
        assertEquals(actualPlayer, expectedPlayer);
        Position assPos = getEntities(res, "assassin").get(0).getPosition();
        int playerX = actualPlayer.getPosition().getX();
        int playerY = actualPlayer.getPosition().getY();
        assertEquals(playerX + 1, assPos.getX());
        assertEquals(playerY, assPos.getY());

        // Interact with assassin to mind control 
        EntityResponse actualAssassin = getEntities(res, "assassin").get(0);
        String assassinId = actualAssassin.getId();
        res = assertDoesNotThrow(() -> dmc.interact(assassinId));
        actualAssassin = getEntities(res, "assassin").get(0);

        Boolean interactable = actualAssassin.isInteractable();
        assertEquals(false, interactable);

        // Assassin follows player
        res = dmc.tick(Direction.LEFT);
        expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(2, 1), initPlayer.isInteractable());
        actualPlayer = getPlayer(res).get();
        assertEquals(expectedPlayer, actualPlayer);

        EntityResponse expectedAssassin = new EntityResponse(initAssassin.getId(), initAssassin.getType(), new Position(3, 1), false);
        actualAssassin = getEntities(res, "assassin").get(0);
        assertEquals(expectedAssassin, actualAssassin);

        res = dmc.tick(Direction.DOWN);
        expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(2, 2), initPlayer.isInteractable());
        actualPlayer = getPlayer(res).get();
        assertEquals(expectedPlayer, actualPlayer);

        expectedAssassin = new EntityResponse(initAssassin.getId(), initAssassin.getType(), new Position(2, 1), false);
        actualAssassin = getEntities(res, "assassin").get(0);
        assertEquals(expectedAssassin, actualAssassin);

        // Player try to move to assassin, but they just swap positions
        res = dmc.tick(Direction.UP);
        expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(2, 1), initPlayer.isInteractable());
        actualPlayer = getPlayer(res).get();
        assertEquals(expectedPlayer, actualPlayer);

        expectedAssassin = new EntityResponse(initAssassin.getId(), initAssassin.getType(), new Position(2, 2), false);
        actualAssassin = getEntities(res, "assassin").get(0);
        assertEquals(expectedAssassin, actualAssassin);

        // Mind control effects worn off from assassin
        res = dmc.tick(Direction.UP);
        actualAssassin = getEntities(res, "assassin").get(0);
        interactable = actualAssassin.isInteractable();
        assertEquals(true, interactable);
    }

}
