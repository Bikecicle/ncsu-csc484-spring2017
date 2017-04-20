package path_finding;

import graph.Node;

public interface Heuristic {

	public double estimate(Node node, Node target);

}
