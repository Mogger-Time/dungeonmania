package dungeonmania.StaticEntities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.countEntityOfType;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class TestZombieToastSpawner {

    @Test
    @DisplayName("Test spawn one zombie toast")
    public void testSpawnOneZombie() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_ZTSpawnerTest_basic", "c_ZTSpawnerTest_zombieOneTick");
        Position spawnerPos = getEntities(res, "zombie_toast_spawner").get(0).getPosition();
        res = dmc.tick(Direction.UP);

        assertEquals(1, countEntityOfType(res, "zombie_toast"));
        Position zombiePos = getEntities(res, "zombie_toast").get(0).getPosition();
        assertTrue(isZombieSpawnInValidPos(spawnerPos, zombiePos, null));
    }

    @Test
    @DisplayName("Test spawn multiple zombie toast, one zombie every tick")
    public void testSpawnMultipleZombies1() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_ZTSpawnerTest_basic", "c_ZTSpawnerTest_zombieOneTick");
        Position spawnerPos = getEntities(res, "zombie_toast_spawner").get(0).getPosition();

        for (int zombieCount = 0; zombieCount < 5; zombieCount++) {
            res = dmc.tick(Direction.RIGHT);
            assertEquals(zombieCount + 1, countEntityOfType(res, "zombie_toast"));
            EntityResponse zombie = getEntities(res, "zombie_toast").get(zombieCount);
            assertTrue(isZombieSpawnInValidPos(spawnerPos, zombie.getPosition(), null));
        }
    }

    @Test
    @DisplayName("Test spawn multiple zombie toast, one zombie every four tick")
    public void testSpawnMultipleZombies2() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_ZTSpawnerTest_basic", "c_ZTSpawnerTest_zombieFourTicks");
        Position spawnerPos = getEntities(res, "zombie_toast_spawner").get(0).getPosition();

        for (int zombieCount = 0; zombieCount < 9; zombieCount++) {
            res = dmc.tick(Direction.RIGHT);
            if ((zombieCount + 1) % 4 == 0) {
                assertEquals((zombieCount + 1) / 4, countEntityOfType(res, "zombie_toast"));
                EntityResponse zombie = getEntities(res, "zombie_toast").get(((zombieCount + 1) / 4) - 1);
                assertTrue(isZombieSpawnInValidPos(spawnerPos, zombie.getPosition(), null));
            }
        }
    }

    @Test
    @DisplayName("Test spawner against one wall")
    public void testSpawnerAgainstOneWall() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_ZTSpawnerTest_oneWall", "c_ZTSpawnerTest_zombieOneTick");
        Position spawnerPos = getEntities(res, "zombie_toast_spawner").get(0).getPosition();
        Position wall = getEntities(res, "wall").get(0).getPosition();
        int zombieCount = 0;
        res = dmc.tick(Direction.UP);
        zombieCount++;
        assertEquals(zombieCount, countEntityOfType(res, "zombie_toast"));

        // Check that each zombie spawned is not on the wall tile
        res = dmc.tick(Direction.RIGHT);
        zombieCount++;
        EntityResponse zombie = getEntities(res, "zombie_toast").get(zombieCount - 1);
        assertEquals(zombieCount, countEntityOfType(res, "zombie_toast"));
        assertTrue(isZombieSpawnInValidPos(spawnerPos, zombie.getPosition(), wall));

        res = dmc.tick(Direction.RIGHT);
        zombieCount++;
        zombie = getEntities(res, "zombie_toast").get(zombieCount - 1);
        assertEquals(zombieCount, countEntityOfType(res, "zombie_toast"));
        assertTrue(isZombieSpawnInValidPos(spawnerPos, zombie.getPosition(), wall));
    }

    @Test
    @DisplayName("Test spawner cannot spawn zombie toast if surrounded by walls")
    public void testSpawnerAgainstWalls() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_ZTSpawnerTest_fourWalls", "c_ZTSpawnerTest_zombieOneTick");

        res = dmc.tick(Direction.UP);
        assertEquals(0, countEntityOfType(res, "zombie_toast"));
    }

    public boolean isZombieSpawnInValidPos(Position spawnerPos, Position zombiePos, Position obstacle) {
        List<Position> validSpawnPositions = new ArrayList<Position>();
        int x = spawnerPos.getX();
        int y = spawnerPos.getY();
        validSpawnPositions.add(new Position(x+1, y));
        validSpawnPositions.add(new Position(x, y+1));
        validSpawnPositions.add(new Position(x-1, y));
        validSpawnPositions.add(new Position(x, y-1));

        if (obstacle == null) {
            if (validSpawnPositions.contains(zombiePos)) {
                return true;
            } 
        }
        else {
            if (validSpawnPositions.contains(zombiePos) && zombiePos != obstacle) {
                return true;
            }
        }

        return false; 
    }
}
