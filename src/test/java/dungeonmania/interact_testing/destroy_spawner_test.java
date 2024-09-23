package dungeonmania.interact_testing;
import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import dungeonmania.exceptions.InvalidActionException;

import static dungeonmania.TestUtils.getEntities;

import org.junit.jupiter.api.Test;

public class destroy_spawner_test {
    @Test
    public void destroy_spawner() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_ZTSpawnerTest_basic", "c_ZTSpawnerTest_noZombies");

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        List<EntityResponse> spawner = getEntities(res, "zombie_toast_spawner");
        assert spawner.size() == 1;
        String spawnerid = spawner.get(0).getId();
        res = assertDoesNotThrow(()->dmc.interact(spawnerid));
        spawner = getEntities(res, "zombie_toast_spawner");
        assert spawner.size() == 0;
    }

    @Test
    public void destroy_spawner_nosword() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_ZTSpawnerTest_basic", "c_ZTSpawnerTest_noZombies");
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        List<EntityResponse> spawner = getEntities(res, "zombie_toast_spawner");
        assert spawner.size() == 1;
        String spawnerid = spawner.get(0).getId();
        assertThrows(InvalidActionException.class, ()->dmc.interact(spawnerid), "No weapon");
    }

    @Test
    public void destroy_spawner_far() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_ZTSpawnerTest_basic", "c_ZTSpawnerTest_noZombies");
        res = dmc.tick(Direction.RIGHT);

        List<EntityResponse> spawner = getEntities(res, "zombie_toast_spawner");
        assert spawner.size() == 1;
        String spawnerid = spawner.get(0).getId();
        assertThrows(InvalidActionException.class, ()->dmc.interact(spawnerid), "Not adjacent to ZTS");
    }

    @Test
    public void destroy_spawner_noid() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_ZTSpawnerTest_basic", "c_ZTSpawnerTest_noZombies");
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);

        assertThrows(IllegalArgumentException.class, ()->dmc.interact("shidma"), "No entity exists");
    }
}
