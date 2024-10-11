package dungeonmania.entities.movingEntity;

import dungeonmania.dtos.EntitiesDto;
import dungeonmania.entities.movingEntity.movementStrategy.RandomStrategy;
import dungeonmania.game.GameLauncher;
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
    public void setupEntity(EntitiesDto entitiesDto, Position position) {
        this.healthIncrease = GameLauncher.getConfig().getHydraHealthIncreaseAmount();
        this.healthIncreaseRate = GameLauncher.getConfig().getHydraHealthIncreaseRate();
        super.setDamage(GameLauncher.getConfig().getHydraAttack());
        super.setHealth(GameLauncher.getConfig().getHydraHealth());
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
