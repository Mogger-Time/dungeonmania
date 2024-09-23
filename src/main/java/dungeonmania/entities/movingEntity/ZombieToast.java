package dungeonmania.entities.movingEntity;

import org.json.JSONObject;

import dungeonmania.entities.movingEntity.movementStrategy.FearStrategy;
import dungeonmania.entities.movingEntity.movementStrategy.RandomStrategy;
import dungeonmania.entities.movingEntity.playerStrategy.InvincibleStrategy;
import dungeonmania.entities.movingEntity.playerStrategy.InvisibleStrategy;
import dungeonmania.entities.movingEntity.playerStrategy.NormalStrategy;
import dungeonmania.entities.movingEntity.playerStrategy.PlayerStrategy;
import dungeonmania.util.Position;

public class ZombieToast extends Enemy {
    
    private int spawnRate;

    public ZombieToast() {
        super();
        setName("zombie_toast");
        super.setStrategy(new RandomStrategy());
    }

    public int getSpawnRate() {
        return spawnRate;
    }

    @Override
    public void setupEntity(JSONObject entityConfig, Position position) {
        super.setPosition(position);
        super.setHealth(entityConfig.getDouble("zombie_health"));
        super.setDamage(entityConfig.getDouble("zombie_attack"));
        this.spawnRate = entityConfig.getInt("zombie_spawn_rate");
    }

    public void alert(PlayerStrategy strat) {
        if (strat instanceof NormalStrategy) {
            setStrategy(new RandomStrategy());
        } else if (strat instanceof InvincibleStrategy) {
            setStrategy(new FearStrategy());
        } else if (strat instanceof InvisibleStrategy) {
            setStrategy(new RandomStrategy());
        }
    }
}
