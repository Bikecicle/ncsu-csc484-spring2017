package path_finding;

import graph.Edge;
import graph.Node;

public class NodeRecord {

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
}
