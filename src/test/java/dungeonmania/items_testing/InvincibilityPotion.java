package dungeonmania.items_testing;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static dungeonmania.TestUtils.getEntities;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class InvincibilityPotion {
    @Test
    @DisplayName("Test the player can consume an invincibility potion")
    public void testconsumeinvpotion() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_invcpotionTest_basic", "c_invcpotionTest_basic");

        DungeonResponse newPos = dmc.tick(Direction.RIGHT);
        List<ItemResponse> inventory = newPos.getInventory();
        assertEquals(inventory.size(), 1);
        assertEquals(inventory.get(0).getType(), "invincibility_potion");

        assertDoesNotThrow(() -> dmc.tick(inventory.get(0).getId()));
    }

    @Test
    @DisplayName("Test battle instantly won")
    public void testinvbattle() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_spidervplayerinvincible", "c_spidervplayer");

        DungeonResponse newPos = dmc.tick(Direction.LEFT);
        List<ItemResponse> inventory = newPos.getInventory();
        assertDoesNotThrow(() -> dmc.tick(inventory.get(0).getId()));
        DungeonResponse afterbattle = dmc.getDungeonResponseModel();
        BattleResponse battle = afterbattle.getBattles().get(0);
        List<RoundResponse> rounds = battle.getRounds();
        assertEquals(rounds.size(), 1);
        //assertEquals(rounds.get(0).getDeltaEnemyHealth(), -5);
        assertEquals(rounds.get(0).getDeltaCharacterHealth(), -0.0);
        double finalehealth = battle.getInitialEnemyHealth();
        finalehealth = finalehealth + rounds.get(0).getDeltaEnemyHealth();
        assert finalehealth <= 0;
    }

    @Test
    @DisplayName("Test mercenaries run away")
    public void testmercenaryrun() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_mercenarychase", "c_mercenarychase");

        DungeonResponse newPos = dmc.tick(Direction.RIGHT);
        List<ItemResponse> inventory = newPos.getInventory();
        assertDoesNotThrow(() -> dmc.tick(inventory.get(0).getId()));

        newPos = dmc.tick(Direction.RIGHT);
        newPos = dmc.tick(Direction.RIGHT);
        newPos = dmc.tick(Direction.RIGHT);
        newPos = dmc.tick(Direction.RIGHT);

        DungeonResponse afterbattle = dmc.getDungeonResponseModel();
        EntityResponse mercenary = getEntities(afterbattle, "mercenary").get(0);
        Position mercloc = mercenary.getPosition();
        assert mercloc.getX() > 10;
    }
}
