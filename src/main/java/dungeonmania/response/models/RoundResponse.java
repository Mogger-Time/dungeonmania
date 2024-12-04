package dungeonmania.response.models;

import lombok.Getter;

import java.util.List;

public class RoundResponse {
    private final double deltaPlayerHealth;
    @Getter
    private final double deltaEnemyHealth;
    @Getter
    private final List<ItemResponse> weaponryUsed;

    public RoundResponse(double deltaPlayerHealth, double deltaEnemyHealth, List<ItemResponse> weaponryUsed) {
        this.deltaPlayerHealth = deltaPlayerHealth;
        this.deltaEnemyHealth = deltaEnemyHealth;
        this.weaponryUsed = weaponryUsed;
    }

    public double getDeltaCharacterHealth() {
        return deltaPlayerHealth;
    }

}
