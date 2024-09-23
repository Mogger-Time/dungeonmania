package dungeonmania.entities.items;

import java.util.List;

public interface Buildable {
    boolean isBuildable(List<Item> inventory);
}
