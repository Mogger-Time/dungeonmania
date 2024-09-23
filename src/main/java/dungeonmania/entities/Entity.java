package dungeonmania.entities;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.entities.movingEntity.Player;

import java.util.UUID;

import org.json.JSONObject;

public abstract class Entity {
    
    private String      entityID;
    private Position    position;
    private Boolean     collision;
    private Boolean     interactable;
    private String name;

    public Entity() {
        this.entityID = UUID.randomUUID().toString();
        this.position = new Position(0, 0);
        this.collision = false;
        this.interactable = false;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setCollision(Boolean collision) {
        this.collision = collision;
    }

    public void setInteractable(Boolean interactable) {
        this.interactable = interactable;
    }

    public String getEntityID() {
        return entityID;
    }

    public Boolean getCollision() {
        return collision;
    }

    public Boolean getInteractable() {
        return interactable;
    }

    public Position getNewPosition(Direction direction) {
        
        return this.position.translateBy(direction);
    }

    public Position getPosition() {
        return this.position;
    }

    public EntityResponse getEntityResponse() {
        return new EntityResponse(this.entityID, this.name, this.position, this.interactable);
    }

    public ItemResponse getItemResponse() {
        return new ItemResponse(this.entityID, this.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //effects of being on entity on the player
    //collectables will give player item
    //portal will tp player etc..
    public boolean interact(Player player) {
        return false;
    }

    public void premove(Player player, Direction movement) {}

    public void setupEntity(JSONObject entityConfig, Position position) {
        setPosition(position);
    }
}
