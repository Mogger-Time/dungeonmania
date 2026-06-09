package dungeonmania.entities.movingEntity;

import dungeonmania.dtos.EntitiesDto;
import dungeonmania.entities.movingEntity.movementStrategy.FollowStrategy;
import dungeonmania.entities.movingEntity.movementStrategy.MovementStrategy;
import dungeonmania.entities.movingEntity.movementStrategy.RandomStrategy;
import dungeonmania.game.Game;
import dungeonmania.game.GameRuntimeContext;
import dungeonmania.util.Position;
import dungeonmania.util.RandomProvider;

public class Assassin extends Mercenary {

    private final MovementStrategy altstrat = new FollowStrategy();
    private int reconRadius;
    private double bribefailrate;

    public Assassin() {
        super();
        setName("assassin");
        super.setStrategy(new FollowStrategy());
    }

    @Override
    public void setupEntity(EntitiesDto entitiesDto, Position position) {
        super.setPosition(position);
        super.setHealth(GameRuntimeContext.getConfig().getAssassinHealth());
        super.setDamage(GameRuntimeContext.getConfig().getAssassinAttack());
        this.reconRadius = GameRuntimeContext.getConfig().getAssassinReconRadius();
        super.setBribeAmount(GameRuntimeContext.getConfig().getAssassinBribeAmount());
        super.setBribeRadius(GameRuntimeContext.getConfig().getBribeRadius());
        this.bribefailrate = GameRuntimeContext.getConfig().getAssassinBribeFailRate();
    }

    @Override
    public boolean bribe() {
        if (RandomProvider.nextDouble() < bribefailrate) {
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
        if (Math.abs(playerpos.getX() - ourpos.getX()) < reconRadius && Math.abs(playerpos.getY() - ourpos.getY()) < reconRadius && getStrategy() instanceof RandomStrategy) {
            super.move(game, altstrat);
        } else {
            super.move(game);
        }
    }
}
