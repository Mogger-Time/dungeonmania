package dungeonmania.game;

import dungeonmania.dtos.EntitiesDto;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.items.*;
import dungeonmania.entities.movingEntity.Enemy;
import dungeonmania.entities.movingEntity.Mercenary;
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
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Game {

    @Getter
    private final String dungeonID;
    @Getter
    private final String dungeonName;
    @Getter
    private final String configName;
    @Getter
    private final List<Entity> entities;
    private final Goal goal;
    private final int initialenemies;
    @Setter
    @Getter
    private Player player;
    private int gameTick;

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

    public List<Enemy> getEnemies() {

        List<Enemy> enemies = new ArrayList<>();

        for (Entity entity : entities) {
            if (entity instanceof Enemy) {
                enemies.add((Enemy) entity);
            }
        }

        return enemies;
    }

    public List<Bomb> getBombs() {
        List<Bomb> bombs = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof Bomb) {
                bombs.add((Bomb) entity);
            }
        }
        return bombs;
    }

    public int getTreasures() {
        int treasurecount = 0;

        for (Item item : player.getInventory()) {
            if (item instanceof Treasure || item instanceof SunStone) {
                treasurecount++;
            }
        }
        return treasurecount;
    }

    public List<FloorSwitch> getFloorSwitches() {
        List<FloorSwitch> switches = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof FloorSwitch) {
                switches.add((FloorSwitch) entity);
            }
        }
        return switches;
    }

    public List<ZombieToastSpawner> getZombieToastSpawners() {
        List<ZombieToastSpawner> spawners = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof ZombieToastSpawner) {
                spawners.add((ZombieToastSpawner) entity);
            }
        }
        return spawners;
    }

    public List<LightBulb> getLightBulbs() {
        List<LightBulb> lightbulbs = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof LightBulb) {
                lightbulbs.add((LightBulb) entity);
            }
        }
        return lightbulbs;
    }

    public List<Wire> getWires() {
        List<Wire> wires = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof Wire) {
                wires.add((Wire) entity);
            }
        }
        return wires;
    }

    public List<SwitchDoor> getSwitchDoors() {
        List<SwitchDoor> switchDoors = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof SwitchDoor) {
                switchDoors.add((SwitchDoor) entity);
            }
        }
        return switchDoors;
    }

    public List<Entity> getPlayerEntities() {
        return getEntitiesinPos(player.getPosition());
    }

    public PlayerStrategy getPlayerStrategy() {
        return player.getStrategy();
    }

    public List<Entity> getEntitiesinPos(Position position) {
        return entities.stream().filter(s -> (s.getPosition().equals(position))).collect(Collectors.toList());
    }

    public Hashtable<Position, Direction> posDirTranslation(Position position) {
        return new Hashtable<>() {{
            put(new Position(position.getX() - 1, position.getY()), Direction.LEFT);
            put(new Position(position.getX(), position.getY() - 1), Direction.UP);
            put(new Position(position.getX() + 1, position.getY()), Direction.RIGHT);
            put(new Position(position.getX(), position.getY() + 1), Direction.DOWN);
        }};
    }

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

    public DungeonResponse getDungeonResponse() {

        List<EntityResponse> entityResponses = new ArrayList<>();
        for (Entity entity : entities) {
            entityResponses.add(entity.getEntityResponse());
        }
        if (player.getHealth() > 0) {
            entityResponses.add(player.getEntityResponse());
        }

        return new DungeonResponse(dungeonID, dungeonName, entityResponses, player.getInvResponse(), player.getBattleResponses(), player.getBuildables(), goal.printcurGoal(this));
    }

    public void updateGame() {
        for (Enemy enemy : getEnemies()) {
            enemy.move(this);
        }
        for (ZombieToastSpawner spawner : getZombieToastSpawners()) {
            spawner.spawnZombie();
        }
        for (Wire wire : getWires()) {
            wire.checkActivated();
        }
        for (Bomb bomb : getBombs()) {
            bomb.checkStatus(this);
        }
        for (LightBulb lightbulb : getLightBulbs()) {
            lightbulb.checkActivated();
        }
        for (SwitchDoor switchdoor : getSwitchDoors()) {
            switchdoor.checkActivated();
        }
        List<Entity> delentities = new ArrayList<>();
        for (Entity entity : getPlayerEntities()) {
            if (entity.interact(player)) {
                delentities.add(entity);
            }
        }
        spawnSpiders();
        entities.removeAll(delentities);
        player.updateStrat(this);
        gameTick++;
    }

    public void spawnSpiders() {
        int spawnRate = GameLauncher.getSpiderSpawnRate();

        if (spawnRate == 0) {
            return;
        }

        int x = (int) (Math.random() * 10);
        int y = (int) (Math.random() * 10);

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
        Entity entityfound = null;
        for (Entity entity : entities) {
            if (entityId.equals(entity.getEntityID())) {
                entityfound = entity;
                break;
            }
        }
        if (entityfound == null) {
            throw new IllegalArgumentException("No entity exists");
        }
        if (entityfound instanceof ZombieToastSpawner) {
            if (!Position.isAdjacent(entityfound.getPosition(), player.getPosition())) {
                throw new InvalidActionException("Not adjacent to ZTS");
            }
            List<BattleItem> battleItems = player.getBattleItems();
            List<BattleItem> swords = battleItems.stream().filter(s -> (s instanceof Sword)).collect(Collectors.toList());
            if (swords.isEmpty()) {
                throw new InvalidActionException("No sword");
            }
            entities.remove(entityfound);
        } else if (entityfound instanceof Mercenary) {
            Mercenary merc = (Mercenary) entityfound;
            player.bribe(merc);
        } else {
            throw new InvalidActionException("Entity can not be interacted with");
        }
    }

    public List<Integer> getDimensions(Position destination) {
        int leftmost = 0;
        int rightmost = 0;
        int upmost = 0;
        int downmost = 0;
        for (Entity entity : entities) {
            Position entpos = entity.getPosition();
            if (entpos.getX() < leftmost) {
                leftmost = entpos.getX();
            } else if (entpos.getX() > rightmost) {
                rightmost = entpos.getX();
            }
            if (entpos.getY() < upmost) {
                upmost = entpos.getY();
            } else if (entpos.getY() > downmost) {
                downmost = entpos.getY();
            }
        }
        if (destination.getX() < leftmost) {
            leftmost = destination.getX();
        } else if (destination.getX() > rightmost) {
            rightmost = destination.getX();
        }
        if (destination.getY() < upmost) {
            upmost = destination.getY();
        } else if (destination.getY() > downmost) {
            downmost = destination.getY();
        }
        leftmost--;
        upmost--;
        rightmost++;
        downmost++;
        List<Integer> dimensions = new ArrayList<>();
        dimensions.add(leftmost);
        dimensions.add(upmost);
        dimensions.add(rightmost);
        dimensions.add(downmost);
        return dimensions;
    }

    public Map<Position, PosDirWrapper> getPath(Entity source, Position destination) {
        List<Integer> grid = getDimensions(destination);
        int leftbound = grid.get(0);
        int rightbound = grid.get(2);
        int upbound = grid.get(1);
        int downbound = grid.get(3);
        Map<Position, Double> distance = new HashMap<>();
        //Position key is the current position
        //PosDirWrapper.getPosition is the previous position
        //PosDirWrapper.getDirection is the direction taken from prev to get to current
        Map<Position, PosDirWrapper> previous = new HashMap<>();
        List<Position> visited = new ArrayList<>();
        List<Position> queue = new ArrayList<>();
        for (int i = leftbound; i <= rightbound; i++) {
            for (int j = upbound; j <= downbound; j++) {
                Position node = new Position(i, j);
                distance.put(node, Double.MAX_VALUE);
            }
        }
        distance.put(source.getPosition(), 0.0);
        queue.add(source.getPosition());
        while (!queue.isEmpty()) {
            Position temp = null;
            Double smallestdistance = Double.MAX_VALUE;
            for (Position pos : queue) {
                if (distance.get(pos) < smallestdistance) {
                    temp = pos;
                    smallestdistance = distance.get(pos);
                }
            }
            visited.add(temp);
            queue.remove(temp);
            if (temp.equals(destination)) {
                break;
            }
            for (Entry<Position, Direction> entry : posDirTranslation(temp).entrySet()) {
                Position adjPos = entry.getKey();
                Direction dirto = entry.getValue();
                if (leftbound <= adjPos.getX() && adjPos.getX() <= rightbound && upbound <= adjPos.getY() && adjPos.getY() <= downbound) {
                    int cost = 1;
                    List<Entity> entitiesatPos = getEntitiesinPos(adjPos);
                    for (Entity possibleportal : entitiesatPos) {
                        if (possibleportal instanceof Portal) {
                            Portal foundportal = (Portal) possibleportal;
                            adjPos = foundportal.getnewLoc(dirto, this);
                        }
                        if (possibleportal.getCollision()) {
                            adjPos = null;
                        }
                        if (possibleportal instanceof SwampTile) {
                            SwampTile foundswamp = (SwampTile) possibleportal;
                            cost = cost + foundswamp.getFactor();
                        }
                    }
                    if (adjPos == null) {
                        continue;
                    }
                    if (!visited.contains(adjPos)) {
                        if (!queue.contains(adjPos)) {
                            queue.add(adjPos);
                        }
                        if (distance.get(temp) + cost < distance.get(adjPos)) {
                            distance.put(adjPos, (distance.get(temp) + cost));
                            previous.put(adjPos, new PosDirWrapper(temp, dirto));
                        }
                    }
                }
            }
        }
        return previous;
    }
}
