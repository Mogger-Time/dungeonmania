package dungeonmania.entities.movingEntity;

import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.Battle;
import dungeonmania.entities.items.BattleItem;
import dungeonmania.entities.items.Bomb;
import dungeonmania.entities.items.Bow;
import dungeonmania.entities.items.Item;
import dungeonmania.entities.items.ItemFactory;
import dungeonmania.entities.items.MidnightArmour;
import dungeonmania.entities.items.Potion;
import dungeonmania.entities.items.Sceptre;
import dungeonmania.entities.items.Shield;
import dungeonmania.entities.items.Treasure;
import dungeonmania.entities.movingEntity.mercStrategy.ControlledStrat;
import dungeonmania.entities.movingEntity.playerStrategy.NormalStrategy;
import dungeonmania.entities.movingEntity.playerStrategy.PlayerStrategy;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.game.Game;
import dungeonmania.game.GameLauncher;

import org.json.JSONObject;

import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.ItemResponse;

public class Player extends MovingEntity {
    
    private transient PlayerStrategy strategy;
    private transient List<PlayerStrategy> queuedstrats = new ArrayList<PlayerStrategy>();
    private transient List<Item> inventory = new ArrayList<Item>();
    private transient List<Battle> battles = new ArrayList<Battle>();
    private transient List<Mercenary> ally = new ArrayList<Mercenary>();
    private double allyattack;
    private double allydefence;
    private Position prevpos;
    public Player() {
        super();
        this.strategy = new NormalStrategy();
        this.queuedstrats = new ArrayList<PlayerStrategy>();
        this.inventory = new ArrayList<Item>();
        this.battles = new ArrayList<Battle>();
        setName("player");
    }

    public void setAllyattack(double attack) {
        allyattack = attack;
    }

    public double getAllyattack() {
        return allyattack;
    }

    public void setAllydefence(double defence) {
        allydefence = defence;
    }

    public double getAllydefence() {
        return allydefence;
    }

