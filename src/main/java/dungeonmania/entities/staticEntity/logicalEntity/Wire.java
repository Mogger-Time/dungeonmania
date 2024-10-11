package dungeonmania.entities.staticEntity.logicalEntity;


import dungeonmania.entities.Entity;
import dungeonmania.entities.staticEntity.Boulder;
import dungeonmania.entities.staticEntity.FloorSwitch;
import dungeonmania.game.Game;
import dungeonmania.game.GameLauncher;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;

public class Wire extends LogicalEntity {

    private boolean isActivated;
    private boolean isVisited = false;

    public Wire() {
        super();
        setName("wire");
        super.setInteractable(false);
    }

    public boolean isActivated() {
        return isActivated;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void checkActivated() {
        Position wirepos = this.getPosition();
        List<Position> adjacentWire = new ArrayList<>();
        int x = wirepos.getX();
        int y = wirepos.getY();
        adjacentWire.add(new Position(x - 1, y));
        adjacentWire.add(new Position(x + 1, y));
        adjacentWire.add(new Position(x, y - 1));
        adjacentWire.add(new Position(x, y + 1));

        Position boulderpos = null;
        Position switchpos = null;

        Game activeGame = GameLauncher.getActiveGame();
        this.isActivated = false;
        for (Position pos : adjacentWire) {
            List<Entity> entsInPos = activeGame.getEntitiesinPos(pos);
            for (Entity entity : entsInPos) {
                if (entity instanceof Wire && !this.isVisited) {
                    //If connected to another wire
                    this.isActivated = true;
                    this.isVisited = true;
                    ((Wire) entity).checkActivated();
                    if (((Wire) entity).isActivated) {
                        return;
                    }
                }

                if (entity instanceof Boulder) {
                    boulderpos = pos;
                }
                if (entity instanceof FloorSwitch) {
                    switchpos = pos;
                }
                //If wire is connected to an activated floor switch
                if (boulderpos == switchpos && boulderpos != null) {
                    this.isActivated = true;
                }

            }

        }
    }


}
