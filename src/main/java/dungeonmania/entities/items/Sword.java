package dungeonmania.entities.items;

import dungeonmania.dtos.EntitiesDto;
import dungeonmania.game.GameLauncher;
import dungeonmania.util.Position;

public class Sword extends BattleItem {

    public Sword() {
        super();
        setName("sword");
        setDefence(0);
        setMultiplier(1);
    }

    @Override
    public void setupEntity(EntitiesDto entitiesDto, Position position) {
        super.setPosition(position);
        setDurability(GameLauncher.getConfig().getSwordDurability());
        setAttack(GameLauncher.getConfig().getSwordAttack());
    }
}
