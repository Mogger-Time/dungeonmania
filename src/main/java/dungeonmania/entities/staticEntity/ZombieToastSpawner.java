package dungeonmania.entities.staticEntity;

import dungeonmania.dtos.EntitiesDto;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.InteractableEntity;
import dungeonmania.entities.items.BattleItem;
import dungeonmania.entities.items.Sword;
import dungeonmania.entities.movingEntity.Player;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.game.Game;
import dungeonmania.game.GameRuntimeContext;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.util.RandomProvider;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

public class ZombieToastSpawner extends StaticEntity implements InteractableEntity {

    private int zombieSpawnRate;
    private int tickCounter = 1;

    public ZombieToastSpawner() {
        super();
        setName("zombie_toast_spawner");
        super.setInteractable(true);
        super.setCollision(true);
    }

    // Assumes zombie cannot spawn on static entities, but can on same tile as items or moving entities
    public void spawnZombie() {
        if (zombieSpawnRate == 0) {
            return;
        }

        Position spawnerPos = this.getPosition();

        Game activeGame = GameRuntimeContext.getActiveGame();

        Hashtable<Position, Direction> posDir = activeGame.posDirTranslation(spawnerPos);
        ArrayList<Direction> adjacentTiles = new ArrayList<>();
        adjacentTiles.add(Direction.LEFT);
        adjacentTiles.add(Direction.UP);
        adjacentTiles.add(Direction.RIGHT);
        adjacentTiles.add(Direction.DOWN);

        for (Position pos : posDir.keySet()) {
            List<Entity> entitiesOnTile = activeGame.getEntitiesinPos(pos);
            for (Entity entity : entitiesOnTile) {
                if (entity instanceof StaticEntity) {
                    adjacentTiles.remove(posDir.get(pos));
                    break;
                }
            }
        }

        int noOfPositions = adjacentTiles.size();
        // Zombie will spawn at every X ticks if there are valid positions to spawn in
        if (tickCounter % zombieSpawnRate == 0 && noOfPositions > 0) {
            // Get random valid spawn position
            int dirIndex = RandomProvider.nextInt(noOfPositions);

            // Add zombie to dungeon
            Position validPos = null;
            Direction validDir = adjacentTiles.get(dirIndex);
            Hashtable<Position, Direction> validPosDir = activeGame.posDirTranslation(spawnerPos);
            for (Position pos : posDir.keySet()) {
                if (validPosDir.get(pos) == validDir) {
                    validPos = pos;
                    break;
                }
            }

            int zombieX = validPos.getX();
            int zombieY = validPos.getY();
            Entity zombie = EntityFactory.createEntity(new EntitiesDto(zombieX, zombieY, "zombie_toast"));
            activeGame.addEntity(zombie);
        }

        tickCounter++;
    }

    @Override
    public void setupEntity(EntitiesDto entitiesDto, Position position) {
        setPosition(position);
        this.zombieSpawnRate = GameRuntimeContext.getConfig().getZombieSpawnRate();
    }

    @Override
    public void interact(Game game, Player player) throws InvalidActionException {
        if (!Position.isAdjacent(getPosition(), player.getPosition())) {
            throw new InvalidActionException("Not adjacent to ZTS");
        }
        List<BattleItem> swords = player.getBattleItems().stream()
                .filter(Sword.class::isInstance)
                .collect(Collectors.toList());
        if (swords.isEmpty()) {
            throw new InvalidActionException("No sword");
        }
        game.removeEntity(this);
    }
}
      