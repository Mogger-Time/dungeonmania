package dungeonmania.MovingEntities;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import org.junit.jupiter.api.Test;

public class TestHydra {
    @Test
    public void test_regain_health() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_hydra_test", "c_assassin_test");

        res = dmc.tick(Direction.UP);
        BattleResponse battle = res.getBattles().get(0);

        assert battle.getRounds().size() == 10;
        for (int i = 0; i < 10; i++) {
            assert battle.getRounds().get(i).getDeltaEnemyHealth() == 1;
            assert battle.getRounds().get(i).getDeltaCharacterHealth() == -1;
        }
    }
}
