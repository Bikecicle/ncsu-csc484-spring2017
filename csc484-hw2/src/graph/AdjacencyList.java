package graph;

import java.util.LinkedList;
import java.util.List;

public class AdjacencyList {

	public List<Node> nodeList;
	public List<Edge> edgeList;

	public AdjacencyList() {
		nodeList = new LinkedList<Node>();
		edgeList = new LinkedList<Edge>();
	}

	public void addNode(Node node) {
		nodeList.add(node);
	}

	public void addEdge(Node origin, Node destination, double weight) {
		new Edge(origin, destination, weight);
	}
	
}
