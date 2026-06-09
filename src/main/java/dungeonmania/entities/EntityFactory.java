package dungeonmania.entities;

import dungeonmania.dtos.EntitiesDto;
import dungeonmania.entities.items.*;
import dungeonmania.entities.movingEntity.*;
import dungeonmania.entities.staticEntity.*;
import dungeonmania.entities.staticEntity.logicalEntity.LightBulb;
import dungeonmania.entities.staticEntity.logicalEntity.SwitchDoor;
import dungeonmania.entities.staticEntity.logicalEntity.Wire;
import dungeonmania.util.Position;

import java.util.Map;
import java.util.function.Supplier;

public class EntityFactory {
    private static final Map<String, Supplier<Entity>> entityFactory;

    static {

        entityFactory =
                Map.ofEntries(Map.entry("player", Player::new),

                        // Static entities
                        Map.entry("wall", Wall::new),
                        Map.entry("exit", Exit::new),
                        Map.entry("boulder", Boulder::new),
                        Map.entry("switch", FloorSwitch::new),
                        Map.entry("door", Door::new),
                        Map.entry("portal", Portal::new),
                        Map.entry("swamp_tile", SwampTile::new),
                        Map.entry("light_bulb_off", LightBulb::new),
                        Map.entry("wire", Wire::new),
                        Map.entry("switch_door", SwitchDoor::new),

                        // entities.put("time_travelling_portal", TimeTravellingPortal::new);
                        Map.entry("zombie_toast_spawner", ZombieToastSpawner::new),

                        // Moving entities
                        Map.entry("spider", Spider::new),
                        Map.entry("zombie_toast", ZombieToast::new),
                        Map.entry("mercenary", Mercenary::new),
                        Map.entry("assassin", Assassin::new),
                        Map.entry("hydra", Hydra::new),

                        // Items
                        Map.entry("treasure", Treasure::new),
                        Map.entry("key", Key::new),
                        Map.entry("invincibility_potion", InvincibilityPotion::new),
                        Map.entry("invisibility_potion", InvisibilityPotion::new),
                        Map.entry("wood", Wood::new),
                        Map.entry("arrow", Arrow::new),
                        Map.entry("bomb", Bomb::new),
                        Map.entry("sword", Sword::new),
                        Map.entry("sun_stone", SunStone::new),
                        Map.entry("time_turner", TimeTurner::new));
    }

    public static Entity createEntity(EntitiesDto entitiesDto) {
        Position entityPosition = new Position(entitiesDto.getX(), entitiesDto.getY());
        Supplier<Entity> entitySupplier = entityFactory.get(entitiesDto.getType());

        Entity entity = entitySupplier.get();

        entity.setupEntity(entitiesDto, entityPosition);

        if (entity instanceof Bomb) {
            if (entitiesDto.getLogic() != null) {
                ((Bomb) entity).setLogic(entitiesDto.getLogic());
            }
        }

        return entity;
    }
}