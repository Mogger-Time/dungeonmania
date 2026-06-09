package dungeonmania.entities.movingEntity;

import dungeonmania.dtos.EntitiesDto;
import dungeonmania.entities.movingEntity.movementStrategy.ClockwiseStrategy;
import dungeonmania.entities.movingEntity.playerStrategy.PlayerStrategy;
import dungeonmania.game.GameRuntimeContext;
import dungeonmania.util.Position;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Spider extends Enemy {

    private int spawnRate;

    public Spider() {
        super();
        setName("spider");
        super.setStrategy(new ClockwiseStrategy(0));
    }

    @Override
    public void setupEntity(EntitiesDto entitiesDto, Position position) {
        super.setPosition(position);
        super.setHealth(GameRuntimeContext.getConfig().getSpiderHealth());
        super.setDamage(GameRuntimeContext.getConfig().getSpiderAttack());
        this.setSpawnRate(GameRuntimeContext.getConfig().getSpiderSpawnRate());
    }

    public void alert(PlayerStrategy strat) {

    }
}