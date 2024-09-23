package dungeonmania.entities.items;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.game.GameLauncher;

import java.util.List;

public class ItemFactory {

    public static Item craftItem(String itemtype, List<Item> inventory) throws InvalidActionException {
        if (itemtype.equals("shield")) {
            Shield newShield = new Shield();
            if (!newShield.isBuildable(inventory)) {
                throw new InvalidActionException("Not enough items to craft");
            } else {
                newShield.setupEntity(GameLauncher.getConfig(), null);
                return newShield;
            }
        } else if (itemtype.equals("bow")) {
            Bow newBow = new Bow();
            if (!newBow.isBuildable(inventory)) {
                throw new InvalidActionException("Not enough items to craft");
            } else {
                newBow.setupEntity(GameLauncher.getConfig(), null);
                return newBow;
            }
        } else if (itemtype.equals("sceptre")) {
            Sceptre newSceptre = new Sceptre();
            if (!newSceptre.isBuildable(inventory)) {
                throw new InvalidActionException("Not enough items to craft");
            } else {
                newSceptre.setupEntity(GameLauncher.getConfig(), null);
                return newSceptre;
            }
        } else if (itemtype.equals("midnight_armour")) {
            MidnightArmour newMidnightArmour = new MidnightArmour();
            if (!newMidnightArmour.isBuildable(inventory)) {
                throw new InvalidActionException("Not enough items to craft");
            } else {
                newMidnightArmour.setupEntity(GameLauncher.getConfig(), null);
                return newMidnightArmour;
            }
        } else {
            return null;
        }
    }
}

