package graph;

import java.util.LinkedList;
import java.util.List;

import general.Vector;

public class Node {

	public List<Edge> incidentList;
	public Vector position;
	public int id;

	public Node() {
		incidentList = new LinkedList<Edge>();
	}

	public Node(int id, double y, double x) {
		incidentList = new LinkedList<Edge>();
		position = new Vector(x, y);
		this.id = id;
	}
}
