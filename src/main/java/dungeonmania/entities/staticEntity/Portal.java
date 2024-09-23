package dungeonmania.entities.staticEntity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntity.MovingEntity;
import dungeonmania.entities.movingEntity.Player;
import dungeonmania.game.Game;
import dungeonmania.game.GameLauncher;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Portal extends StaticEntity {

    private String colour;

    public Portal() {
        super();
        setName("portal");
        super.setInteractable(true);
    }

    public String getColour() {
        return colour;
    }

    public Position findotherPortal() {
        List<Entity> entities = GameLauncher.getEntities();
        for (Entity entity : entities) {
            if (entity instanceof Portal) {
                Portal portalExit = (Portal) entity;
                if (portalExit.getColour().equals(this.colour) && !portalExit.getPosition().equals(this.getPosition())) {
                    return portalExit.getPosition();
                }
            }
        }
        return null;
    }

    public void enterPortal(MovingEntity entity, Direction movement) {
        Game activeGame = GameLauncher.getActiveGame();

        // Find coordinates of corresponding portal
        Position newpos = getnewLoc(movement, activeGame);
        if (newpos != null) {
            entity.setPosition(newpos);
        }

        List<Entity> futurepos = activeGame.getEntitiesinPos(entity.getPosition());
        for (Entity ent : futurepos) {
            if (ent instanceof Portal) {
                Portal nextportal = (Portal) ent;
                nextportal.enterPortal(entity, movement);
            }
        }
    }
    
    public Position getnewLoc(Direction movement, Game activeGame) {
        Position portalExitPos = this.findotherPortal();
        ArrayList<Direction> validPositions = activeGame.getValidAdjacentTiles(portalExitPos);

        // If portal exit is blocked or surrounded, don't move player
        if (validPositions.size() == 0) {
            return null;
        }

        // Move player to new position if valid
        for (Direction dir : validPositions) {
            if (dir == movement) {
                return portalExitPos.translateBy(dir);
            }
        }
        return null;
    }

    @Override
    public void setupEntity(JSONObject entityConfig, Position position) {
        setPosition(position);
        this.colour = entityConfig.getString("colour");
    }

    @Override
    public void premove(Player player, Direction movement) {
        enterPortal(player, movement);
    }
}
