package dungeonmania.response.models;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public final class DungeonResponse {
    private final String dungeonId;
    private final String dungeonName;
    private final List<EntityResponse> entities;
    private final List<ItemResponse> inventory;
    private final List<BattleResponse> battles;
    private final List<String> buildables;
    private final String goals;
    @Getter
    private final List<AnimationQueue> animations;

    public DungeonResponse(String dungeonId, String dungeonName, List<EntityResponse> entities,
                           List<ItemResponse> inventory, List<BattleResponse> battles, List<String> buildables, String goals) {
        this(dungeonId, dungeonName, entities, inventory, battles, buildables, goals, new ArrayList<>());
    }

    public DungeonResponse(String dungeonId, String dungeonName, List<EntityResponse> entities,
                           List<ItemResponse> inventory, List<BattleResponse> battles, List<String> buildables, String goals,
                           List<AnimationQueue> animations) {
        this.dungeonId = dungeonId;
        this.dungeonName = dungeonName;
        this.entities = entities;
        this.inventory = inventory;
        this.battles = battles;
        this.buildables = buildables;
        this.goals = goals;
        this.animations = animations;
    }

    public String getDungeonName() {
        return dungeonName;
    }

    public List<ItemResponse> getInventory() {
        return inventory;
    }

    public List<BattleResponse> getBattles() {
        return battles;
    }

    public List<String> getBuildables() {
        return buildables;
    }

    public String getGoals() {
        return goals;
    }

    public String getDungeonId() {
        return dungeonId;
    }

    public List<EntityResponse> getEntities() {
        return entities;
    }
}
