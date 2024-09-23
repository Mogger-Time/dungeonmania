package dungeonmania.entities.movingEntity.playerStrategy;

import dungeonmania.Battle;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.entities.items.BattleItem;
import dungeonmania.entities.items.Item;
import dungeonmania.entities.movingEntity.Enemy;
import dungeonmania.entities.movingEntity.Player;

public class PlayerStrategy {

    private int duration;
    private String strategyName;

    public PlayerStrategy(int duration) {
        this.duration = duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public void wearout() {
        duration--;
    }

    public Battle startBattle(Player player, Enemy enemy) {
        return new Battle(player, enemy);
    }
    public List<BattleItem> getBattleItems(List<Item> inventory) {
        List<BattleItem> battleitems = inventory.stream().filter(s->(s instanceof BattleItem)).map(s->(BattleItem) s).collect(Collectors.toList());
        return battleitems;
    }
}