    @Override
    public void setupEntity(JSONObject entityConfig, Position position) {
        setDamage(entityConfig.getInt("player_attack"));
        setHealth(entityConfig.getInt("player_health"));
        setAllyattack(entityConfig.getInt("ally_attack"));
        setAllydefence(entityConfig.getInt("ally_defence"));
        setInteractable(false);
        setPosition(position);
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public List<Mercenary> getAlly() {
        return ally;
    }

    public void removeItem(Item item) {
        inventory.remove(item);
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public PlayerStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(PlayerStrategy strategy) {
        this.strategy = strategy;
    }

    public List<ItemResponse> getInvResponse() {
        List<ItemResponse> responses = new ArrayList<ItemResponse>();
        for (Item item : inventory) {
            responses.add(item.getItemResponse());
        }
        return responses;
    }

    public List<BattleItem> getBattleItems() {
        return strategy.getBattleItems(inventory);
    }

    public void updateStrat(Game game) {
        ally = ally.stream().filter(s->s.isAlly()).collect(Collectors.toList());
        strategy.wearout();
        if (strategy.getDuration() == 0) {
            if (queuedstrats.size() == 0) {
                strategy = new NormalStrategy();
            } else {
                strategy = queuedstrats.get(0);
                queuedstrats.remove(strategy);
            }
            for (Enemy enemy : game.getEnemies()) {
                enemy.alert(strategy);
            }
        }
    }

    public void startBattle(Enemy enemy) {
        Battle newbattle = strategy.startBattle(this, enemy);
        if (newbattle != null) {
            battles.add(newbattle);
        }
    }

    public List<Battle> getBattles() {
        return battles;
    }

    public List<BattleResponse> getBattleResponses() {
        List<BattleResponse> battleResponses = new ArrayList<BattleResponse>();
        for (Battle battle : battles) {
            battleResponses.add(battle.getResponse());
        }
        return battleResponses;
    }

    public void addStrat(PlayerStrategy newstate) {
        if (strategy instanceof NormalStrategy) {
            strategy = newstate;
            Game activegame = GameLauncher.getActiveGame();
            for (Enemy enemy : activegame.getEnemies()) {
                enemy.alert(strategy);
            }
        } else {
            queuedstrats.add(newstate);
        }
    }

    //true if its a bomb, controller will handle that case
    public Bomb useItem(String itemid) throws InvalidActionException, IllegalArgumentException {
        Item itemfound = null;
        for (Item item : inventory) {
            if (itemid.equals(item.getEntityID())) {
                itemfound = item;
            }
        }
        if (itemfound == null) {
            throw new InvalidActionException("Item not in inventory");
        } else if (itemfound instanceof Bomb) {
            inventory.remove(itemfound);
            Bomb downcast = (Bomb) itemfound;
            downcast.deploy();
            downcast.setPosition(getPosition());
            return downcast;
        } else if (itemfound instanceof Potion) {
            inventory.remove(itemfound);
            Potion downcast = (Potion) itemfound;
            addStrat(downcast.consume());
            return null;
        } else {
            throw new IllegalArgumentException("Item not usable");
        }
    }

    public List<String> getBuildables() {
        List<String> buildables = new ArrayList<String>();
        if (Bow.checkBuildable(inventory)) {
            buildables.add("bow");
        }
        if (Shield.checkBuildable(inventory)) {
            buildables.add("shield");
        }
        if (Sceptre.checkBuildable(inventory)) {
            buildables.add("sceptre");
        }
        if (MidnightArmour.checkBuildable(inventory)) {
            buildables.add("midnight_armour");
        }
        return buildables;
    }

    public void craftItem(String itemtype) throws IllegalArgumentException, InvalidActionException {
        Item crafted = ItemFactory.craftItem(itemtype, inventory);
        if (crafted == null) {
            throw new IllegalArgumentException("Not a buildable item");
        } else {
            inventory.add(crafted);
        }
    }

    public void bribe(Mercenary merc) throws InvalidActionException {
        Position playerpos = this.getPosition();
        Position mercpos = merc.getPosition();
        List<Item> sceptres = inventory.stream().filter(s->(s instanceof Sceptre)).collect(Collectors.toList());
        if (sceptres.size() > 0) {
            Sceptre sceptre = (Sceptre) sceptres.get(0);
            int duration = sceptre.getMindControlDuration() - 1;
            ally.add(merc);
            merc.setAllystatus(new ControlledStrat(duration));
            merc.setInteractable(false);
            return;
        }
        
        if (Math.abs(playerpos.getX() - mercpos.getX()) > merc.getBribeRadius() || Math.abs(playerpos.getY() - mercpos.getY()) > merc.getBribeRadius()) {
            throw new InvalidActionException("Mercenary out of range");
        }
        List<Item> treasures = inventory.stream().filter(s->(s instanceof Treasure)).collect(Collectors.toList());
        if (treasures.size() < merc.getBribeAmount()) {
            throw new InvalidActionException("Not enough treasure");
        }
        inventory.removeAll(treasures.subList(0, merc.getBribeAmount()));
        if (merc.bribe()) {
            ally.add(merc);
        }
    }

	public void setInventory(List<Item> items) {
        inventory = items;
	}

    public List<PlayerStrategy> getQueuedStrategies() {
        return queuedstrats;
    }

    public void setQueuedStrategies(List<PlayerStrategy> playerQueuedStrategies) {
        queuedstrats = playerQueuedStrategies;
    }

    public void setAlly(List<Mercenary> loadedAllys) {
        ally = loadedAllys;
    }

    public void setBattles(List<Battle> loadedBattles) {
        battles = loadedBattles;
    }

    public Position getprevPos() {
        return prevpos;
    }

    @Override
    public void setPosition(Position position) {
        this.prevpos = getPosition();
        super.setPosition(position);
    }
}
