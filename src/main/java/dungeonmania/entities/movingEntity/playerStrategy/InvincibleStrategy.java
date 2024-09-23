package dungeonmania.entities.movingEntity.playerStrategy;

import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.entities.items.BattleItem;
import dungeonmania.entities.items.Item;
import dungeonmania.entities.items.UsedIP;

public class InvincibleStrategy extends PlayerStrategy {

    public InvincibleStrategy(int duration) {
        super(duration);
        setStrategyName("invincible");
    }

    @Override
    public List<BattleItem> getBattleItems(List<Item> inventory) {
        List<BattleItem> battleitems = inventory.stream().filter(s->(s instanceof BattleItem)).map(s->(BattleItem) s).collect(Collectors.toList());
        battleitems.add(new UsedIP());
        return battleitems;
    }
}
