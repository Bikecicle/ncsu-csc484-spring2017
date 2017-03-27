package path_finding;

import graph.Node;

public class Euclidian implements Heuristic{

	@Override
	public double estimate(Node node, Node target) {
		return Math.sqrt(Math.pow(target.x - node.x, 2) + Math.pow(target.y - node.y, 2));
	}

}
