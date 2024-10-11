package dungeonmania.response.models;

import java.util.ArrayList;
import java.util.List;

public final class BattleResponse {
    private final String enemy;
    private final double initialPlayerHealth;
    private final double initialEnemyHealth;
    private final List<RoundResponse> rounds;

    public BattleResponse() {
        this.initialPlayerHealth = 0;
        this.initialEnemyHealth = 0;
        this.enemy = "";
        this.rounds = new ArrayList<>();
    }

    public BattleResponse(String enemy, List<RoundResponse> rounds, double initialPlayerHealth, double initialEnemyHealth) {
        this.initialPlayerHealth = initialPlayerHealth;
        this.initialEnemyHealth = initialEnemyHealth;
        this.enemy = enemy;
        this.rounds = rounds;
    }

    public String getEnemy() {
        return enemy;
    }

    public double getInitialPlayerHealth() {
        return initialPlayerHealth;
    }

    public double getInitialEnemyHealth() {
        return initialEnemyHealth;
    }

    public List<RoundResponse> getRounds() {
        return rounds;
    }
}
