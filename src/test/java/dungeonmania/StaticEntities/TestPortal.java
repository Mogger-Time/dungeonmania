package dungeonmania.StaticEntities;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static dungeonmania.TestUtils.getGoals;
import static dungeonmania.TestUtils.getPlayer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestPortal {

    @Test
    @DisplayName("Test player goes through portal")
    public void testPlayerThroughPortal() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_portalTest_basic", "c_portalTest_basic");
        EntityResponse initPlayer = getPlayer(res).get();
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(6, 4), false);

        DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);
        actualDungonRes = dmc.tick(Direction.RIGHT);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();

        assertEquals(expectedPlayer, actualPlayer);
    }

    // @Test
    // @DisplayName("Test mercenary goes through portal")
    // public void testMercenaryThroughPortal() {
    //     DungeonManiaController dmc = new DungeonManiaController();
    //     DungeonResponse res = dmc.newGame("d_portalTest_basic", "c_portalTest_basic");
    //     EntityResponse initMerc = getEntities(res, "mercenary").get(0);
    //     EntityResponse expectedMerc = new EntityResponse(initMerc.getId(), initMerc.getType(), new Position(6, 4), true);

    //     DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);
    //     actualDungonRes = dmc.tick(Direction.RIGHT);
    //     EntityResponse actualMerc = getPlayer(actualDungonRes).get();

    //     assertEquals(expectedMerc, actualMerc);
    // }

    @Test
    @DisplayName("Test player goes through multiple portals")
    public void testplayerThroughManyPortals() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_portalTest_multiplePortals", "c_portalTest_basic");

        EntityResponse initPlayer = getPlayer(res).get();
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(3, 4), false);

        DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);
        actualDungonRes = dmc.tick(Direction.RIGHT);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();

        assertEquals(expectedPlayer, actualPlayer);
    }

    @Test
    @DisplayName("Test portal blocked by one wall")
    public void testPortalBlockedByOneWall() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_portalTest_oneWall", "c_portalTest_basic");

        EntityResponse initPlayer = getPlayer(res).get();
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(2, 1), false);

        DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);
        actualDungonRes = dmc.tick(Direction.RIGHT);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();

        assertEquals(expectedPlayer, actualPlayer);
    }

    @Test
    @DisplayName("Test portal surrounded by four walls")
    public void testPortalSurroundedByFourWalls() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_portalTest_fourWalls", "c_portalTest_basic");

        EntityResponse initPlayer = getPlayer(res).get();
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(2, 1), false);

        DungeonResponse actualDungeonRes = dmc.tick(Direction.RIGHT);
        actualDungeonRes = dmc.tick(Direction.RIGHT);
        EntityResponse actualPlayer = getPlayer(actualDungeonRes).get();

        assertEquals(expectedPlayer, actualPlayer);
    }

    @Test
    @DisplayName("Test player goes through portal and exits dungeon")
    public void testPlayerThroughPortalToExit() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_portalTest_portalToExit", "c_portalTest_basic");

        DungeonResponse actualDungeonRes = dmc.tick(Direction.RIGHT);
        EntityResponse initPlayer = getPlayer(res).get();
        actualDungeonRes = dmc.tick(Direction.RIGHT);
        EntityResponse actualPlayer = getPlayer(actualDungeonRes).get();
        assertNotEquals(initPlayer, actualPlayer);
        String goal = getGoals(actualDungeonRes);
        assertEquals("", goal);
    }

}
