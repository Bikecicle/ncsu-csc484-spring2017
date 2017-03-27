package path_finding;

import java.util.Comparator;
import java.util.List;
import graph.AdjacencyList;
import graph.Edge;
import graph.Node;

public class Dijkstra {
	
	public int visited;
	public long time;
	public double length;

	public Path path(AdjacencyList graph, Node start, Node end) {
		NodeRecord startRecord = new NodeRecord(start);
		PathfindingList open = new PathfindingList();
		open.add(startRecord);
		PathfindingList closed = new PathfindingList();
		visited = 0;
		time = System.nanoTime();

		NodeRecord current = null;
		while (!open.isEmpty()) {
			open.sort(new Comparator<NodeRecord>() {
				@Override
				public int compare(NodeRecord o1, NodeRecord o2) {
					return (int) Math.signum(o1.costSoFar - o2.costSoFar);
				}
			});
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
			visited++;
		}
		time = System.nanoTime() - time;
		
		if (current.node != end)
			return null;
		Path path = new Path();
		length = current.costSoFar;
		while (current.node != start) {
			path.add(0, current.connection);
			current = closed.find(current.connection.origin);
		}
		return path;
	}

}
