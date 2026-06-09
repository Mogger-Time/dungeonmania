package dungeonmania.game;

import dungeonmania.dtos.EntitiesDto;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.items.*;
import dungeonmania.entities.movingEntity.Enemy;
import dungeonmania.entities.movingEntity.Player;
import dungeonmania.entities.movingEntity.playerStrategy.PlayerStrategy;
import dungeonmania.entities.staticEntity.*;
import dungeonmania.entities.staticEntity.logicalEntity.LightBulb;
import dungeonmania.entities.staticEntity.logicalEntity.SwitchDoor;
import dungeonmania.entities.staticEntity.logicalEntity.Wire;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.goals.Goal;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.PosDirWrapper;
import dungeonmania.util.Position;
import dungeonmania.util.RandomProvider;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class Game {

    private final String dungeonID;
    private final String dungeonName;
    private final String configName;
    private final List<Entity> entities;
    private final Goal goal;
    private final int initialenemies;
    private Player player;
    private int gameTick;
    @JsonIgnore
    private transient DungeonResponseMapper responseMapper = new DungeonResponseMapper();
    @JsonIgnore
    private transient WorldUpdateService worldUpdateService = new WorldUpdateService();
    @JsonIgnore
    private transient InteractionService interactionService = new InteractionService();
    @JsonIgnore
    private transient PathfindingService pathfindingService = new PathfindingService();

    public Game(String dungeonName, String configName, List<Entity> entities, Goal goal) {
        this.dungeonID = dungeonName + UUID.randomUUID();
        this.dungeonName = dungeonName;
        this.configName = configName;
        this.entities = entities;
        this.goal = goal;
        this.gameTick = 1;
        for (Entity entity : entities) {
            if (entity instanceof Player) {
                player = (Player) entity;
                entities.remove(entity);
                break;
            }
        }
        initialenemies = getEnemies().size();
    }

    @JsonCreator
    public Game(@JsonProperty("dungeonID") String dungeonID,
                @JsonProperty("dungeonName") String dungeonName,
                @JsonProperty("configName") String configName,
                @JsonProperty("entities") List<Entity> entities,
                @JsonProperty("goal") Goal goal,
                @JsonProperty("initialEnemies") int initialEnemies,
                @JsonProperty("player") Player player,
                @JsonProperty("gameTick") int gameTick) {
        this.dungeonID = dungeonID;
        this.dungeonName = dungeonName;
        this.configName = configName;
        this.entities = entities != null ? entities : new ArrayList<>();
        this.goal = goal;
        this.player = player;
        this.gameTick = gameTick;
        this.initialenemies = initialEnemies;
        ensureServices();
    }

    public int getInitialEnemies() {
        return initialenemies;
    }

    public Position getPlayerPosition() {
        return player.getPosition();
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    @JsonIgnore
    public List<Enemy> getEnemies() {

        List<Enemy> enemies = new ArrayList<>();

        for (Entity entity : entities) {
            if (entity instanceof Enemy) {
                enemies.add((Enemy) entity);
            }
        }

        return enemies;
    }

    @JsonIgnore
    public List<Bomb> getBombs() {
        List<Bomb> bombs = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof Bomb) {
                bombs.add((Bomb) entity);
            }
        }
        return bombs;
    }

    @JsonIgnore
    public int getTreasures() {
        int treasurecount = 0;

        for (Item item : player.getInventory()) {
            if (item instanceof Treasure || item instanceof SunStone) {
                treasurecount++;
            }
        }
        return treasurecount;
    }

    @JsonIgnore
    public List<FloorSwitch> getFloorSwitches() {
        List<FloorSwitch> switches = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof FloorSwitch) {
                switches.add((FloorSwitch) entity);
            }
        }
        return switches;
    }

    @JsonIgnore
    public List<ZombieToastSpawner> getZombieToastSpawners() {
        List<ZombieToastSpawner> spawners = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof ZombieToastSpawner) {
                spawners.add((ZombieToastSpawner) entity);
            }
        }
        return spawners;
    }

    @JsonIgnore
    public List<LightBulb> getLightBulbs() {
        List<LightBulb> lightbulbs = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof LightBulb) {
                lightbulbs.add((LightBulb) entity);
            }
        }
        return lightbulbs;
    }

    @JsonIgnore
    public List<Wire> getWires() {
        List<Wire> wires = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof Wire) {
                wires.add((Wire) entity);
            }
        }
        return wires;
    }

    @JsonIgnore
    public List<SwitchDoor> getSwitchDoors() {
        List<SwitchDoor> switchDoors = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof SwitchDoor) {
                switchDoors.add((SwitchDoor) entity);
            }
        }
        return switchDoors;
    }

    @JsonIgnore
    public List<Entity> getPlayerEntities() {
        return getEntitiesinPos(player.getPosition());
    }

    @JsonIgnore
    public PlayerStrategy getPlayerStrategy() {
        return player.getStrategy();
    }

    @JsonIgnore
    public List<Entity> getEntitiesinPos(Position position) {
        return entities.stream().filter(s -> (s.getPosition().equals(position))).collect(Collectors.toList());
    }

    @JsonIgnore
    public Hashtable<Position, Direction> posDirTranslation(Position position) {
        return new Hashtable<>() {{
            put(new Position(position.getX() - 1, position.getY()), Direction.LEFT);
            put(new Position(position.getX(), position.getY() - 1), Direction.UP);
            put(new Position(position.getX() + 1, position.getY()), Direction.RIGHT);
            put(new Position(position.getX(), position.getY() + 1), Direction.DOWN);
        }};
    }

    @JsonIgnore
    public ArrayList<Direction> getValidAdjacentTiles(Position position) {
        Hashtable<Position, Direction> posDir = posDirTranslation(position);
        ArrayList<Direction> adjacentTiles = new ArrayList<>();
        adjacentTiles.add(Direction.LEFT);
        adjacentTiles.add(Direction.UP);
        adjacentTiles.add(Direction.RIGHT);
        adjacentTiles.add(Direction.DOWN);

        for (Position pos : posDir.keySet()) {
            List<Entity> entitiesOnTile = getEntitiesinPos(pos);
            for (Entity entity : entitiesOnTile) {
                if (entity.getCollision()) {
                    adjacentTiles.remove(posDir.get(pos));
                    break;
                }
            }
        }

        return adjacentTiles;
    }

    @JsonIgnore
    public boolean noBouldersNext(Direction direction, Position position) {
        Position newPosition = position.translateBy(direction);
        List<Entity> entitiesOnTile = getEntitiesinPos(newPosition);
        for (Entity entity : entitiesOnTile) {
            if (entity instanceof Boulder) {
                return false;
            }
        }
        return true;
    }

    @JsonIgnore
    public DungeonResponse getDungeonResponse() {
        ensureServices();
        return responseMapper.map(this);
    }

    public void updateGame() {
        ensureServices();
        worldUpdateService.update(this);
    }

    public void spawnSpiders() {
        int spawnRate = GameRuntimeContext.getConfig().getSpiderSpawnRate();

        if (spawnRate == 0) {
            return;
        }

        int x = RandomProvider.nextInt(10);
        int y = RandomProvider.nextInt(10);

        if (gameTick % spawnRate == 0) {
            this.addEntity(EntityFactory.createEntity(new EntitiesDto(x, y, "spider")));
        }
    }

    public void useItem(String itemUsedId) throws InvalidActionException {
        Bomb itemresult = player.useItem(itemUsedId);
        if (itemresult != null) {
            entities.add(itemresult);
        }
    }

    public void build(String buildable) throws InvalidActionException {
        player.craftItem(buildable);
    }

    public void move(Direction movement) {
        Position futurepos = player.getNewPosition(movement);
        List<Entity> entsinpos = getEntitiesinPos(futurepos);

        // Check if entity in way, or portal transports, or boulder to push
        for (Entity entity : entsinpos) {
            entity.premove(player, movement);
            if (entity instanceof Portal) {
                return;
            }
        }
        entsinpos = getEntitiesinPos(futurepos); //in case boulder moves, get entities again
        entsinpos = entsinpos.stream().filter(Entity::getCollision).collect(Collectors.toList());
        if (entsinpos.isEmpty()) {
            player.setPosition(futurepos);
        }
        List<Entity> delentities = new ArrayList<>();
        for (Entity entity : getPlayerEntities()) {
            if (entity.interact(player)) {
                delentities.add(entity);
            }
        }
        entities.removeAll(delentities);
    }

    public void interact(String entityId) throws InvalidActionException {
        ensureServices();
        interactionService.interact(this, entityId);
    }

    @JsonIgnore
    public List<Integer> getDimensions(Position destination) {
        ensureServices();
        return pathfindingService.getDimensions(this, destination);
    }

    @JsonIgnore
    public Map<Position, PosDirWrapper> getPath(Entity source, Position destination) {
        ensureServices();
        return pathfindingService.getPath(this, source, destination);
    }

    private void ensureServices() {
        if (responseMapper == null) {
            responseMapper = new DungeonResponseMapper();
        }
        if (worldUpdateService == null) {
            worldUpdateService = new WorldUpdateService();
        }
        if (interactionService == null) {
            interactionService = new InteractionService();
        }
        if (pathfindingService == null) {
            pathfindingService = new PathfindingService();
        }
    }
}
