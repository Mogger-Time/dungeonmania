package dungeonmania.entities.staticEntity;

import dungeonmania.dtos.EntitiesDto;
import dungeonmania.util.Position;

public class SwampTile extends StaticEntity {

    private int movementFactor;

    public SwampTile() {
        super();
        setName("swampTile");
    }

    public int getFactor() {
        return movementFactor;
    }

    @Override
    public void setupEntity(EntitiesDto entitiesDto, Position position) {
        this.movementFactor = entitiesDto.getMovement_factor();
        setPosition(position);
    }
}
