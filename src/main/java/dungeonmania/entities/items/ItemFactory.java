package dungeonmania.entities.items;

import dungeonmania.exceptions.InvalidActionException;

import java.util.List;

public class ItemFactory {

    public static Item craftItem(String itemtype, List<Item> inventory) throws InvalidActionException {
        switch (itemtype) {
            case "shield":
                Shield newShield = new Shield();
                if (!newShield.isBuildable(inventory)) {
                    throw new InvalidActionException("Not enough items to craft");
                } else {
                    newShield.setupEntity(null, null);
                    return newShield;
                }
            case "bow":
                Bow newBow = new Bow();
                if (!newBow.isBuildable(inventory)) {
                    throw new InvalidActionException("Not enough items to craft");
                } else {
                    newBow.setupEntity(null, null);
                    return newBow;
                }
            case "sceptre":
                Sceptre newSceptre = new Sceptre();
                if (!newSceptre.isBuildable(inventory)) {
                    throw new InvalidActionException("Not enough items to craft");
                } else {
                    newSceptre.setupEntity(null, null);
                    return newSceptre;
                }
            case "midnight_armour":
                MidnightArmour newMidnightArmour = new MidnightArmour();
                if (!newMidnightArmour.isBuildable(inventory)) {
                    throw new InvalidActionException("Not enough items to craft");
                } else {
                    newMidnightArmour.setupEntity(null, null);
                    return newMidnightArmour;
                }
            default:
                return null;
        }
    }
}

