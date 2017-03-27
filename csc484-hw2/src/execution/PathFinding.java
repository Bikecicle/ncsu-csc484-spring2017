package execution;

import java.util.List;
import java.util.Scanner;

import graph.AdjacencyList;
import graph.Edge;
import path_finding.AStar;
import path_finding.Dijkstra;
import path_finding.Euclidian;
import path_finding.Manhattan;

public class PathFinding {

	public static void main(String[] args) {

		Dijkstra dijkstra = new Dijkstra();
		AStar aStar = new AStar();

		Scanner in = new Scanner(System.in);

		System.out.println("Graph File: ");
		String filename = in.next();
		System.out.println("Loading graph...");
		AdjacencyList graph = AdjacencyList.load(filename);
		System.out.println("Loading complete");

		while (true) {
			System.out.println();
			System.out.println("Start: ");
			int start = in.nextInt();
			if (start == -1)
				break;
			System.out.println("Goal: ");
			int end = in.nextInt();

			System.out.println();
			System.out.println("Dijkstra's:");
			List<Edge> dpath = dijkstra.path(graph, graph.findNode(start), graph.findNode(end));
			for (Edge edge : dpath) {
				System.out.println(edge);
			}
			System.out.println("Total Length: " + dijkstra.length + " * 700ft");
			System.out.println("Time Elapsed: " + dijkstra.time + " ns");
			System.out.println("Visited: " + dijkstra.visited);

			System.out.println();
			System.out.println("A* (Euclidian):");
			List<Edge> epath = aStar.path(graph, graph.findNode(start), graph.findNode(end), new Euclidian());
			for (Edge edge : epath) {
				System.out.println(edge);
			}
			System.out.println("Total Length: " + aStar.length + " * 700ft");
			System.out.println("Time Elapsed: " + aStar.time + " ns");
			System.out.println("Visited: " + aStar.visited);
			
			System.out.println();
			System.out.println("A* (Manhattan):");
			List<Edge> mpath = aStar.path(graph, graph.findNode(start), graph.findNode(end), new Manhattan());
			for (Edge edge : mpath) {
				System.out.println(edge);
			}
			System.out.println("Total Length: " + aStar.length + " * 700ft");
			System.out.println("Time Elapsed: " + aStar.time + " ns");
			System.out.println("Visited: " + aStar.visited);
		}
		in.close();
		System.out.println("Exiting...");
	}
}
