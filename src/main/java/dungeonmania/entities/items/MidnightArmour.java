package dungeonmania.entities.items;

import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntity.ZombieToast;
import dungeonmania.game.Game;
import dungeonmania.game.GameLauncher;

import dungeonmania.util.Position;

public class MidnightArmour extends BattleItem implements Buildable {
        
    public MidnightArmour() {
        super();
        setName("midnight_armour");
        setMultiplier(1);
        setDurability(Integer.MAX_VALUE);
    }
    
    @Override
    public void setupEntity(JSONObject entityConfig, Position position) {
        super.setPosition(position);
        super.setAttack(entityConfig.getInt("midnight_armour_attack"));
        super.setDefence(entityConfig.getInt("midnight_armour_defence"));
    }

    public boolean isBuildable(List<Item> inventory) {
        List<Item> sword = inventory.stream().filter(s->(s instanceof Sword)).collect(Collectors.toList());
        List<Item> sunstone = inventory.stream().filter(s->(s instanceof SunStone)).collect(Collectors.toList());

        Game activeGame = GameLauncher.getActiveGame();
        List<Entity> entities = activeGame.getEntities();
        List<Entity> zombie = entities.stream().filter(s->(s instanceof ZombieToast)).collect(Collectors.toList());

        if (zombie.size() == 0) {
            if (sword.size() >= 1 && sunstone.size() >= 1) {
                inventory.remove(sword.get(0));
                inventory.remove(sunstone.get(0));
                return true;
            }
        }
        
        return false;
    }

    public static boolean checkBuildable(List<Item> inventory) {
        List<Item> sword = inventory.stream().filter(s->(s instanceof Sword)).collect(Collectors.toList());
        List<Item> sunstone = inventory.stream().filter(s->(s instanceof SunStone)).collect(Collectors.toList());

        Game activeGame = GameLauncher.getActiveGame();
        List<Entity> entities = activeGame.getEntities();
        List<Entity> zombie = entities.stream().filter(s->(s instanceof ZombieToast)).collect(Collectors.toList());

        if (zombie.size() == 0) {
            if (sword.size() >= 1 && sunstone.size() >= 1) {
                return true;
            }
        }
        return false;
    }
}
