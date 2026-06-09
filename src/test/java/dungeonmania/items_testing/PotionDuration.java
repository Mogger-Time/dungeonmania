package dungeonmania.items_testing;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PotionDuration {
    @Test
    public void invis_potion_expires() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_merc_potion_test", "c_invcpotionTest_basic");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        List<EntityResponse> merc = getEntities(res, "mercenary");
        Position oldmercpos = merc.get(0).getPosition();
        List<ItemResponse> invis = getInventory(res, "invisibility_potion");
        assertDoesNotThrow(() -> dmc.tick(invis.get(0).getId()));

        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);

        merc = getEntities(res, "mercenary");
        Position mercpos = merc.get(0).getPosition();
        List<EntityResponse> player = getEntities(res, "player");
        Position playerpos = player.get(0).getPosition();
        int difference = Math.abs(mercpos.getX() - playerpos.getX()) + Math.abs(mercpos.getY() - playerpos.getY());
        assert oldmercpos.getX() - mercpos.getX() < 4;

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        merc = getEntities(res, "mercenary");
        Position newpos = merc.get(0).getPosition();
        //assert mercpos.getX() - newpos.getX() == 2;
        player = getEntities(res, "player");
        playerpos = player.get(0).getPosition();
        int difference2 = Math.abs(newpos.getX() - playerpos.getX()) + Math.abs(newpos.getY() - playerpos.getY());
        assert difference2 == difference - 4;
    }

    @Test
    public void invinc_potion_expires() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_merc_potion_test", "c_invcpotionTest_basic");

        res = dmc.tick(Direction.RIGHT);

        List<EntityResponse> merc = getEntities(res, "mercenary");
        Position oldmercpos = merc.get(0).getPosition();
        List<ItemResponse> invis = getInventory(res, "invincibility_potion");
        assertDoesNotThrow(() -> dmc.tick(invis.get(0).getId()));

        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);

        merc = getEntities(res, "mercenary");
        Position mercpos = merc.get(0).getPosition();
        assert oldmercpos.getX() - mercpos.getX() == -4;

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        merc = getEntities(res, "mercenary");
        Position newpos = merc.get(0).getPosition();
        assert mercpos.getX() - newpos.getX() == 2;
    }

    @Test
    public void invis_to_invinc() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_merc_potion_test", "c_invcpotionTest_basic");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        List<EntityResponse> merc = getEntities(res, "mercenary");
        Position oldmercpos = merc.get(0).getPosition();
        List<ItemResponse> invis = getInventory(res, "invisibility_potion");
        List<ItemResponse> invinc = getInventory(res, "invincibility_potion");
        assertDoesNotThrow(() -> dmc.tick(invis.get(0).getId()));
        assertDoesNotThrow(() -> dmc.tick(invinc.get(0).getId()));

        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);

        merc = getEntities(res, "mercenary");
        Position mercpos = merc.get(0).getPosition();
        List<EntityResponse> player = getEntities(res, "player");
        Position playerpos = player.get(0).getPosition();
        int difference = Math.abs(mercpos.getX() - playerpos.getX()) + Math.abs(mercpos.getY() - playerpos.getY());
        assert oldmercpos.getX() - mercpos.getX() < 4;

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        merc = getEntities(res, "mercenary");
        Position newpos = merc.get(0).getPosition();
        player = getEntities(res, "player");
        playerpos = player.get(0).getPosition();
        int difference2 = Math.abs(newpos.getX() - playerpos.getX()) + Math.abs(newpos.getY() - playerpos.getY());
        assert difference2 >= difference;
    }
}
