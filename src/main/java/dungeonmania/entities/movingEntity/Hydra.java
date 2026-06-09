package dungeonmania.entities.movingEntity;

import dungeonmania.dtos.EntitiesDto;
import dungeonmania.entities.movingEntity.movementStrategy.RandomStrategy;
import dungeonmania.game.GameRuntimeContext;
import dungeonmania.util.Position;
import dungeonmania.util.RandomProvider;

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
        this.healthIncrease = GameRuntimeContext.getConfig().getHydraHealthIncreaseAmount();
        this.healthIncreaseRate = GameRuntimeContext.getConfig().getHydraHealthIncreaseRate();
        super.setDamage(GameRuntimeContext.getConfig().getHydraAttack());
        super.setHealth(GameRuntimeContext.getConfig().getHydraHealth());
        super.setPosition(position);
    }

    @Override
    public double takeDamage(double damage) {
        if (RandomProvider.nextDouble() <= healthIncreaseRate) {
            return super.takeDamage(-healthIncrease);
        } else {
            return super.takeDamage(damage);
        }
    }
}
