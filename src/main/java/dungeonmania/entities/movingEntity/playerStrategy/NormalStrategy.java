package dungeonmania.entities.movingEntity.playerStrategy;

public class NormalStrategy extends PlayerStrategy {

    public NormalStrategy() {
        super(1);
        setStrategyName("normal");
    }

    @Override
    public void wearout() {
        return;
    }
}
