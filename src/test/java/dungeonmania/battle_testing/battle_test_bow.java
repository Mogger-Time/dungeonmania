package dungeonmania.battle_testing;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
public class battle_test_bow {
    @Test
    public void test_simple_won() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("d_battle_test_bow", "c_battleTests_basicMercenaryMercenaryDies");
        response = controller.tick(Direction.UP);
        response = controller.tick(Direction.UP);
        response = controller.tick(Direction.UP);
        response = controller.tick(Direction.UP);
        response = assertDoesNotThrow(() -> controller.build("bow"));
        response = controller.tick(Direction.UP);
        BattleResponse battle = response.getBattles().get(0);
        RoundResponse firstround = battle.getRounds().get(0);
        RoundResponse secondround = battle.getRounds().get(1);
        assertEquals(2, battle.getRounds().size());
        assertEquals(10, battle.getInitialPlayerHealth());
        assertEquals(5, battle.getInitialEnemyHealth());
        assertEquals(-4, firstround.getDeltaEnemyHealth());
        assertEquals(-0.5, firstround.getDeltaCharacterHealth());
        assertEquals(1, firstround.getWeaponryUsed().size());
        assertEquals(-4, secondround.getDeltaEnemyHealth());
        assertEquals(-0.5, secondround.getDeltaCharacterHealth());
        assertEquals(1, secondround.getWeaponryUsed().size());
        double finalehealth = battle.getInitialEnemyHealth();
        double finalphealth = battle.getInitialPlayerHealth();
        for (RoundResponse round : battle.getRounds()) {
            finalehealth = finalehealth + round.getDeltaEnemyHealth();
            finalphealth = finalphealth + round.getDeltaCharacterHealth();
        }
        assert finalehealth <= 0;
        assert finalphealth == 9;

        response = controller.tick(Direction.UP);
        battle = response.getBattles().get(1);
        firstround = battle.getRounds().get(0);
        secondround = battle.getRounds().get(1);
        assertEquals(3, battle.getRounds().size());
        assertEquals(9, battle.getInitialPlayerHealth());
        assertEquals(5, battle.getInitialEnemyHealth());
        assertEquals(-2, firstround.getDeltaEnemyHealth());
        assertEquals(-0.5, firstround.getDeltaCharacterHealth());
        assertEquals(0, firstround.getWeaponryUsed().size());
        assertEquals(-2, secondround.getDeltaEnemyHealth());
        assertEquals(-0.5, secondround.getDeltaCharacterHealth());
        assertEquals(0, secondround.getWeaponryUsed().size());
        finalehealth = battle.getInitialEnemyHealth();
        finalphealth = battle.getInitialPlayerHealth();
        for (RoundResponse round : battle.getRounds()) {
            finalehealth = finalehealth + round.getDeltaEnemyHealth();
            finalphealth = finalphealth + round.getDeltaCharacterHealth();
        }
        assert finalehealth <= 0;
        assert finalphealth == 7.5;
    }

    @Test
    public void test_one_bow() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("d_battle_test_bow", "c_battle_test_long");
        response = controller.tick(Direction.UP);
        response = controller.tick(Direction.UP);
        response = controller.tick(Direction.UP);
        response = controller.tick(Direction.UP);
        response = assertDoesNotThrow(() -> controller.build("bow"));
        response = controller.tick(Direction.UP);
        BattleResponse battle = response.getBattles().get(0);
        RoundResponse firstround = battle.getRounds().get(0);
        RoundResponse secondround = battle.getRounds().get(1);
        assertEquals(2, battle.getRounds().size());
        assertEquals(10, battle.getInitialPlayerHealth());
        assertEquals(5, battle.getInitialEnemyHealth());
        assertEquals(-4, firstround.getDeltaEnemyHealth());
        assertEquals(-0.5, firstround.getDeltaCharacterHealth());
        assertEquals(1, firstround.getWeaponryUsed().size());
        assertEquals(-4, secondround.getDeltaEnemyHealth());
        assertEquals(-0.5, secondround.getDeltaCharacterHealth());
        assertEquals(1, secondround.getWeaponryUsed().size());
        double finalehealth = battle.getInitialEnemyHealth();
        double finalphealth = battle.getInitialPlayerHealth();
        for (RoundResponse round : battle.getRounds()) {
            finalehealth = finalehealth + round.getDeltaEnemyHealth();
            finalphealth = finalphealth + round.getDeltaCharacterHealth();
        }
        assert finalehealth <= 0;
        assert finalphealth == 9;
    }
}
