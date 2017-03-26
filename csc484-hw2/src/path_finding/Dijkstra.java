package path_finding;

import java.util.ArrayList;
import java.util.List;
import graph.AdjacencyList;
import graph.Edge;
import graph.Node;

public class Dijkstra {

	public static List<Edge> path(AdjacencyList graph, Node start, Node end) {
		NodeRecord startRecord = new NodeRecord(start);
		PathfindingList open = new PathfindingList();
		open.add(startRecord);
		PathfindingList closed = new PathfindingList();

		NodeRecord current = null;
		while (!open.isEmpty()) {
			open.sort(null);
			current = open.get(0);
			if (current.node == end)
				break;
			List<Edge> connections = current.node.incidentList;
			for (Edge connection : connections) {
				Node endNode = connection.destination;
				double endNodeCost = current.costSoFar + connection.weight;
				NodeRecord endNodeRecord = null;
				if (closed.contains(endNode)) {
					continue;
				} else if (open.contains(endNode)) {
					endNodeRecord = open.find(endNode);
					if (endNodeRecord.costSoFar <= endNodeCost)
						continue;
				} else {
					endNodeRecord = new NodeRecord(endNode);
				}
				endNodeRecord.connection = connection;
				endNodeRecord.costSoFar = endNodeCost;
				if (!open.contains(endNode))
					open.add(endNodeRecord);
			}
			open.remove(current);
			closed.add(current);
		}
		
		if (current.node != end)
			return null;
		
		List<Edge> path = new ArrayList<Edge>();
		while (current.node != start) {
			path.add(0, current.connection);
			current = closed.find(current.connection.origin);
		}
		return path;
	}

}
