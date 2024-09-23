package dungeonmania.entities.movingEntity;

import org.json.JSONObject;

import dungeonmania.entities.movingEntity.movementStrategy.RandomStrategy;
import dungeonmania.util.Position;

public class Hydra extends ZombieToast {

    private double healthIncrease;
    private double healthIncreaseRate;
    
    public Hydra() {
        super();
        super.setName("hydra");
        super.setStrategy(new RandomStrategy());
    }

    @Override
    public void setupEntity(JSONObject entityConfig, Position position) {
        this.healthIncrease = entityConfig.getDouble("hydra_health_increase_amount");
        this.healthIncreaseRate = entityConfig.getDouble("hydra_health_increase_rate");
        super.setDamage(entityConfig.getDouble("hydra_attack"));
        super.setHealth(entityConfig.getDouble("hydra_health"));
        super.setPosition(position);
    }
    @Override
    public double takeDamage(double damage) {
        if (Math.random() <= healthIncreaseRate) {
            return super.takeDamage(-healthIncrease);
        } else {
            return super.takeDamage(damage);
        }
    }
}
