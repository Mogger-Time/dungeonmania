package dungeonmania;

import dungeonmania.entities.items.BattleItem;
import dungeonmania.entities.items.Item;
import dungeonmania.entities.movingEntity.Enemy;
import dungeonmania.entities.movingEntity.Player;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.RoundResponse;

import java.util.List;
import java.util.ArrayList;

public class Battle {
    private transient Enemy enemy;
    private transient Player player;
    private double initialphealth;
    private double initialehealth;
    private List<Double> playerhealthlosses = new ArrayList<Double>();
    private List<Double> enemyhealthlosses = new ArrayList<Double>();
    private List<BattleItem> itemsused = new ArrayList<BattleItem>();
    private int numrounds = 0;

    public Battle(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
        this.initialphealth = this.player.getHealth();
        this.initialehealth = this.enemy.getHealth();
        getItemsUsed();
        while (enemy.getHealth() > 0 && player.getHealth() > 0) {
            addRound();
        }
    }

    public void getItemsUsed() {
        List<BattleItem> battleitems = player.getBattleItems();
        List<String> useditems = new ArrayList<String>();
        for (BattleItem battleitem: battleitems) {
            if (!useditems.contains(battleitem.getClass().getSimpleName())) {
                itemsused.add(battleitem);
                useditems.add(battleitem.getClass().getSimpleName());
                battleitem.reduceDurability();
                if (battleitem.getDurability() == 0) {
                    player.removeItem(battleitem);
                }
            }
        }
    }

    public void addRound() {
        double defence = 0;
        double attack = 0;
        double multiplier = 1;
        for (BattleItem battleitem: itemsused) {
            attack+=battleitem.getAttack();
            defence+=battleitem.getDefence();
            multiplier = multiplier * battleitem.getMultiplier();
        }
        attack+=player.getDamage();
        attack+=(player.getAllyattack() * player.getAlly().size());
        defence+=(player.getAllydefence() * player.getAlly().size());
        attack = attack * multiplier;
        double enemyhealthloss = attack / 5;
        double playerhealthloss = (enemy.getDamage() - defence) / 10;
        if (playerhealthloss < 0) {
            playerhealthloss = 0;
        }
        playerhealthlosses.add(player.takeDamage(playerhealthloss));
        enemyhealthlosses.add(enemy.takeDamage(enemyhealthloss));
        numrounds++;
    }

    public BattleResponse getResponse() {
        List<RoundResponse> roundsresponse = new ArrayList<RoundResponse>();
        for (int i = 0; i < numrounds; i++) {
            roundsresponse.add(getRResponse(i));
        }
        return new BattleResponse(enemy.getClass().getSimpleName(), roundsresponse, initialphealth, initialehealth);
    }

    public RoundResponse getRResponse(int i) {
        List<ItemResponse> itemsresponse = new ArrayList<ItemResponse>();
        for (Item item : itemsused) {
            itemsresponse.add(item.getItemResponse());
        }
        return new RoundResponse(playerhealthlosses.get(i), enemyhealthlosses.get(i), itemsresponse);
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy loadedEnemy) {
        enemy = loadedEnemy;
    }

}
