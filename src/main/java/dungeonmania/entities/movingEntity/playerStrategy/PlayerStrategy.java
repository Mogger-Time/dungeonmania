package dungeonmania.entities.movingEntity.playerStrategy;

import dungeonmania.Battle;
import dungeonmania.entities.items.BattleItem;
import dungeonmania.entities.items.Item;
import dungeonmania.entities.movingEntity.Enemy;
import dungeonmania.entities.movingEntity.Player;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class PlayerStrategy {

    private int duration;
    private String strategyName;

    public PlayerStrategy(int duration) {
        this.duration = duration;
    }

    public void wearout() {
        duration--;
    }

    public Battle startBattle(Player player, Enemy enemy) {
        return new Battle(player, enemy);
    }

    public List<BattleItem> getBattleItems(List<Item> inventory) {
        return inventory.stream().filter(s -> (s instanceof BattleItem)).map(s -> (BattleItem) s).collect(Collectors.toList());
    }
}
