package dungeonmania.interact_testing;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import org.junit.jupiter.api.Test;

import java.util.List;

import static dungeonmania.TestUtils.getEntities;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class bribe_mercenary_test {
    @Test
    public void successful_bribe_test() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_bribe_test", "c_bribe_test");

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        List<EntityResponse> mercenary = getEntities(res, "mercenary");
        assert mercenary.size() == 1;
        String mercenaryid = mercenary.get(0).getId();
        res = assertDoesNotThrow(() -> dmc.interact(mercenaryid));
    }

    @Test
    public void no_treasure_test() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_bribe_test", "c_bribe_test");

        res = dmc.tick(Direction.DOWN);
        List<EntityResponse> mercenary = getEntities(res, "mercenary");
        assert mercenary.size() == 1;
        String mercenaryid = mercenary.get(0).getId();
        assertThrows(InvalidActionException.class, () -> dmc.interact(mercenaryid), "Not enough treasure");
    }

    @Test
    public void not_in_range_test() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_bribe_test", "c_bribe_test");

        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);

        List<EntityResponse> mercenary = getEntities(res, "mercenary");
        assert mercenary.size() == 1;
        String mercenaryid = mercenary.get(0).getId();
        assertThrows(InvalidActionException.class, () -> dmc.interact(mercenaryid), "Not in range");
    }

}
