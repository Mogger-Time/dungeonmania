package dungeonmania.items_testing;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static dungeonmania.TestUtils.getInventory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;



public class BattleItem {
    //just testing that shield and bow can be crafted
    @Test
    @DisplayName("Can craft shield")
    public void testshield() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_craftitems_test", "c_mercenarychase");
        initDungonRes = dmc.tick(Direction.RIGHT);
        initDungonRes = dmc.tick(Direction.RIGHT);
        initDungonRes = dmc.tick(Direction.RIGHT);
        initDungonRes = assertDoesNotThrow(() -> dmc.build("shield"));
        assertEquals(getInventory(initDungonRes, "shield").size(), 1);
        assertEquals(getInventory(initDungonRes, "key").size(), 0);
        assertEquals(getInventory(initDungonRes, "wood").size(), 0);
    }

    @Test
    @DisplayName("Can craft shield with treasure")
    public void testtreasureshield() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_craftitems_test", "c_mercenarychase");
        initDungonRes = dmc.tick(Direction.RIGHT);
        initDungonRes = dmc.tick(Direction.RIGHT);
        initDungonRes = dmc.tick(Direction.DOWN);
        initDungonRes = assertDoesNotThrow(() -> dmc.build("shield"));
        assertEquals(getInventory(initDungonRes, "shield").size(), 1);
        assertEquals(getInventory(initDungonRes, "treasure").size(), 0);
        assertEquals(getInventory(initDungonRes, "wood").size(), 0);
    }

    @Test
    @DisplayName("Can craft bow")
    public void testbow() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_craftitems_test", "c_mercenarychase");
        initDungonRes = dmc.tick(Direction.LEFT);
        initDungonRes = dmc.tick(Direction.LEFT);
        initDungonRes = dmc.tick(Direction.LEFT);
        initDungonRes = dmc.tick(Direction.LEFT);
        initDungonRes = assertDoesNotThrow(() -> dmc.build("bow"));
        assertEquals(getInventory(initDungonRes, "bow").size(), 1);
        assertEquals(getInventory(initDungonRes, "arrow").size(), 0);
        assertEquals(getInventory(initDungonRes, "wood").size(), 0);
    }
}
