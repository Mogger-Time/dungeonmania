package dungeonmania.battle_testing;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
public class battle_test_static {
    @Test
    public void simple_battle() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("d_battleTest_basicSpider", "c_battleTests_basicMercenaryMercenaryDies");
        response = controller.tick(Direction.DOWN);
        BattleResponse battle = response.getBattles().get(0);
        RoundResponse firstround = battle.getRounds().get(0);
        assertEquals(3, battle.getRounds().size());
        assertEquals(10, battle.getInitialPlayerHealth());
        assertEquals(5, battle.getInitialEnemyHealth());
        assertEquals(-2, firstround.getDeltaEnemyHealth());
        assertEquals(-0.5, firstround.getDeltaCharacterHealth());
        assertEquals(0, firstround.getWeaponryUsed().size());
        double finalehealth = battle.getInitialEnemyHealth();
        double finalphealth = battle.getInitialPlayerHealth();
        for (RoundResponse round : battle.getRounds()) {
            finalehealth = finalehealth + round.getDeltaEnemyHealth();
            finalphealth = finalphealth + round.getDeltaCharacterHealth();
        }
        assert finalehealth <= 0;
        assert finalphealth == 8.5;
    }

    @Test
    public void test_simple_lost() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("d_battleTest_basicSpider", "c_battleTests_basicMercenaryPlayerDies");
        response = controller.tick(Direction.DOWN);
        BattleResponse battle = response.getBattles().get(0);
        RoundResponse firstround = battle.getRounds().get(0);
        assertEquals(3, battle.getRounds().size());
        assertEquals(5, battle.getInitialPlayerHealth());
        assertEquals(10, battle.getInitialEnemyHealth());
        assertEquals(-0.2, firstround.getDeltaEnemyHealth());
        assertEquals(-2, firstround.getDeltaCharacterHealth());
        assertEquals(0, firstround.getWeaponryUsed().size());
        double finalehealth = battle.getInitialEnemyHealth();
        double finalphealth = battle.getInitialPlayerHealth();
        for (RoundResponse round : battle.getRounds()) {
            finalehealth = finalehealth + round.getDeltaEnemyHealth();
            finalphealth = finalphealth + round.getDeltaCharacterHealth();
        }
        assert finalphealth <= 0;
        assert finalehealth >= 9.4;
    }

    @Test
    public void test_simple_tie() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("d_battleTest_basicSpider", "c_battleTests_basicMercenaryBothDie");
        response = controller.tick(Direction.DOWN);
        BattleResponse battle = response.getBattles().get(0);
        RoundResponse firstround = battle.getRounds().get(0);
        assertEquals(5, battle.getRounds().size());
        assertEquals(5, battle.getInitialPlayerHealth());
        assertEquals(10, battle.getInitialEnemyHealth());
        assertEquals(-2, firstround.getDeltaEnemyHealth());
        assertEquals(-1, firstround.getDeltaCharacterHealth());
        assertEquals(0, firstround.getWeaponryUsed().size());
        double finalehealth = battle.getInitialEnemyHealth();
        double finalphealth = battle.getInitialPlayerHealth();
        for (RoundResponse round : battle.getRounds()) {
            finalehealth = finalehealth + round.getDeltaEnemyHealth();
            finalphealth = finalphealth + round.getDeltaCharacterHealth();
        }
        assert finalphealth == 0;
        assert finalehealth == 0;
    }
}
