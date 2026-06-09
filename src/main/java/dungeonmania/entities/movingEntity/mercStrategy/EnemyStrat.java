package dungeonmania.entities.movingEntity.mercStrategy;

import dungeonmania.entities.movingEntity.Mercenary;
import dungeonmania.entities.movingEntity.Player;
import dungeonmania.entities.movingEntity.movementStrategy.FearStrategy;
import dungeonmania.entities.movingEntity.movementStrategy.FollowStrategy;
import dungeonmania.entities.movingEntity.movementStrategy.RandomStrategy;
import dungeonmania.entities.movingEntity.playerStrategy.InvincibleStrategy;
import dungeonmania.entities.movingEntity.playerStrategy.InvisibleStrategy;
import dungeonmania.entities.movingEntity.playerStrategy.NormalStrategy;
import dungeonmania.entities.movingEntity.playerStrategy.PlayerStrategy;

public class EnemyStrat extends MercStrat {
    public EnemyStrat(int duration) {
        super(duration);
        setStrategyName("enemy");
    }

    @Override
    public void alert(PlayerStrategy strat, Mercenary mercenary) {
        if (strat instanceof InvisibleStrategy) {
            mercenary.setStrategy(new RandomStrategy());
        } else if (strat instanceof NormalStrategy) {
            mercenary.setStrategy(new FollowStrategy());
        } else if (strat instanceof InvincibleStrategy) {
            mercenary.setStrategy(new FearStrategy());
        }
    }

    @Override
    public boolean interact(Player player, Mercenary mercenary) {
        player.startBattle(mercenary);
        return mercenary.getHealth() <= 0;
    }

    @Override
    public boolean isAlly() {
        return false;
    }

    @Override
    public boolean gotoPrev(Player player, Mercenary mercenary) {
        return false;
    }
}