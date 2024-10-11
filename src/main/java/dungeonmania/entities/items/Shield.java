package dungeonmania.entities.items;

import dungeonmania.dtos.EntitiesDto;
import dungeonmania.game.GameLauncher;
import dungeonmania.util.Position;

import java.util.List;
import java.util.stream.Collectors;

public class Shield extends BattleItem implements Buildable {

    public Shield() {
        super();
        setName("shield");
        setAttack(0);
        setMultiplier(1);
    }

    public static boolean checkBuildable(List<Item> inventory) {
        List<Item> wood = inventory.stream().filter(s -> (s instanceof Wood)).collect(Collectors.toList());
        List<Item> treasure = inventory.stream().filter(s -> (s instanceof Treasure)).collect(Collectors.toList());
        List<Item> key = inventory.stream().filter(s -> (s instanceof Key)).collect(Collectors.toList());
        if (wood.size() > 1) {
            return !treasure.isEmpty() || !key.isEmpty();
        }
        return false;
    }

    public boolean isBuildable(List<Item> inventory) {
        List<Item> wood = inventory.stream().filter(s -> (s instanceof Wood)).collect(Collectors.toList());
        List<Item> treasure = inventory.stream().filter(s -> (s instanceof Treasure)).collect(Collectors.toList());
        List<Item> key = inventory.stream().filter(s -> (s instanceof Key)).collect(Collectors.toList());
        List<Item> sunstone = inventory.stream().filter(s -> (s instanceof SunStone)).collect(Collectors.toList());
        if (wood.size() > 1) {
            if (!treasure.isEmpty()) {
                inventory.removeAll(wood.subList(0, 2));
                inventory.remove(treasure.get(0));
                return true;
            } else if (!key.isEmpty()) {
                inventory.removeAll(wood.subList(0, 2));
                inventory.remove(key.get(0));
                return true;
            } else if (!sunstone.isEmpty()) {
                inventory.removeAll(wood.subList(0, 2));
                return true;
            }
        }
        return false;
    }

    @Override
    public void setupEntity(EntitiesDto entitiesDto, Position position) {
        setDurability(GameLauncher.getConfig().getShieldDurability());
        setDefence(GameLauncher.getConfig().getShieldDefence());
    }
}
