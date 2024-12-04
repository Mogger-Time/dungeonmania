package dungeonmania.battle_testing;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class battle_test_multiple {
    @Test
    public void test_simple_won() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("d_battle_test_multiple", "c_battleTests_basicMercenaryMercenaryDies");
        response = controller.tick(Direction.LEFT);
        response = controller.tick(Direction.LEFT);
        response = controller.tick(Direction.LEFT);
        response = controller.tick(Direction.LEFT);
        response = assertDoesNotThrow(() -> controller.build("shield"));

        response = controller.tick(Direction.LEFT);
        response = controller.tick(Direction.LEFT);
        response = controller.tick(Direction.LEFT);
        response = controller.tick(Direction.LEFT);
        response = assertDoesNotThrow(() -> controller.build("bow"));
        response = controller.tick(Direction.LEFT);
        BattleResponse battle = response.getBattles().get(0);
        RoundResponse firstround = battle.getRounds().get(0);
        assertEquals(1, battle.getRounds().size());
        assertEquals(10, battle.getInitialPlayerHealth());
        assertEquals(5, battle.getInitialEnemyHealth());
        assertEquals(-5.2, firstround.getDeltaEnemyHealth());
        assertEquals(-0.2, firstround.getDeltaCharacterHealth());
        assertEquals(3, firstround.getWeaponryUsed().size());
    }
}
