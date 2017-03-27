package data_sets;

import graph.AdjacencyList;
import graph.Node;

public class LargeGraph {

	private static final int nodeCount = 10000;
	private static final int graphDim = 100;

	public static void main(String[] args) {
		AdjacencyList graph = new AdjacencyList();
		System.out.println("Adding nodes...");
		for (int i = 0; i < nodeCount; i++) {
			graph.addNode(new Node(i, Math.random() * graphDim, Math.random() * graphDim));
		}
		System.out.println("Adding edges...");
		for (int i = 0; i < nodeCount; i++) {
			int edgeCount = (int) (Math.random() * nodeCount / 100) + 1;
			for (int j = 0; j < edgeCount; j++) {
				graph.addDoubleEdge(i, (int) (Math.random() * nodeCount));
			}
			System.out.println((100.0 * i / nodeCount) + "%");
		}
		System.out.println("Saving...");
		graph.save("large_graph.txt");
		System.out.println("Complete!");
	}
}
