package dungeonmania.entities.items;

import dungeonmania.dtos.EntitiesDto;
import dungeonmania.game.GameLauncher;
import dungeonmania.util.Position;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Sceptre extends Item implements Buildable {

    private int mindControlDuration;

    public Sceptre() {
        super();
        setName("sceptre");
    }

    public static boolean checkBuildable(List<Item> inventory) {
        List<Item> wood = inventory.stream().filter(s -> (s instanceof Wood)).collect(Collectors.toList());
        List<Item> arrows = inventory.stream().filter(s -> (s instanceof Arrow)).collect(Collectors.toList());
        List<Item> treasure = inventory.stream().filter(s -> (s instanceof Treasure)).collect(Collectors.toList());
        List<Item> key = inventory.stream().filter(s -> (s instanceof Key)).collect(Collectors.toList());
        List<Item> sunstone = inventory.stream().filter(s -> (s instanceof SunStone)).collect(Collectors.toList());

        List<Item> sceptre = inventory.stream().filter(s -> (s instanceof Sceptre)).collect(Collectors.toList());

        if (sceptre.isEmpty()) {
            if ((!wood.isEmpty() || arrows.size() > 1) && !sunstone.isEmpty()) {
                return !treasure.isEmpty() || !key.isEmpty() || sunstone.size() > 1;
            }
        }

        return false;
    }

    @Override
    public void setupEntity(EntitiesDto entitiesDto, Position position) {
        super.setPosition(position);
        this.mindControlDuration = GameLauncher.getConfig().getMindControlDuration();
    }

    @Override
    public boolean isBuildable(List<Item> inventory) {
        List<Item> wood = inventory.stream().filter(s -> (s instanceof Wood)).collect(Collectors.toList());
        List<Item> arrows = inventory.stream().filter(s -> (s instanceof Arrow)).collect(Collectors.toList());
        List<Item> treasure = inventory.stream().filter(s -> (s instanceof Treasure)).collect(Collectors.toList());
        List<Item> key = inventory.stream().filter(s -> (s instanceof Key)).collect(Collectors.toList());
        List<Item> sunstone = inventory.stream().filter(s -> (s instanceof SunStone)).collect(Collectors.toList());

        if (!wood.isEmpty() && !sunstone.isEmpty()) {
            if (!treasure.isEmpty()) {
                inventory.remove(wood.get(0));
                inventory.remove(treasure.get(0));
                inventory.remove(sunstone.get(0));
                return true;
            } else if (!key.isEmpty()) {
                inventory.remove(wood.get(0));
                inventory.remove(key.get(0));
                inventory.remove(sunstone.get(0));
                return true;
            } else if (sunstone.size() > 1) {
                inventory.remove(wood.get(0));
                inventory.remove(sunstone.get(0));
                return true;
            }
        } else if (arrows.size() > 1 && !sunstone.isEmpty()) {
            if (!treasure.isEmpty()) {
                inventory.removeAll(arrows.subList(0, 2));
                inventory.remove(treasure.get(0));
                inventory.remove(sunstone.get(0));
                return true;
            } else if (!key.isEmpty()) {
                inventory.removeAll(arrows.subList(0, 2));
                inventory.remove(key.get(0));
                inventory.remove(sunstone.get(0));
                return true;
            } else if (sunstone.size() > 1) {
                inventory.removeAll(arrows.subList(0, 2));
                inventory.remove(sunstone.get(0));
                return true;
            }
        }

        return false;
    }
}
