package dungeonmania.util;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Node {
    private final Position position;
    private final Map<Node, Double> adjacent = new HashMap<>();
    private List<Node> path = new ArrayList<>();
    @Setter
    private Double distance = Double.MAX_VALUE;

    public Node(Position position) {
        this.position = position;
    }

    public void addAdjacent(Node node, Double distance) {
        adjacent.put(node, distance);
    }

    public void setPath(Node node) {
        path = node.getPath();
        path.add(node);
    }

}
