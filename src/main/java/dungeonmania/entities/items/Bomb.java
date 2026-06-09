package dungeonmania.entities.items;

import dungeonmania.dtos.EntitiesDto;
import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntity.Player;
import dungeonmania.entities.staticEntity.logicalEntity.LogicActivationEvaluator;
import dungeonmania.game.Game;
import dungeonmania.game.GameRuntimeContext;
import dungeonmania.util.Position;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class Bomb extends Item {
    @Getter
    private int range;
    private boolean deployed = false;
    @Setter
    private String logic;

    public Bomb() {
        super();
        setName("bomb");
    }

    public void deploy() {
        deployed = true;
    }

    public void checkStatus(Game game) {
        if (!deployed) {
            return;
        }
        if (LogicActivationEvaluator.isActivated(logic, game, getPosition())) {
            detonate(game);
        }
    }

    public void detonate(Game game) {
        Position position = this.getPosition();
        position = position.translateBy(-range, -range);
        for (int i = 0; i <= (2 * range); i++) {
            for (int j = 0; j <= (2 * range); j++) {
                Position temppos = position.translateBy(i, j);
                List<Entity> blownupentities = game.getEntities().stream().filter(s -> (s.getPosition().equals(temppos))).collect(Collectors.toList());
                for (Entity entity : blownupentities) {
                    game.removeEntity(entity);
                }
            }
        }
    }

    @Override
    public void setupEntity(EntitiesDto entitiesDto, Position position) {
        super.setPosition(position);
        this.range = GameRuntimeContext.getConfig().getBombRadius();
    }

    @Override
    public boolean interact(Player player) {
        if (deployed) {
            return false;
        } else {
            return super.interact(player);
        }
    }

}
