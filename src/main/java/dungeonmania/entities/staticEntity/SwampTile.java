package dungeonmania.entities.staticEntity;

import org.json.JSONObject;

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
    public void setupEntity(JSONObject entityConfig, Position position) {
        setPosition(position);
        this.movementFactor = entityConfig.getInt("movement_factor");
    }
}
