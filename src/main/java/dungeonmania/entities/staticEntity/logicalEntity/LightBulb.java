package dungeonmania.entities.staticEntity.logicalEntity;

import dungeonmania.dtos.EntitiesDto;
import dungeonmania.game.GameRuntimeContext;
import dungeonmania.util.Position;

public class LightBulb extends LogicalEntity {
    private String logic;

    public LightBulb() {
        super();
        setName("light_bulb_off");
        super.setInteractable(false);
    }

    @Override
    public void setupEntity(EntitiesDto entitiesDto, Position position) {
        this.logic = entitiesDto.getLogic();
        setPosition(position);
    }

    public void checkActivated() {
        if (LogicActivationEvaluator.isActivated(logic, GameRuntimeContext.getActiveGame(), getPosition())) {
            setName("light_bulb_on");
        } else {
            setName("light_bulb_off");
        }
    }

}
