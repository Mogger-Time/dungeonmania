package dungeonmania.battle_testing;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
public class battle_test_shield {
    @Test
    public void test_simple_won() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("d_battle_test_shield", "c_battleTests_basicMercenaryMercenaryDies");
        response = controller.tick(Direction.LEFT);
        response = controller.tick(Direction.LEFT);
        response = controller.tick(Direction.LEFT);
        response = assertDoesNotThrow(() -> controller.build("shield"));        
        response = controller.tick(Direction.LEFT);
        BattleResponse battle = response.getBattles().get(0);
        RoundResponse firstround = battle.getRounds().get(0);
        assertEquals(3, battle.getRounds().size());
        assertEquals(10, battle.getInitialPlayerHealth());
        assertEquals(5, battle.getInitialEnemyHealth());
        assertEquals(-2, firstround.getDeltaEnemyHealth());
        assertEquals(-0.2, firstround.getDeltaCharacterHealth());
        assertEquals(1, firstround.getWeaponryUsed().size());
        double finalehealth = battle.getInitialEnemyHealth();
        double finalphealth = battle.getInitialPlayerHealth();
        for (RoundResponse round : battle.getRounds()) {
            finalehealth = finalehealth + round.getDeltaEnemyHealth();
            finalphealth = finalphealth + round.getDeltaCharacterHealth();
        }
        assert finalehealth <= 0;
        assert finalphealth > 9.39 && finalphealth < 9.41;

        response = controller.tick(Direction.LEFT);
        BattleResponse battle2 = response.getBattles().get(1);
        RoundResponse firstround2 = battle2.getRounds().get(0);
        RoundResponse secondround2 = battle2.getRounds().get(1);
        assertEquals(3, battle2.getRounds().size());
        assertEquals(5, battle2.getInitialEnemyHealth());
        assertEquals(-2, firstround2.getDeltaEnemyHealth());
        assertEquals(-0.5, firstround2.getDeltaCharacterHealth());
        assertEquals(0, firstround2.getWeaponryUsed().size());
        assertEquals(-2, secondround2.getDeltaEnemyHealth());
        assertEquals(-0.5, secondround2.getDeltaCharacterHealth());
        assertEquals(0, secondround2.getWeaponryUsed().size());
        finalehealth = battle2.getInitialEnemyHealth();
        finalphealth = battle2.getInitialPlayerHealth();
        for (RoundResponse round : battle2.getRounds()) {
            finalehealth = finalehealth + round.getDeltaEnemyHealth();
            finalphealth = finalphealth + round.getDeltaCharacterHealth();
        }
        assert finalehealth <= 0;
        assert finalphealth >= 7.9;
    }

    @Test
    public void test_one_shield() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("d_battle_test_shield", "c_battle_test_long");
        response = controller.tick(Direction.LEFT);
        response = controller.tick(Direction.LEFT);
        response = controller.tick(Direction.LEFT);
        response = assertDoesNotThrow(() -> controller.build("shield"));
        response = controller.tick(Direction.LEFT);
        BattleResponse battle = response.getBattles().get(0);
        RoundResponse firstround = battle.getRounds().get(0);
        RoundResponse secondround = battle.getRounds().get(1);
        assertEquals(3, battle.getRounds().size());
        assertEquals(10, battle.getInitialPlayerHealth());
        assertEquals(5, battle.getInitialEnemyHealth());
        assertEquals(-2, firstround.getDeltaEnemyHealth());
        assertEquals(-0.2, firstround.getDeltaCharacterHealth());
        assertEquals(1, firstround.getWeaponryUsed().size());
        assertEquals(-2, secondround.getDeltaEnemyHealth());
        assertEquals(-0.2, secondround.getDeltaCharacterHealth());
        assertEquals(1, secondround.getWeaponryUsed().size());
        double finalehealth = battle.getInitialEnemyHealth();
        double finalphealth = battle.getInitialPlayerHealth();
        for (RoundResponse round : battle.getRounds()) {
            finalehealth = finalehealth + round.getDeltaEnemyHealth();
            finalphealth = finalphealth + round.getDeltaCharacterHealth();
        }
        assert finalehealth <= 0;
        assert finalphealth >= 9.4;

        response = controller.tick(Direction.LEFT);
        BattleResponse battle2 = response.getBattles().get(1);
        RoundResponse firstround2 = battle2.getRounds().get(0);
        RoundResponse secondround2 = battle2.getRounds().get(1);
        assertEquals(3, battle2.getRounds().size());
        assertEquals(5, battle2.getInitialEnemyHealth());
        assertEquals(-2, firstround2.getDeltaEnemyHealth());
        assertEquals(-0.2, firstround2.getDeltaCharacterHealth());
        assertEquals(1, firstround2.getWeaponryUsed().size());
        assertEquals(-2, secondround2.getDeltaEnemyHealth());
        assertEquals(-0.2, secondround2.getDeltaCharacterHealth());
        assertEquals(1, secondround2.getWeaponryUsed().size());
        finalehealth = battle2.getInitialEnemyHealth();
        finalphealth = battle2.getInitialPlayerHealth();
        for (RoundResponse round : battle2.getRounds()) {
            finalehealth = finalehealth + round.getDeltaEnemyHealth();
            finalphealth = finalphealth + round.getDeltaCharacterHealth();
        }
        assert finalehealth <= 0;
        assert finalphealth >= 8.8;
    }
}
