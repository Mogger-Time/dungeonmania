package dungeonmania.MovingEntities;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;
import static dungeonmania.TestUtils.getValueFromConfigFile;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class TestAssassin {
    @Test
    public void test_bribe_fail() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassin_test", "c_assassin_test");

        res = dmc.tick(Direction.UP);
        List<EntityResponse> assassin = getEntities(res, "assassin");
        String assassinid = assassin.get(0).getId();
        res = assertDoesNotThrow(()->dmc.interact(assassinid));

        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);

        assert res.getBattles().size() == 1;
    }

    @Test
    public void see_invisible() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassin_test", "c_assassin_test");

        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);

        List<ItemResponse> potion = getInventory(res, "invisibility_potion");
        String potionid = potion.get(0).getId();
        res = assertDoesNotThrow(()->dmc.tick(potionid));

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);

        List<EntityResponse> assassin = getEntities(res, "assassin");
        Position assassinpos = assassin.get(0).getPosition();

        assert assassinpos.getY() == 6;
    }

    @Test
    public void out_of_range() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassin_test", "c_assassin_test");

        res = dmc.tick(Direction.DOWN);

        List<ItemResponse> potion = getInventory(res, "invisibility_potion");
        String potionid = potion.get(0).getId();
        res = assertDoesNotThrow(()->dmc.tick(potionid));

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);

        List<EntityResponse> assassin = getEntities(res, "assassin");
        Position assassinpos = assassin.get(0).getPosition();

        assert assassinpos.getY() < 5;
    }
}
