package dungeonmania.entities.items;

import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;

import dungeonmania.util.Position;

public class Sceptre extends Item implements Buildable {

    private int mindControlDuration;
    
    public Sceptre() {
        super();
        setName("sceptre");
    }

    public int getMindControlDuration() {
        return mindControlDuration;
    }

    @Override
    public void setupEntity(JSONObject entityConfig, Position position) {
        super.setPosition(position);
        this.mindControlDuration = entityConfig.getInt("mind_control_duration");
    }

    @Override
    public boolean isBuildable(List<Item> inventory) {
        List<Item> wood = inventory.stream().filter(s->(s instanceof Wood)).collect(Collectors.toList());
        List<Item> arrows = inventory.stream().filter(s->(s instanceof Arrow)).collect(Collectors.toList());
        List<Item> treasure = inventory.stream().filter(s->(s instanceof Treasure)).collect(Collectors.toList());
        List<Item> key = inventory.stream().filter(s->(s instanceof Key)).collect(Collectors.toList());
        List<Item> sunstone = inventory.stream().filter(s->(s instanceof SunStone)).collect(Collectors.toList());
        
        if (wood.size() > 0 && sunstone.size() > 0) {
            if (treasure.size() > 0) {
                inventory.remove(wood.get(0));
                inventory.remove(treasure.get(0));
                inventory.remove(sunstone.get(0));
                return true;
            } 
            else if (key.size() > 0) {
                inventory.remove(wood.get(0));
                inventory.remove(key.get(0));
                inventory.remove(sunstone.get(0));
                return true;
            }
            else if (sunstone.size() > 1) {
                inventory.remove(wood.get(0));
                inventory.remove(sunstone.get(0));
                return true;
            }
        }
        else if (arrows.size() > 1 && sunstone.size() > 0) {
            if (treasure.size() > 0) {
                inventory.removeAll(arrows.subList(0,2));
                inventory.remove(treasure.get(0));
                inventory.remove(sunstone.get(0));
                return true;
            } 
            else if (key.size() > 0) {
                inventory.removeAll(arrows.subList(0,2));
                inventory.remove(key.get(0));
                inventory.remove(sunstone.get(0));
                return true;
            }
            else if (sunstone.size() > 1) {
                inventory.removeAll(arrows.subList(0,2));
                inventory.remove(sunstone.get(0));
                return true;
            }
        }

        return false;
    }

    public static boolean checkBuildable(List<Item> inventory) {
        List<Item> wood = inventory.stream().filter(s->(s instanceof Wood)).collect(Collectors.toList());
        List<Item> arrows = inventory.stream().filter(s->(s instanceof Arrow)).collect(Collectors.toList());
        List<Item> treasure = inventory.stream().filter(s->(s instanceof Treasure)).collect(Collectors.toList());
        List<Item> key = inventory.stream().filter(s->(s instanceof Key)).collect(Collectors.toList());
        List<Item> sunstone = inventory.stream().filter(s->(s instanceof SunStone)).collect(Collectors.toList());

        List<Item> sceptre = inventory.stream().filter(s->(s instanceof Sceptre)).collect(Collectors.toList());
        
        if (sceptre.size() == 0) {
            if ((wood.size() > 0 || arrows.size() > 1) && sunstone.size() > 0) {
                if (treasure.size() > 0 || key.size() > 0 || sunstone.size() > 1) {
                    return true;
                } 
            }
        }
        
        return false;
    }
}
