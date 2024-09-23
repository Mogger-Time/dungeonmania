package dungeonmania.entities.items;

import org.json.JSONObject;

import dungeonmania.util.Position;

public class Sword extends BattleItem {

    public Sword() {
        super();
        setName("sword");
        setDefence(0);
        setMultiplier(1);
    }

    @Override
    public void setupEntity(JSONObject entityConfig, Position position) {
        super.setPosition(position);
        setDurability(entityConfig.getInt("sword_durability"));
        setAttack(entityConfig.getInt("sword_attack"));
    }
}
