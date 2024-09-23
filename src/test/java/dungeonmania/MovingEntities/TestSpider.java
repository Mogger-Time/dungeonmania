package dungeonmania.MovingEntities;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static dungeonmania.TestUtils.countEntityOfType;
import static dungeonmania.TestUtils.getEntities;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSpider {
    private static DungeonResponse genericSpiderSequence(DungeonManiaController controller, String configFile) {
        /*
         *  exit   wall player  wall
         *  wall   [  ] spider  wall
         *  wall   wall  wall  wall
         */
        DungeonResponse initialResponse = controller.newGame("d_battleTest_basicSpider", configFile);
        int spiderCount = countEntityOfType(initialResponse, "spider");

        assertEquals(1, countEntityOfType(initialResponse, "player"));
        assertEquals(1, spiderCount);
        return controller.tick(Direction.UP);
    }

    @Test
    @DisplayName("Test Spider immediately moves 1 square upwards after spawn")

    public void testSpiderMovement() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_spiderTest_basicMovement", "c_spiderTest_basicMovement");
        // get original pos
        Position pos = getEntities(initDungonRes, "spider").get(0).getPosition();
        // Tick the game to move the spider
        DungeonResponse actualDungonRes = dmc.tick(Direction.UP);
        Position newPos = getEntities(actualDungonRes, "spider").get(0).getPosition();

        // Assert that the spider moved 1 square upwards
        assertEquals(pos.getX(), newPos.getX());
        assertEquals(pos.getY() - 1, newPos.getY());
    }

    // This test was given from the example tests
    @Test
    @DisplayName("Test basic movement of spiders")
    public void basicMovement() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_basicMovement", "c_spiderTest_basicMovement");
        Position pos = getEntities(res, "spider").get(0).getPosition();

        List<Position> movementTrajectory = new ArrayList<>();
        int x = pos.getX();
        int y = pos.getY();
        int nextPositionElement = 0;
        movementTrajectory.add(new Position(x, y - 1));
        movementTrajectory.add(new Position(x + 1, y - 1));
        movementTrajectory.add(new Position(x + 1, y));
        movementTrajectory.add(new Position(x + 1, y + 1));
        movementTrajectory.add(new Position(x, y + 1));
        movementTrajectory.add(new Position(x - 1, y + 1));
        movementTrajectory.add(new Position(x - 1, y));
        movementTrajectory.add(new Position(x - 1, y - 1));

        // Assert Circular Movement of Spider
        for (int i = 0; i <= 20; ++i) {
            res = dmc.tick(Direction.UP);
            assertEquals(movementTrajectory.get(nextPositionElement), getEntities(res, "spider").get(0).getPosition());

            nextPositionElement++;
            if (nextPositionElement == 8) {
                nextPositionElement = 0;
            }
        }
    }

    @Test
    @DisplayName("Test Spider reverses direction when it hits a boulder")
    public void testSpiderReversesDirection() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_boulderEncounter", "c_spiderTest_boulderEncounter");
        // Tick the game to move the spider up from x: 5, y: 5 to x: 5, y: 4
        res = dmc.tick(Direction.UP);
        // Tick the game to move the player left and check if spider moved left instead of right
        res = dmc.tick(Direction.LEFT);
        Position newPos = getEntities(res, "spider").get(0).getPosition();
        // Check the next position that the spider moves on is a boulder
        // spider should move to x: 4, y: 4 instead of x: 6, y: 4
        Position boulderPos = getEntities(res, "boulder").get(0).getPosition();
        assertEquals(newPos.getX(), boulderPos.getX() - 2);
        assertEquals(newPos.getY(), boulderPos.getY());
    }

    @Test
    @DisplayName("Test Spider is able to traverse through every entity")
    public void testSpiderTraversesEveryEntity() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_entityTraversal", "c_spiderTest_entityTraversal");
        Position pos = getEntities(res, "spider").get(0).getPosition();

        List<Position> movementTrajectory = new ArrayList<>();
        int x = pos.getX();
        int y = pos.getY();
        int nextPositionElement = 0;
        movementTrajectory.add(new Position(x, y - 1));
        movementTrajectory.add(new Position(x + 1, y - 1));
        movementTrajectory.add(new Position(x + 1, y));
        movementTrajectory.add(new Position(x + 1, y + 1));
        movementTrajectory.add(new Position(x, y + 1));
        movementTrajectory.add(new Position(x - 1, y + 1));
        movementTrajectory.add(new Position(x - 1, y));
        movementTrajectory.add(new Position(x - 1, y - 1));

        // Assert Circular Movement of Spider is not blocked by wall, door, switches, portals and exits
        for (int i = 0; i <= 20; ++i) {
            res = dmc.tick(Direction.UP);
            assertEquals(movementTrajectory.get(nextPositionElement), getEntities(res, "spider").get(0).getPosition());

            nextPositionElement++;
            if (nextPositionElement == 8) {
                nextPositionElement = 0;
            }
        }
    }

    @Test
    @DisplayName("Test Spider battle with player - player loses")
    public void testSpiderBattleSpiderWins() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_battleTest_basicSpider", "c_battleTests_basicMercenaryPlayerDies");

        res = dmc.tick(Direction.DOWN);

        // assert there are no player in the game
        assertEquals(getEntities(res, "player").size(), 0);
        assertEquals(getEntities(res, "spider").size(), 1);
    }

    @Test
    @DisplayName("Test Spider battle with player - player wins")
    public void testSpiderBattlePlayerWins() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_battleTest_basicSpider", "c_battleTests_basicMercenaryMercenaryDies");

        res = dmc.tick(Direction.DOWN);

        // assert there are no player in the game
        assertEquals(getEntities(res, "spider").size(), 0);
        assertEquals(getEntities(res, "player").size(), 1);
    }
}
