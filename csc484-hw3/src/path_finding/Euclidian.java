package path_finding;

import graph.Node;

public class Euclidian implements Heuristic{

	@Override
	public double estimate(Node node, Node target) {
		return Math.sqrt(Math.pow(target.position.x - node.position.x, 2) + Math.pow(target.position.y - node.position.y, 2));
	}

}
