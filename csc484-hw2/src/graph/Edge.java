package graph;

public class Edge {

	public Node origin;
	public Node destination;
	public double weight;

	public Edge(Node origin, Node destination, double weight) {
		this.origin = origin;
		this.destination = destination;
		this.weight = weight;
		origin.incidentList.add(this);
	}

	public Node getOtherEnd(Node node) {
		if (origin != node)
			return origin;
		return destination;
	}

}
