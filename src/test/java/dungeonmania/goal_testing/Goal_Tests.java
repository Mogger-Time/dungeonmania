package dungeonmania.goal_testing;
import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static dungeonmania.TestUtils.getGoals;

import org.junit.jupiter.api.Test;
public class Goal_Tests {
    @Test
    public void test_simple_exit() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse response = dmc.newGame("d_exitOnlyGoal", "c_battle_test_long");
        assertEquals(getGoals(response), ":exit");

        response = dmc.tick(Direction.DOWN);
        response = dmc.tick(Direction.DOWN);
        assertEquals(getGoals(response), "");
    }

    @Test
    public void test_treasure() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse response = dmc.newGame("d_treasure_goal", "c_treasure_goal");
        assertEquals(getGoals(response), ":treasure");
        response = dmc.tick(Direction.DOWN);
        assertEquals(getGoals(response), ":treasure");
        response = dmc.tick(Direction.DOWN);
        assertEquals(getGoals(response), "");
    }

    @Test
    public void test_and() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse response = dmc.newGame("d_treasure_exit_goal", "c_treasure_goal");
        assertEquals(getGoals(response), "(:treasure AND :exit)");
        response = dmc.tick(Direction.DOWN);
        assertEquals(getGoals(response), "(:treasure AND :exit)");
        response = dmc.tick(Direction.DOWN);
        assertEquals(getGoals(response), ":exit");
        response = dmc.tick(Direction.DOWN);
        assertEquals(getGoals(response), "");
    }

    @Test
    public void test_or() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse response = dmc.newGame("d_treasure_or_exit_goal", "c_treasure_goal");
        assertEquals(getGoals(response), "(:treasure OR :exit)");
        response = dmc.tick(Direction.DOWN);
        assertEquals(getGoals(response), "(:treasure OR :exit)");
        response = dmc.tick(Direction.DOWN);
        assertEquals(getGoals(response), "");
        response = dmc.tick(Direction.DOWN);
        assertEquals(getGoals(response), "");
    }

    @Test
    public void test_enemy() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse response = dmc.newGame("d_enemies_goal", "c_enemies_goal");
        assertEquals(getGoals(response), ":enemies");
        response = dmc.tick(Direction.DOWN);
        assertEquals(getGoals(response), "");
    }

    @Test
    public void test_boulders() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse response = dmc.newGame("d_boulder_goal", "c_treasure_goal");
        assertEquals(getGoals(response), ":boulders");
        response = dmc.tick(Direction.DOWN);
        assertEquals(getGoals(response), "");
    }
}
