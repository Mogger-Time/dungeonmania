package dungeonmania.util;

import java.util.*;

public class Node {
    private Position position;
    private List<Node> path = new ArrayList<Node>();
    private Double distance = Double.MAX_VALUE;
    private Map<Node, Double> adjacent = new HashMap<Node, Double>();

    public Node(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public Double getDistance() {
        return distance;
    }

    public List<Node> getPath() {
        return path;
    }

    public Map<Node, Double> getAdjacent() {
        return adjacent;
    }

    public void addAdjacent(Node node, Double distance) {
        adjacent.put(node, distance);
    }

    public void setPath(Node node) {
        path = node.getPath();
        path.add(node);
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
