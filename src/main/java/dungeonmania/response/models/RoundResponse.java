package dungeonmania.response.models;

import java.util.List;

public class RoundResponse {
    private double deltaPlayerHealth;
    private double deltaEnemyHealth;
    private List<ItemResponse> weaponryUsed;

    public RoundResponse(double deltaPlayerHealth, double deltaEnemyHealth, List<ItemResponse> weaponryUsed)
    {
        this.deltaPlayerHealth = deltaPlayerHealth;
        this.deltaEnemyHealth = deltaEnemyHealth;
        this.weaponryUsed = weaponryUsed;
    }

    public double getDeltaCharacterHealth(){
        return deltaPlayerHealth;
    }
    
    public double getDeltaEnemyHealth(){
        return deltaEnemyHealth;
    }

    public List<ItemResponse> getWeaponryUsed() { return weaponryUsed; }
}
