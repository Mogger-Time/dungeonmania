package dungeonmania.game;

import dungeonmania.entities.Entity;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;

import java.util.ArrayList;
import java.util.List;

public class DungeonResponseMapper {
    public DungeonResponse map(Game game) {
        List<EntityResponse> entityResponses = new ArrayList<>();
        for (Entity entity : game.getEntities()) {
            entityResponses.add(entity.getEntityResponse());
        }
        if (game.getPlayer().getHealth() > 0) {
            entityResponses.add(game.getPlayer().getEntityResponse());
        }
        return new DungeonResponse(game.getDungeonID(), game.getDungeonName(), entityResponses,
                game.getPlayer().getInvResponse(), game.getPlayer().getBattleResponses(),
                game.getPlayer().getBuildables(), game.getGoal().printcurGoal(game));
    }
}
