package dungeonmania.entities;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.json.JSONObject;

import dungeonmania.game.GameLauncher;
import dungeonmania.util.Position;
import dungeonmania.entities.items.Arrow;
import dungeonmania.entities.items.Bomb;
import dungeonmania.entities.items.InvincibilityPotion;
import dungeonmania.entities.items.InvisibilityPotion;
import dungeonmania.entities.items.Key;
import dungeonmania.entities.items.Sceptre;
import dungeonmania.entities.items.SunStone;
import dungeonmania.entities.items.Sword;
import dungeonmania.entities.items.TimeTurner;
import dungeonmania.entities.items.Treasure;
import dungeonmania.entities.items.Wood;
import dungeonmania.entities.movingEntity.Assassin;
import dungeonmania.entities.movingEntity.Hydra;
import dungeonmania.entities.movingEntity.Mercenary;
import dungeonmania.entities.movingEntity.Player;
import dungeonmania.entities.movingEntity.Spider;
import dungeonmania.entities.movingEntity.ZombieToast;
import dungeonmania.entities.staticEntity.Boulder;
import dungeonmania.entities.staticEntity.Door;
import dungeonmania.entities.staticEntity.Wall;
import dungeonmania.entities.staticEntity.ZombieToastSpawner;
import dungeonmania.entities.staticEntity.logicalEntity.LightBulb;
import dungeonmania.entities.staticEntity.logicalEntity.SwitchDoor;
import dungeonmania.entities.staticEntity.logicalEntity.Wire;
import dungeonmania.entities.staticEntity.Exit;
import dungeonmania.entities.staticEntity.FloorSwitch;
import dungeonmania.entities.staticEntity.Portal;
import dungeonmania.entities.staticEntity.SwampTile;

public class EntityFactory {
    private static final Map<String, Supplier<Entity>> entityFactory;

    static {
        final Map<String, Supplier<Entity>> entities = new HashMap<>();

        entities.put("player", Player::new);

        // Static entities
        entities.put("wall", Wall::new);
        entities.put("exit", Exit::new);
        entities.put("boulder", Boulder::new);
        entities.put("switch", FloorSwitch::new);
        entities.put("door", Door::new);
        entities.put("portal", Portal::new);
        entities.put("swamp_tile", SwampTile::new);
        entities.put("light_bulb_off", LightBulb::new);
        entities.put("wire", Wire::new);
        entities.put("switch_door", SwitchDoor::new);
        // entities.put("time_travelling_portal", TimeTravellingPortal::new);
        entities.put("zombie_toast_spawner", ZombieToastSpawner::new);

        // Moving entities
        entities.put("spider", Spider::new);
        entities.put("zombie_toast", ZombieToast::new);
        entities.put("mercenary", Mercenary::new);
        entities.put("assassin", Assassin::new);
        entities.put("hydra", Hydra::new);

        // Items
        entities.put("treasure", Treasure::new);
        entities.put("key", Key::new);
        entities.put("invincibility_potion", InvincibilityPotion::new);
        entities.put("invisibility_potion", InvisibilityPotion::new);
        entities.put("wood", Wood::new);
        entities.put("arrow", Arrow::new);
        entities.put("bomb", Bomb::new);
        entities.put("sword", Sword::new);
        entities.put("sun_stone", SunStone::new);
        entities.put("time_turner", TimeTurner::new);

        entityFactory = Collections.unmodifiableMap(entities);
    }

    public static Entity createEntity(JSONObject entityJSON) {
        String entityType = entityJSON.getString("type");
        Position entityPosition = new Position(entityJSON.getInt("x"), entityJSON.getInt("y"));
        Supplier<Entity> entitySupplier = entityFactory.get(entityType);

        Entity entity = entitySupplier.get();
        if (entity instanceof Door || 
            entity instanceof Portal || 
            entity instanceof SwampTile ||
            entity instanceof Key ||
            entity instanceof LightBulb ||
            entity instanceof SwitchDoor) {
            entity.setupEntity(entityJSON, entityPosition);
        } 
        else {
            entity.setupEntity(GameLauncher.getConfig(), entityPosition);
        }
        if (entity instanceof Bomb) {
            if (entityJSON.has("logic")) {
                ((Bomb) entity).setLogic(entityJSON.getString("logic"));
            }
        }
        
        return entity;
    }
}