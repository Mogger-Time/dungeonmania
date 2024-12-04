package dungeonmania.entities;

import dungeonmania.dtos.EntitiesDto;
import dungeonmania.entities.movingEntity.Player;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public abstract class Entity {

    private String entityID;
    private Position position;
    private Boolean collision;
    private Boolean interactable;
    private String name;

    public Entity() {
        this.entityID = UUID.randomUUID().toString();
        this.position = new Position(0, 0);
        this.collision = false;
        this.interactable = false;
    }

    public Position getNewPosition(Direction direction) {
        return this.position.translateBy(direction);
    }

    public EntityResponse getEntityResponse() {
        return new EntityResponse(this.entityID, this.name, this.position, this.interactable);
    }

    public ItemResponse getItemResponse() {
        return new ItemResponse(this.entityID, this.name);
    }

    //effects of being on entity on the player
    //collectables will give player item
    //portal will tp player etc..
    public boolean interact(Player player) {
        return false;
    }

    public void premove(Player player, Direction movement) {
    }

    public void setupEntity(EntitiesDto entitiesDto, Position position) {
        setPosition(position);
    }
}
