package dungeonmania.entities.movingEntity;

import org.json.JSONObject;

import dungeonmania.entities.movingEntity.movementStrategy.ClockwiseStrategy;
import dungeonmania.entities.movingEntity.playerStrategy.PlayerStrategy;
import dungeonmania.util.Position;

public class Spider extends Enemy {
    
    private int spawnRate;

    public Spider() {
        super();
        setName("spider");
        super.setStrategy(new ClockwiseStrategy(0));
    }

    @Override
    public void setupEntity(JSONObject entityConfig, Position position) {
        super.setPosition(position);
        super.setHealth(entityConfig.getDouble("spider_health"));
        super.setDamage(entityConfig.getDouble("spider_attack"));
        this.setSpawnRate(entityConfig.getInt("spider_spawn_rate"));
    }

    public void setSpawnRate(int spawnRate) {
        this.spawnRate = spawnRate;
    }

    public int getSpawnRate() {
        return spawnRate;
    }

    public void alert(PlayerStrategy strat) {

    }
}