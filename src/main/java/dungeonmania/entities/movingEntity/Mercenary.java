package dungeonmania.entities.movingEntity;

import org.json.JSONObject;

import dungeonmania.entities.movingEntity.mercStrategy.BribedStrat;
import dungeonmania.entities.movingEntity.mercStrategy.EnemyStrat;
import dungeonmania.entities.movingEntity.mercStrategy.MercStrat;
import dungeonmania.entities.movingEntity.movementStrategy.FollowStrategy;
import dungeonmania.entities.movingEntity.movementStrategy.MovementStrategy;
import dungeonmania.entities.movingEntity.playerStrategy.PlayerStrategy;
import dungeonmania.game.Game;
import dungeonmania.util.Position;

public class Mercenary extends Enemy {

    private int bribeRadius;
    private int bribeAmount;
    private MercStrat allystatus;

    public Mercenary() {
        super();
        super.setName("mercenary");
        super.setStrategy(new FollowStrategy());
        allystatus = new EnemyStrat(1);
    }

    @Override
    public void setupEntity(JSONObject entityConfig, Position position) {
        super.setPosition(position);
        super.setHealth(entityConfig.getDouble("mercenary_health"));
        super.setDamage(entityConfig.getDouble("mercenary_attack"));
        this.bribeRadius = entityConfig.getInt("bribe_radius");
        this.bribeAmount = entityConfig.getInt("bribe_amount");
    }

    public int getBribeRadius() {
        return bribeRadius;
    }

    public int getBribeAmount() {
        return bribeAmount;
    }

    public void setBribeRadius(int briberadius) {
        bribeRadius = briberadius;
    }

    public void setBribeAmount(int bribeamount) {
        bribeAmount = bribeamount;
    }

    public void alert(PlayerStrategy strat) {
        allystatus.alert(strat, this);
    }

    @Override
    public boolean interact(Player player) {
        return allystatus.interact(player, this);
    }

    @Override
    public void move(Game game) {
        move(game, getStrategy());
    }

    @Override
    public void move(Game game, MovementStrategy strategy) {
        if (!allystatus.gotoPrev(game.getPlayer(), this)) {
            super.move(game, strategy);
        }
        updateallystatus();
    }

    public void updateallystatus() {
        allystatus.wearout();
        if (allystatus.getDuration() == 0) {
            allystatus = new EnemyStrat(1);
            super.setInteractable(true);
        }
    }

    public boolean isAlly() {
        return allystatus.isAlly();
    }

    public void setAllystatus(MercStrat strat) {
        allystatus = strat;
    }

    public boolean bribe() {
        allystatus = new BribedStrat(1);
        return true;
    }
}
