package dungeonmania.entities.movingEntity.playerStrategy;

import dungeonmania.Battle;
import dungeonmania.entities.movingEntity.Enemy;
import dungeonmania.entities.movingEntity.Player;

public class InvisibleStrategy extends PlayerStrategy {

    public InvisibleStrategy(int duration) {
        super(duration);
        setStrategyName("invisible");
    }

    @Override
    public Battle startBattle(Player player, Enemy enemy) {
        return null;
    }
}
