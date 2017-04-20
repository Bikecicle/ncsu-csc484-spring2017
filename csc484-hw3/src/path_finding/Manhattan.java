package path_finding;

import graph.Node;

public class Manhattan implements Heuristic {

	@Override
	public double estimate(Node node, Node target) {
		return Math.abs(target.position.x - node.position.x + target.position.y - node.position.y);
	}

}
