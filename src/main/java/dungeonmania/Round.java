package dungeonmania;

import dungeonmania.entities.items.BattleItem;
import dungeonmania.entities.items.Item;
import dungeonmania.entities.movingEntity.Enemy;
import dungeonmania.entities.movingEntity.Player;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.RoundResponse;

import java.util.ArrayList;
import java.util.List;

public class Round {
    private double playerhealthloss;
    private double enemyhealthloss;
    private transient List<Item> itemsused = new ArrayList<>();

    public Round(Player player, Enemy enemy) {
        List<BattleItem> battleitems = player.getBattleItems();
        List<String> useditems = new ArrayList<>();
        double defence = 0;
        double attack = 0;
        double multiplier = 1;
        for (BattleItem battleitem : battleitems) {
            if (!useditems.contains(battleitem.getClass().getSimpleName())) {
                itemsused.add(battleitem);
                useditems.add(battleitem.getClass().getSimpleName());
                attack += battleitem.getAttack();
                defence += battleitem.getDefence();
                multiplier = multiplier * battleitem.getMultiplier();
                battleitem.reduceDurability();
                if (battleitem.getDurability() == 0) {
                    player.removeItem(battleitem);
                }
            }
        }
        attack += player.getDamage();
        attack += (player.getAllyattack() * player.getAlly().size());
        defence += (player.getAllydefence() * player.getAlly().size());
        attack = attack * multiplier;
        enemyhealthloss = attack / 5;
        playerhealthloss = (enemy.getDamage() - defence) / 10;
        if (playerhealthloss < 0) {
            playerhealthloss = 0;
        }
        playerhealthloss = player.takeDamage(playerhealthloss);
        enemyhealthloss = enemy.takeDamage(enemyhealthloss);
    }

    public RoundResponse getResponse() {
        List<ItemResponse> itemsresponse = new ArrayList<>();
        for (Item item : itemsused) {
            itemsresponse.add(item.getItemResponse());
        }
        return new RoundResponse(playerhealthloss, enemyhealthloss, itemsresponse);
    }

    public void setItemsUsed(List<Item> loadedItems) {
        itemsused = loadedItems;
    }
}
