package dungeonmania.entities.items;

import dungeonmania.dtos.EntitiesDto;
import dungeonmania.entities.movingEntity.playerStrategy.InvisibleStrategy;
import dungeonmania.entities.movingEntity.playerStrategy.PlayerStrategy;
import dungeonmania.game.GameLauncher;
import dungeonmania.util.Position;

public class InvisibilityPotion extends Potion {

    public InvisibilityPotion() {
        super();
        setName("invisibility_potion");
    }

    public PlayerStrategy consume() {
        return new InvisibleStrategy(this.getDuration());
    }

    @Override
    public void setupEntity(EntitiesDto entitiesDto, Position position) {
        super.setPosition(position);
        setDuration(GameLauncher.getConfig().getInvisibilityPotionDuration());
    }
}
