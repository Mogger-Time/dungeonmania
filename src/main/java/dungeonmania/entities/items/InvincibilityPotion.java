package dungeonmania.entities.items;

import dungeonmania.dtos.EntitiesDto;
import dungeonmania.entities.movingEntity.playerStrategy.InvincibleStrategy;
import dungeonmania.entities.movingEntity.playerStrategy.PlayerStrategy;
import dungeonmania.game.GameRuntimeContext;
import dungeonmania.util.Position;

public class InvincibilityPotion extends Potion {

    public InvincibilityPotion() {
        super();
        setName("invincibility_potion");
    }

    public PlayerStrategy consume() {
        return new InvincibleStrategy(this.getDuration());
    }

    @Override
    public void setupEntity(EntitiesDto entitiesDto, Position position) {
        super.setPosition(position);
        setDuration(GameRuntimeContext.getConfig().getInvincibilityPotionDuration());
    }
}
