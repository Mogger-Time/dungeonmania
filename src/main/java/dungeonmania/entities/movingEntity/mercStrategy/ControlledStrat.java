package dungeonmania.entities.movingEntity.mercStrategy;

public class ControlledStrat extends MercStrat {
    public ControlledStrat(int duration) {
        super(duration);
        setStrategyName("controlled");
    }

    @Override
    public void wearout() {
        duration--;
    }
}
