package dungeonmania.entities.movingEntity;

import org.json.JSONObject;

import dungeonmania.entities.movingEntity.movementStrategy.FearStrategy;
import dungeonmania.entities.movingEntity.movementStrategy.FollowStrategy;
import dungeonmania.entities.movingEntity.movementStrategy.MovementStrategy;
import dungeonmania.entities.movingEntity.movementStrategy.RandomStrategy;
import dungeonmania.entities.movingEntity.playerStrategy.PlayerStrategy;
import dungeonmania.game.Game;
import dungeonmania.util.Position;

public class Assassin extends Mercenary {
    
    private int reconRadius;
    private MovementStrategy altstrat = new FollowStrategy();
    private double bribefailrate;

    public Assassin() {
        super();
        setName("assassin");
        super.setStrategy(new FollowStrategy());
    }

    @Override
    public void setupEntity(JSONObject entityConfig, Position position) {
        super.setPosition(position);
        super.setHealth(entityConfig.getDouble("assassin_health"));
        super.setDamage(entityConfig.getDouble("assassin_attack"));
        this.reconRadius = entityConfig.getInt("assassin_recon_radius");
        super.setBribeAmount(entityConfig.getInt("assassin_bribe_amount"));
        super.setBribeRadius(entityConfig.getInt("bribe_radius"));
        this.bribefailrate = entityConfig.getDouble("assassin_bribe_fail_rate");
    }

    @Override
    public boolean bribe() {
        if (Math.random() < bribefailrate) {
            return false;
        } else {
            return super.bribe();
        }
    }

    // @Override
    // public void move(Game game) {
    //     Position playerpos = game.getPlayerPosition();
    //     Position ourpos = getPosition();
    //     if (Math.abs(playerpos.getX()-ourpos.getX()) < reconRadius && Math.abs(playerpos.getY()-ourpos.getY()) < reconRadius) {
    //         altstrat.move(game, this);
    //     } else {
    //         getStrategy().move(game, this);
    //     }
    //     updateallystatus();
    // }

    @Override
    public void move(Game game) {
        Position playerpos = game.getPlayerPosition();
        Position ourpos = getPosition();
        if (Math.abs(playerpos.getX()-ourpos.getX()) < reconRadius && Math.abs(playerpos.getY()-ourpos.getY()) < reconRadius && getStrategy() instanceof RandomStrategy) {
            super.move(game, altstrat);
        } else {
            super.move(game);
        }
    }
}
