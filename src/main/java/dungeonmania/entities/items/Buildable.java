package dungeonmania.entities.items;

import java.util.List;

public interface Buildable {
    public boolean isBuildable(List<Item> inventory);
}
