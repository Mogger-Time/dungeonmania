package dungeonmania.items_testing;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static dungeonmania.TestUtils.getEntities;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class InvisibilityPotion {
    @Test
    @DisplayName("Test the player can consume an invisibility potion")
    public void testconsumeinvpotion() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_invpotionTest_basic", "c_invpotionTest_basic");

        DungeonResponse newPos = dmc.tick(Direction.RIGHT);
        List<ItemResponse> inventory = newPos.getInventory();
        assertEquals(inventory.size(), 1);
        assertEquals(inventory.get(0).getType(), "invisibility_potion");

        assertDoesNotThrow(() -> dmc.tick(inventory.get(0).getId()));
    }

    @Test
    @DisplayName("Test battle does not occur")
    public void testinvbattle() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_spidervplayer", "c_spidervplayer");

        DungeonResponse newPos = dmc.tick(Direction.LEFT);
        List<ItemResponse> inventory = newPos.getInventory();
        assertDoesNotThrow(() -> dmc.tick(inventory.get(0).getId()));
        DungeonResponse afterbattle = dmc.getDungeonResponseModel();
        assertEquals(afterbattle.getBattles().size(), 0);
    }

    @Test
    @DisplayName("Test mercenaries wander")
    public void testmercenarybehavior() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_mercenarychase", "c_mercenarychase");

        DungeonResponse newPos = dmc.tick(Direction.LEFT);
        List<ItemResponse> inventory = newPos.getInventory();
        assertDoesNotThrow(() -> dmc.tick(inventory.get(0).getId()));
        newPos = dmc.tick(Direction.LEFT);
        newPos = dmc.tick(Direction.LEFT);
        newPos = dmc.tick(Direction.LEFT);
        DungeonResponse afterbattle = dmc.getDungeonResponseModel();
        EntityResponse mercenary = getEntities(afterbattle, "mercenary").get(0);
        Position mercloc = mercenary.getPosition();
        assert mercloc.getX() > 6;
    }
}
