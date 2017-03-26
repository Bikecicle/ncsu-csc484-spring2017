package graph;

import java.util.LinkedList;
import java.util.List;

public class Node {

	public List<Edge> incidentList;
	
	public Node() {
		incidentList = new LinkedList<Edge>();
	}
}
