package graph;

import java.util.LinkedList;
import java.util.List;

public class Node {

	public List<Edge> incidentList;
	public double x;
	public double y;
	public int id;
	
	public Node() {
		incidentList = new LinkedList<Edge>();
	}
	
	public Node(int id, double y, double x) {
		incidentList = new LinkedList<Edge>();
		this.x = x;
		this.y = y;
		this.id = id;
	}
}
