package dungeonmania.entities.staticEntity;

import dungeonmania.util.Position;

public class FloorSwitch extends StaticEntity {

    private boolean isTriggered;

    public FloorSwitch() {
        super();
        setName("switch");
        super.setInteractable(false);
    }

    public void checkTriggered(Boulder boulder) {
        Position boulderPos = boulder.getPosition();
        Position floorSwPos = this.getPosition();

        if (boulderPos == floorSwPos) {
            this.isTriggered = true;
        }
    }
    
}
