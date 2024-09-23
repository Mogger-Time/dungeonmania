package dungeonmania.entities.movingEntity.mercStrategy;

import dungeonmania.entities.movingEntity.Mercenary;
import dungeonmania.entities.movingEntity.Player;
import dungeonmania.entities.movingEntity.movementStrategy.FollowStrategy;
import dungeonmania.entities.movingEntity.movementStrategy.RandomStrategy;
import dungeonmania.entities.movingEntity.playerStrategy.InvincibleStrategy;
import dungeonmania.entities.movingEntity.playerStrategy.InvisibleStrategy;
import dungeonmania.entities.movingEntity.playerStrategy.NormalStrategy;
import dungeonmania.entities.movingEntity.playerStrategy.PlayerStrategy;
import dungeonmania.util.Position;

public class MercStrat {

    protected int duration;
    private String strategyName;

    public MercStrat(int duration) {
        this.duration = duration;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public void alert(PlayerStrategy strat, Mercenary mercenary) {
        if (strat instanceof InvisibleStrategy) {
            mercenary.setStrategy(new RandomStrategy());
        } else if (strat instanceof NormalStrategy) {
            mercenary.setStrategy(new FollowStrategy());
        } else if (strat instanceof InvincibleStrategy) {
            mercenary.setStrategy(new FollowStrategy());
        }
    }

    public boolean interact(Player player, Mercenary mercenary) {
        return false;
    }

    public boolean isAlly() {
        return true;
    }

    public void wearout() {}

    public int getDuration() {
        return duration;
    }

    public boolean gotoPrev(Player player, Mercenary mercenary) {
        if (Position.isAdjacent(player.getprevPos(), mercenary.getPosition())) {
            mercenary.setPosition(player.getprevPos());
            return true;
        } else {
            return false;
        }
    }
}
