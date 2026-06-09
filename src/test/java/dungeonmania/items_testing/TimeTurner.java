package dungeonmania.items_testing;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static dungeonmania.TestUtils.getInventory;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeTurner {

    @Test
    @DisplayName("TimeTurner: Test that the TimeTurner can be picked up")
    public void testTimeTurnerCanBePickedUp() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_timeTurner_pickUp", "c_exitOnlyGoal");

        res = dmc.tick(Direction.DOWN);
        assertEquals(getInventory(res, "time_turner").size(), 1);
    }
}
