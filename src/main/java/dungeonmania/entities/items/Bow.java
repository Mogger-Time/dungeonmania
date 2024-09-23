package dungeonmania.entities.items;

import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;

import dungeonmania.util.Position;

public class Bow extends BattleItem implements Buildable {

    public Bow() {
        super();
        setName("bow");
        setAttack(0);
        setDefence(0);
        setMultiplier(2);
    }

    public boolean isBuildable(List<Item> inventory) {
        List<Item> wood = inventory.stream().filter(s->(s instanceof Wood)).collect(Collectors.toList());
        List<Item> arrow = inventory.stream().filter(s->(s instanceof Arrow)).collect(Collectors.toList());
        if (wood.size() > 0 && arrow.size() > 2) {
            inventory.remove(wood.get(0));
            inventory.removeAll(arrow.subList(0, 3));
            return true;
        }
        return false;
    }

    public static boolean checkBuildable(List<Item> inventory) {
        List<Item> wood = inventory.stream().filter(s->(s instanceof Wood)).collect(Collectors.toList());
        List<Item> arrow = inventory.stream().filter(s->(s instanceof Arrow)).collect(Collectors.toList());
        if (wood.size() > 0 && arrow.size() > 2) {
            return true;
        }
        return false;
    }

    @Override
    public void setupEntity(JSONObject entityConfig, Position position) {
        setDurability(entityConfig.getInt("bow_durability"));
    }
}
