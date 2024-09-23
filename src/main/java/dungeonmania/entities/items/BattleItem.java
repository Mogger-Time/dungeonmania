package dungeonmania.entities.items;

public abstract class BattleItem extends Item {
    private int durability;
    private int attack;
    private int defence;
    private int multiplier;

    public BattleItem() {
        super();
    }

    public BattleItem(int durability, int attack, int defence, int multiplier) {
        super();
        this.durability = durability;
        this.attack = attack;
        this.defence = defence;
        this.multiplier = multiplier;
    }

    public void reduceDurability() {
        this.durability-=1;
    }

    public int getDurability() {
        return this.durability;
    }

    public int getAttack() {
        return this.attack;
    }

    public int getDefence() {
        return this.defence;
    }

    public int getMultiplier() {
        return this.multiplier;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    
}
