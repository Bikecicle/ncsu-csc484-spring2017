package path_finding;

import graph.Edge;
import graph.Node;

public class NodeRecord implements Comparable<NodeRecord>{

	public Node node;
	public Edge connection;
	public double costSoFar;
	public double estimatedTotalCost;

	public NodeRecord(Node node) {
		this.node = node;
		connection = null;
		costSoFar = 0;
		estimatedTotalCost = 0;
	}

	@Override
	public int compareTo(NodeRecord other) {
		return (int) Math.signum(costSoFar - other.costSoFar);
	}
}
