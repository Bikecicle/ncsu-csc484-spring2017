package path_finding;

import java.nio.file.Path;
import java.util.List;

import graph.AdjacencyList;
import graph.Edge;
import graph.Node;

public class AStar {

	public static List<Edge> path(AdjacencyList graph, Node start, Node end, Heuristic heuristic) {
		NodeRecord startRecord = new NodeRecord(start);
		startRecord.estimatedTotalCost = heuristic.estimate(start);
		PathfindingList open = new PathfindingList();
		open.add(startRecord);
		PathfindingList closed = new PathfindingList();
		
		return null;
		
	}
}
