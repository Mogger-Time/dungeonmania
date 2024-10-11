package dungeonmania.items_testing;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;
import static org.junit.jupiter.api.Assertions.*;

public class TestMidnightArmour {
    @Test
    @DisplayName("Test Midnight Armour can be crafted with 1 sword and 1 sun stone if there are no zombies currently in the dungeon")
    public void testCraftMidnightArmourNoZombies() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_testMidnightArmourNoZombies", "c_testMidnightArmour");

        res = dmc.tick(Direction.RIGHT);
        assertEquals(getInventory(res, "sword").size(), 1);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(getInventory(res, "sun_stone").size(), 1);

        int zombies = getEntities(res, "zombie_toast").size();
        assert zombies == 0;

        res = assertDoesNotThrow(() -> dmc.build("midnight_armour"));
        assertEquals(getInventory(res, "midnight_armour").size(), 1);

        assertEquals(getInventory(res, "sword").size(), 0);
        assertEquals(getInventory(res, "sun_stone").size(), 0);
    }

    @Test
    @DisplayName("Test Midnight Armour cannot be crafted with 1 sword and 1 sun stone if there are zombies currently in the dungeon")
    public void testCraftMidnightArmourZombies() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_testMidnightArmourZombies", "c_testMidnightArmour");

        res = dmc.tick(Direction.RIGHT);
        assertEquals(getInventory(res, "sword").size(), 1);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(getInventory(res, "sun_stone").size(), 1);

        int zombies = getEntities(res, "zombie_toast").size();
        assert zombies > 0;

        assertThrows(InvalidActionException.class, () -> dmc.build("midnight_armour"));
        assertEquals(getInventory(res, "midnight_armour").size(), 0);
    }
}
