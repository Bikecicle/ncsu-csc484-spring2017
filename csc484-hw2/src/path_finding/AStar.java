package path_finding;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import graph.AdjacencyList;
import graph.Edge;
import graph.Node;

public class AStar {

	public int visited;
	public long time;
	public double length;
	
	public List<Edge> path(AdjacencyList graph, Node start, Node end, Heuristic heuristic) {
		NodeRecord startRecord = new NodeRecord(start);
		startRecord.estimatedTotalCost = heuristic.estimate(start, end);
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
					return (int) Math.signum(o1.estimatedTotalCost - o2.estimatedTotalCost);
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
				double endNodeHeuristic;
				if (closed.contains(endNode)) {
					endNodeRecord = closed.find(endNode);
					if (endNodeRecord.costSoFar <= endNodeCost)
						continue;
					closed.remove(endNodeRecord);
					endNodeHeuristic = endNodeRecord.estimatedTotalCost - endNodeRecord.costSoFar;
				} else if (open.contains(endNode)) {
					endNodeRecord = open.find(endNode);
					if (endNodeRecord.costSoFar <= endNodeCost)
						continue;
					endNodeHeuristic = endNodeRecord.estimatedTotalCost - endNodeRecord.costSoFar;
				} else {
					endNodeRecord = new NodeRecord(endNode);
					endNodeHeuristic = heuristic.estimate(endNode, end);
				}
				endNodeRecord.costSoFar = endNodeCost;
				endNodeRecord.connection = connection;
				endNodeRecord.estimatedTotalCost = endNodeCost + endNodeHeuristic;
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
		
		length = current.costSoFar;
		List<Edge> path = new ArrayList<Edge>();
		while (current.node != start) {
			path.add(0, current.connection);
			current = closed.find(current.connection.origin);
		}
		return path;
	}
}
