package dungeonmania.entities.items;
import org.json.JSONObject;

import dungeonmania.entities.movingEntity.playerStrategy.InvisibleStrategy;
import dungeonmania.entities.movingEntity.playerStrategy.PlayerStrategy;
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
    public void setupEntity(JSONObject entityConfig, Position position) {
        super.setPosition(position);
        setDuration(entityConfig.getInt("invisibility_potion_duration"));
    }
}
