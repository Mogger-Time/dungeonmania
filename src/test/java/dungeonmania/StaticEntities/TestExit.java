package dungeonmania.StaticEntities;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static dungeonmania.TestUtils.getGoals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;

public class TestExit {
    @Test
    @DisplayName("Test the player completes the puzzle upon reaching the exit")
    public void testReachExit() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_exitOnlyGoal", "c_exitOnlyGoal");

        // create the expected result
        assertTrue(getGoals(initDungonRes).contains(":exit"));

        // move player downward
        initDungonRes = dmc.tick(Direction.DOWN);
        initDungonRes = dmc.tick(Direction.DOWN);
     
        // assert after movement reached exit
        assertFalse(getGoals(initDungonRes).contains(":exit"));;
    }
    
}