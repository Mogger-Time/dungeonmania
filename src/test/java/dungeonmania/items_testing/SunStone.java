package dungeonmania.items_testing;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static dungeonmania.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

public class SunStone {
    @Test
    @DisplayName("Test Sun Stone can be used to open a door, and is retained after use")
    public void testSunStoneOpenDoor() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_testSunStoneOpenDoors", "c_sunstoneTest_twoTreasureGoal");
        EntityResponse initPlayer = getPlayer(res).get();
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 3), initPlayer.isInteractable());

        // pick up sun stone
        res = dmc.tick(Direction.DOWN);
        assertEquals(getInventory(res, "sun_stone").size(), 1);

        // check that sun stone is retained in the inventory after use
        // player walks through the door
        res = dmc.tick(Direction.DOWN);
        assertEquals(getInventory(res, "sun_stone").size(), 1);
        EntityResponse actualPlayer = getPlayer(res).get();
        assertEquals(expectedPlayer, actualPlayer);
    }

    @Test
    @DisplayName("Test Sun Stone can be used to open multiple doors, and is retained after use")
    public void testSunStoneOpenMultipleDoors() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_testSunStoneOpenDoors", "c_sunstoneTest_twoTreasureGoal");
        EntityResponse initPlayer = getPlayer(res).get();
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 3), initPlayer.isInteractable());

        // pick up sun stone
        res = dmc.tick(Direction.DOWN);
        assertEquals(getInventory(res, "sun_stone").size(), 1);

        // check that sun stone is retained in the inventory after use
        // player walks through the door
        res = dmc.tick(Direction.DOWN);
        assertEquals(getInventory(res, "sun_stone").size(), 1);
        EntityResponse actualPlayer = getPlayer(res).get();
        assertEquals(expectedPlayer, actualPlayer);

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        // check that sun stone is retained in the inventory after use
        // player walks through the door
        assertEquals(getInventory(res, "sun_stone").size(), 1);
        expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(3, 3), initPlayer.isInteractable());
        actualPlayer = getPlayer(res).get();
        assertEquals(expectedPlayer, actualPlayer);
    }

    @Test
    @DisplayName("Test Sun Stone can be used interchangeably with treasure or key to craft a shield, and is retained after use")
    public void testSunStoneCraftShield() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_testSunStoneCraftItems", "c_sunstoneTest_twoTreasureGoal");

        // shield = 2 wood + (1 treasure / 1 key)
        res = dmc.tick(Direction.RIGHT); // pick up 1 sun_stone
        assertEquals(getInventory(res, "sun_stone").size(), 1);
        res = dmc.tick(Direction.RIGHT); // pick up 1 wood
        assertEquals(getInventory(res, "wood").size(), 1);
        res = dmc.tick(Direction.UP); // pick up 1 wood
        assertEquals(getInventory(res, "wood").size(), 2);

        res = assertDoesNotThrow(() -> dmc.build("shield"));
        assertEquals(getInventory(res, "shield").size(), 1);
        assertEquals(getInventory(res, "wood").size(), 0);
        assertEquals(getInventory(res, "sun_stone").size(), 1);
    }

    @Test
    @DisplayName("Test Sun Stone can be used interchangeably with treasure or key and wood to craft a sceptre, and is retained after use")
    public void testSunStoneWoodCraftSceptre() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_testSunStoneCraftItems", "c_sunstoneTest_twoTreasureGoal");

        // sceptre = 1 wood + (1 key / 1 treasure) + 1 sun stone
        res = dmc.tick(Direction.RIGHT); // pick up 1 sun_stone
        assertEquals(getInventory(res, "sun_stone").size(), 1);
        res = dmc.tick(Direction.RIGHT); // pick up 1 wood
        assertEquals(getInventory(res, "wood").size(), 1);
        res = dmc.tick(Direction.RIGHT); // pick up 1 sun_stone
        assertEquals(getInventory(res, "sun_stone").size(), 2);

        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(getInventory(res, "sceptre").size(), 1);
        assertEquals(getInventory(res, "wood").size(), 0);
        // the sunstone which didn't replace 1 key / 1 treasure is consumed, leaving 1 retained sunstone in the inventory
        assertEquals(getInventory(res, "sun_stone").size(), 1);
    }

    @Test
    @DisplayName("Test Sun Stone can be used interchangeably with treasure or key and arrows to craft a sceptre, and is retained after use")
    public void testSunStoneArrowsCraftSceptre() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_testSunStoneCraftItems", "c_sunstoneTest_twoTreasureGoal");

        // sceptre = 2 arrows + (1 key / 1 treasure) + 1 sun stone
        res = dmc.tick(Direction.RIGHT); // pick up 1 sun_stone
        assertEquals(getInventory(res, "sun_stone").size(), 1);
        res = dmc.tick(Direction.UP); // pick up 1 arrow
        assertEquals(getInventory(res, "arrow").size(), 1);
        res = dmc.tick(Direction.UP); // pick up 1 arrow
        assertEquals(getInventory(res, "arrow").size(), 2);
        res = dmc.tick(Direction.UP); // pick up 1 sun_stone
        assertEquals(getInventory(res, "sun_stone").size(), 2);

        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(getInventory(res, "sceptre").size(), 1);
        assertEquals(getInventory(res, "arrow").size(), 0);
        assertEquals(getInventory(res, "sun_stone").size(), 1);
    }

    @Test
    @DisplayName("Test Sun Stone and sword can craft midnight armour")
    public void testSunStoneCraftMidnightArmour() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_testSunStoneCraftItems", "c_sunstoneTest_twoTreasureGoal");

        // midnight armour = 1 sword + 1 sun stone
        res = dmc.tick(Direction.RIGHT); // pick up 1 sun_stone
        assertEquals(getInventory(res, "sun_stone").size(), 1);
        res = dmc.tick(Direction.DOWN); // pick up 1 sword
        assertEquals(getInventory(res, "sword").size(), 1);

        res = assertDoesNotThrow(() -> dmc.build("midnight_armour"));
        assertEquals(getInventory(res, "midnight_armour").size(), 1);
        assertEquals(getInventory(res, "sword").size(), 0);
        assertEquals(getInventory(res, "sun_stone").size(), 0);
    }

    @Test
    @DisplayName("Test Sun Stone cannot be used to bribe mercenaries")
    public void testMercenaryNotBribedWithSunStone() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_testMercenaryNotBribedWithSunStone", "c_sunstoneTest_twoTreasureGoal");

        List<EntityResponse> mercenary = getEntities(res, "mercenary");
        assertEquals(1, mercenary.size());
        String mercenaryId = mercenary.get(0).getId();

        res = dmc.tick(Direction.RIGHT); // pick up 1 sun_stone
        assertEquals(getInventory(res, "sun_stone").size(), 1);
        res = dmc.tick(Direction.RIGHT); // pick up 1 sun_stone
        assertEquals(getInventory(res, "sun_stone").size(), 2);

        assertEquals(getInventory(res, "treasure").size(), 0);

        assertThrows(InvalidActionException.class, () -> dmc.interact(mercenaryId), "Not enough treasure");
    }

    @Test
    @DisplayName("Test Sun Stone counts towards the treasure goal")
    public void testSunStoneTreasureGoal() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_testSunStoneTreasureGoal", "c_sunstoneTest_twoTreasureGoal");

        // player picks up sun stones
        assertEquals(getGoals(res), ":treasure");
        res = dmc.tick(Direction.DOWN); // pick up 1 sun_stone
        assertEquals(getInventory(res, "sun_stone").size(), 1);
        assertEquals(getGoals(res), ":treasure");
        res = dmc.tick(Direction.DOWN); // pick up 1 sun_stone
        assertEquals(getInventory(res, "sun_stone").size(), 2);
        assertEquals(getGoals(res), "");
    }

    @Test
    @DisplayName("Test Sun Stone counts towards the treasure and exit goal")
    public void testSunStoneTreasureExitGoal() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_testSunStoneTreasureExitGoals", "c_sunstoneTest_threeTreasureGoal");

        // player picks up sun stones and walks towards the exit
        assertEquals(getGoals(res), "(:treasure AND :exit)");
        res = dmc.tick(Direction.DOWN); // pick up 1 sun_stone
        assertEquals(getInventory(res, "sun_stone").size(), 1);
        assertEquals(getGoals(res), "(:treasure AND :exit)");
        res = dmc.tick(Direction.DOWN); // pick up 1 sun_stone
        assertEquals(getInventory(res, "sun_stone").size(), 2);
        assertEquals(getGoals(res), "(:treasure AND :exit)");
        res = dmc.tick(Direction.DOWN); // pick up 1 sun_stone        
        assertEquals(getInventory(res, "sun_stone").size(), 3);
        assertEquals(getGoals(res), ":exit");
        res = dmc.tick(Direction.DOWN);
        assertEquals(getGoals(res), "");
    }

    @Test
    @DisplayName("Test Sun Stone can be used to open multiple doors, craft a sceptre, and is retained after use")
    public void testSunStoneOpenMultipleDoorsWoodCraftSceptre() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_testSunStoneCraftItems", "c_sunstoneTest_twoTreasureGoal");

        // sceptre = 1 wood + (1 key / 1 treasure) + 1 sun stone
        res = dmc.tick(Direction.RIGHT); // pick up 1 sun_stone
        assertEquals(getInventory(res, "sun_stone").size(), 1);
        res = dmc.tick(Direction.RIGHT); // pick up 1 wood
        assertEquals(getInventory(res, "wood").size(), 1);
        res = dmc.tick(Direction.RIGHT); // pick up 1 sun_stone
        assertEquals(getInventory(res, "sun_stone").size(), 2);

        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(getInventory(res, "sceptre").size(), 1);
        assertEquals(getInventory(res, "wood").size(), 0);
        // the sunstone which didn't replace 1 key / 1 treasure is consumed, leaving 1 retained sunstone in the inventory
        assertEquals(getInventory(res, "sun_stone").size(), 1);

        EntityResponse initPlayer = getPlayer(res).get();
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(5, 4), initPlayer.isInteractable());

        // check that sun stone is retained in the inventory after use
        // player walks through the door
        res = dmc.tick(Direction.RIGHT);
        assertEquals(getInventory(res, "sun_stone").size(), 1);
        EntityResponse actualPlayer = getPlayer(res).get();
        assertEquals(expectedPlayer, actualPlayer);

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);

        // check that sun stone is retained in the inventory after use
        // player walks through the door
        assertEquals(getInventory(res, "sun_stone").size(), 1);
        expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(6, 5), initPlayer.isInteractable());
        actualPlayer = getPlayer(res).get();
        assertEquals(expectedPlayer, actualPlayer);
    }

}
