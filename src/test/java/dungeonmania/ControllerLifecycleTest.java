package dungeonmania;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static dungeonmania.TestUtils.getPlayer;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ControllerLifecycleTest {
    @Test
    public void saveLoadAndListGames() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse initial = controller.newGame("d_movementTest_testMovementDown", "c_movementTest_testMovementDown");
        assertNotNull(initial);

        String gameName = "controller_lifecycle_test_save";
        File saved = new File("games/" + gameName + ".json");
        if (saved.exists()) {
            assertTrue(saved.delete());
        }

        assertDoesNotThrow(() -> controller.allGames());
        assertDoesNotThrow(() -> controller.saveGame(gameName));
        assertTrue(saved.exists());

        List<String> games = controller.allGames();
        assertFalse(games.isEmpty());
        assertTrue(games.stream().anyMatch(name -> name.equals(gameName + ".json")));
    }

    @Test
    public void saveLoadThenContinueMatchesOriginalRun() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("d_movementTest_testMovementDown", "c_movementTest_testMovementDown");
        controller.tick(Direction.DOWN);

        String gameName = "controller_lifecycle_continue";
        File saved = new File("games/" + gameName + ".json");
        if (saved.exists()) {
            assertTrue(saved.delete());
        }

        assertDoesNotThrow(() -> controller.saveGame(gameName));

        DungeonResponse continuedWithoutLoad = controller.tick(Direction.RIGHT);
        DungeonResponse loaded = assertDoesNotThrow(() -> controller.loadGame(gameName));
        assertNotNull(loaded);
        DungeonResponse continuedAfterLoad = controller.tick(Direction.RIGHT);

        assertEquals(getPlayer(continuedWithoutLoad).get().getPosition(), getPlayer(continuedAfterLoad).get().getPosition());
        assertEquals(continuedWithoutLoad.getInventory().size(), continuedAfterLoad.getInventory().size());
        assertEquals(continuedWithoutLoad.getGoals(), continuedAfterLoad.getGoals());
    }
}
