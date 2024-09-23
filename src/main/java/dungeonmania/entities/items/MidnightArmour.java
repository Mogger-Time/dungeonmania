package dungeonmania.entities.items;

import dungeonmania.dtos.EntitiesDto;
import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntity.ZombieToast;
import dungeonmania.game.Game;
import dungeonmania.game.GameLauncher;
import dungeonmania.util.Position;

import java.util.List;
import java.util.stream.Collectors;

public class MidnightArmour extends BattleItem implements Buildable {

    public MidnightArmour() {
        super();
        setName("midnight_armour");
        setMultiplier(1);
        setDurability(Integer.MAX_VALUE);
    }

    public static boolean checkBuildable(List<Item> inventory) {
        List<Item> sword = inventory.stream().filter(s -> (s instanceof Sword)).collect(Collectors.toList());
        List<Item> sunstone = inventory.stream().filter(s -> (s instanceof SunStone)).collect(Collectors.toList());

        Game activeGame = GameLauncher.getActiveGame();
        List<Entity> entities = activeGame.getEntities();
        List<Entity> zombie = entities.stream().filter(s -> (s instanceof ZombieToast)).collect(Collectors.toList());

        if (zombie.isEmpty()) {
            return !sword.isEmpty() && !sunstone.isEmpty();
        }
        return false;
    }

    @Override
    public void setupEntity(EntitiesDto entitiesDto, Position position) {
        super.setPosition(position);
        super.setAttack(GameLauncher.getConfig().getMidnightArmourAttack());
        super.setDefence(GameLauncher.getConfig().getMidnightArmourDefence());
    }

    public boolean isBuildable(List<Item> inventory) {
        List<Item> sword = inventory.stream().filter(s -> (s instanceof Sword)).collect(Collectors.toList());
        List<Item> sunstone = inventory.stream().filter(s -> (s instanceof SunStone)).collect(Collectors.toList());

        Game activeGame = GameLauncher.getActiveGame();
        List<Entity> entities = activeGame.getEntities();
        List<Entity> zombie = entities.stream().filter(s -> (s instanceof ZombieToast)).collect(Collectors.toList());

        if (zombie.isEmpty()) {
            if (!sword.isEmpty() && !sunstone.isEmpty()) {
                inventory.remove(sword.get(0));
                inventory.remove(sunstone.get(0));
                return true;
            }
        }

        return false;
    }
}
