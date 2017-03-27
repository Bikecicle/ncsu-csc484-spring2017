package path_finding;

import graph.Node;

public class Manhattan implements Heuristic {

	@Override
	public double estimate(Node node, Node target) {
		return Math.abs(target.x - node.x + target.y - node.y);
	}

}
