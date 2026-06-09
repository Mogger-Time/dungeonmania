package dungeonmania.entities.movingEntity;

import dungeonmania.dtos.EntitiesDto;
import dungeonmania.entities.movingEntity.movementStrategy.FearStrategy;
import dungeonmania.entities.movingEntity.movementStrategy.RandomStrategy;
import dungeonmania.entities.movingEntity.playerStrategy.InvincibleStrategy;
import dungeonmania.entities.movingEntity.playerStrategy.InvisibleStrategy;
import dungeonmania.entities.movingEntity.playerStrategy.NormalStrategy;
import dungeonmania.entities.movingEntity.playerStrategy.PlayerStrategy;
import dungeonmania.game.GameRuntimeContext;
import dungeonmania.util.Position;
import lombok.Getter;

@Getter
public class ZombieToast extends Enemy {

    private int spawnRate;

    public ZombieToast() {
        super();
        setName("zombie_toast");
        super.setStrategy(new RandomStrategy());
    }

    @Override
    public void setupEntity(EntitiesDto entitiesDto, Position position) {
        super.setPosition(position);
        super.setHealth(GameRuntimeContext.getConfig().getZombieHealth());
        super.setDamage(GameRuntimeContext.getConfig().getZombieAttack());
        this.spawnRate = GameRuntimeContext.getConfig().getZombieSpawnRate();
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
