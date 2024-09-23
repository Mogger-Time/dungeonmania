package dungeonmania.entities.items;

import org.json.JSONObject;

import dungeonmania.entities.movingEntity.playerStrategy.InvincibleStrategy;
import dungeonmania.entities.movingEntity.playerStrategy.PlayerStrategy;
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
    public void setupEntity(JSONObject entityConfig, Position position) {
        super.setPosition(position);
        setDuration(entityConfig.getInt("invincibility_potion_duration"));
    }
}
