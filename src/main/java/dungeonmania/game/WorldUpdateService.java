package dungeonmania.game;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntity.Enemy;
import dungeonmania.entities.staticEntity.ZombieToastSpawner;
import dungeonmania.entities.staticEntity.logicalEntity.LightBulb;
import dungeonmania.entities.staticEntity.logicalEntity.SwitchDoor;
import dungeonmania.entities.staticEntity.logicalEntity.Wire;
import dungeonmania.entities.items.Bomb;

import java.util.ArrayList;
import java.util.List;

public class WorldUpdateService {
    public void update(Game game) {
        for (Enemy enemy : game.getEnemies()) {
            enemy.move(game);
        }
        for (ZombieToastSpawner spawner : game.getZombieToastSpawners()) {
            spawner.spawnZombie();
        }
        for (Wire wire : game.getWires()) {
            wire.checkActivated();
        }
        for (Bomb bomb : game.getBombs()) {
            bomb.checkStatus(game);
        }
        for (LightBulb lightbulb : game.getLightBulbs()) {
            lightbulb.checkActivated();
        }
        for (SwitchDoor switchdoor : game.getSwitchDoors()) {
            switchdoor.checkActivated();
        }
        List<Entity> deleted = new ArrayList<>();
        for (Entity entity : game.getPlayerEntities()) {
            if (entity.interact(game.getPlayer())) {
                deleted.add(entity);
            }
        }
        game.spawnSpiders();
        game.getEntities().removeAll(deleted);
        game.getPlayer().updateStrat(game);
        game.setGameTick(game.getGameTick() + 1);
    }
}
