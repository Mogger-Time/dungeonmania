package dungeonmania.response.models;

import java.time.LocalDateTime;

public final class GameResponse {
    private final String dungeonId;
    private final String name;
    private final LocalDateTime lastSaved;

    public GameResponse(String dungeonId, String name, LocalDateTime lastSaved) {
        this.dungeonId = dungeonId;
        this.name = name;
        this.lastSaved = lastSaved;
    }

    public LocalDateTime getLastSaved() {
        return lastSaved;
    }

    public String getGameId() {
        return name;
    }

    public String getDungeonId() {
        return dungeonId;
    }
}
