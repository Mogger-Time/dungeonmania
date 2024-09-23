package dungeonmania.MovingEntities;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static dungeonmania.TestUtils.getEntities;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class testMovement {
    @Test
    public void test_basic() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenarytest_verybasic", "c_mercenaryTest");

        res = dmc.tick(Direction.DOWN);
        Position mercenaryPos = getEntities(res, "mercenary").get(0).getPosition();
        assert mercenaryPos.equals(new Position(3, 4));
    }

    @Test
    public void test_maze() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_movementtest_maze", "c_mercenaryTest");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);

        Position mercenaryPos = getEntities(res, "mercenary").get(0).getPosition();
        assert mercenaryPos.equals(new Position(5, 5));
    }

    @Test
    public void test_fear() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenarytest_verybasic", "c_mercenaryTest");

        res = dmc.tick(Direction.DOWN);
        Position mercenaryPos = getEntities(res, "mercenary").get(0).getPosition();
        assert mercenaryPos.equals(new Position(3, 4));

        List<ItemResponse> inventory = res.getInventory();
        res = assertDoesNotThrow(() -> dmc.tick(inventory.get(0).getId()));

        mercenaryPos = getEntities(res, "mercenary").get(0).getPosition();
        assert mercenaryPos.equals(new Position(3, 3));
    }

    @Test
    public void test_portal() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_movementtest_portal", "c_mercenaryTest");

        res = dmc.tick(Direction.UP);
        Position mercenaryPos = getEntities(res, "mercenary").get(0).getPosition();
        assert mercenaryPos.equals(new Position(2, 4));
    }

    @Test
    public void test_nopath() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_movementtest_nopath", "c_mercenaryTest");
        Position oldPos = getEntities(res, "mercenary").get(0).getPosition();
        res = dmc.tick(Direction.UP);
        Position mercenaryPos = getEntities(res, "mercenary").get(0).getPosition();
        assert !mercenaryPos.equals(oldPos);
    }

    @Test
    public void test_swamptile() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swamptile_test", "c_mercenaryTest");

        Position oldPos = getEntities(res, "mercenary").get(0).getPosition();
        res = dmc.tick(Direction.LEFT);

        Position mercenaryPos = getEntities(res, "mercenary").get(0).getPosition();
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);

        res = dmc.tick(Direction.LEFT);
        Position newPos = getEntities(res, "mercenary").get(0).getPosition();

        assert oldPos.getX() - mercenaryPos.getX() == 1;
        assert mercenaryPos.getX() - newPos.getX() == 1;
    }

    @Test
    public void test_movearoundswamp() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swamptile_maze_test", "c_mercenaryTest");

        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.DOWN);

        Position newPos = getEntities(res, "mercenary").get(0).getPosition();
        assert newPos.getX() == 1;
        assert newPos.getY() == 1;
    }
}
