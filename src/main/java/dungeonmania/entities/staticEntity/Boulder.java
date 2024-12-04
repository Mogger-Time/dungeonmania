package dungeonmania.entities.staticEntity;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntity.MovingEntity;
import dungeonmania.entities.movingEntity.Player;
import dungeonmania.game.Game;
import dungeonmania.game.GameLauncher;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.List;

public class Boulder extends StaticEntity {
    public Boulder() {
        super();
        setName("boulder");
        super.setInteractable(false);
        super.setCollision(true);
    }

    @Override
    public void premove(Player player, Direction direction) {
        pushBoulder(direction);
    }

    public void pushBoulder(Direction direction) {
        Position boulderPos = this.getPosition();

        Position newPos = boulderPos.translateBy(direction);

        Game game = GameLauncher.getActiveGame();
        List<Entity> entitiesPos = game.getEntitiesinPos(newPos);
        boolean blocked = false;
        for (Entity entity : entitiesPos) {
            if ((entity instanceof StaticEntity || entity instanceof MovingEntity) && !(entity instanceof FloorSwitch)) {
                blocked = true;
                break;
            }
        }
        if (!blocked) {
            setPosition(newPos);
        }
    }

}
