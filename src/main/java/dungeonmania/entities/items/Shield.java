package dungeonmania.entities.items;

import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;

import dungeonmania.util.Position;

public class Shield extends BattleItem implements Buildable {

    public Shield() {
        super();
        setName("shield");
        setAttack(0);
        setMultiplier(1);
    }

    public boolean isBuildable(List<Item> inventory) {
        List<Item> wood = inventory.stream().filter(s->(s instanceof Wood)).collect(Collectors.toList());
        List<Item> treasure = inventory.stream().filter(s->(s instanceof Treasure)).collect(Collectors.toList());
        List<Item> key = inventory.stream().filter(s->(s instanceof Key)).collect(Collectors.toList());
        List<Item> sunstone = inventory.stream().filter(s->(s instanceof SunStone)).collect(Collectors.toList());
        if (wood.size() > 1) {
            if (treasure.size() > 0) {
                inventory.removeAll(wood.subList(0, 2));
                inventory.remove(treasure.get(0));
                return true;
            } 
            else if (key.size() > 0) {
                inventory.removeAll(wood.subList(0, 2));
                inventory.remove(key.get(0));
                return true;
            }
            else if (sunstone.size() > 0) {
                inventory.removeAll(wood.subList(0, 2));
                return true;
            }
        }
        return false;
    }

    public static boolean checkBuildable(List<Item> inventory) {
        List<Item> wood = inventory.stream().filter(s->(s instanceof Wood)).collect(Collectors.toList());
        List<Item> treasure = inventory.stream().filter(s->(s instanceof Treasure)).collect(Collectors.toList());
        List<Item> key = inventory.stream().filter(s->(s instanceof Key)).collect(Collectors.toList());
        if (wood.size() > 1) {
            if (treasure.size() > 0 || key.size() > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setupEntity(JSONObject entityConfig, Position position) {
        setDurability(entityConfig.getInt("shield_durability"));
        setDefence(entityConfig.getInt("shield_defence"));
    }
}
