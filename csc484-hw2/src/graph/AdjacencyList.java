package graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class AdjacencyList {

	public List<Node> nodeList;
	public List<Edge> edgeList;

	public AdjacencyList() {
		nodeList = new LinkedList<Node>();
		edgeList = new LinkedList<Edge>();
	}

	public void addNode(Node node) {
		nodeList.add(node);
	}

	public void addEdge(Node origin, Node destination, double weight) {
		edgeList.add(new Edge(origin, destination, weight));
	}

	public void addDoubleEdge(int originId, int destinationId) {
		Node origin = findNode(originId);
		Node destination = findNode(destinationId);
		if (!hasEdge(originId, destinationId)) {
			double weight = Math.sqrt(Math.pow(destination.position.x - origin.position.x, 2)
					+ Math.pow(destination.position.y - origin.position.y, 2));
			edgeList.add(new Edge(origin, destination, weight));
			edgeList.add(new Edge(destination, origin, weight));
		}
	}

	public boolean hasEdge(int originId, int destinationId) {
		for (Edge edge : edgeList) {
			if (edge.origin.id == originId && edge.destination.id == destinationId)
				return true;
		}
		return false;
	}

	public Node findNode(int id) {
		for (Node node : nodeList) {
			if (node.id == id)
				return node;
		}
		return null;
	}

	public void save(String pathname) {
		try {
			File file = new File(pathname);
			FileWriter out = new FileWriter(file);
			out.write(nodeList.size() + " " + edgeList.size() + "\n");
			for (Node node : nodeList) {
				out.write(node.id + " " + node.position.x + " " + node.position.y + "\n");
			}
			for (Edge edge : edgeList) {
				out.write(edge.origin.id + " " + edge.destination.id + " " + edge.weight + "\n");
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static AdjacencyList load(String pathname) {
		AdjacencyList graph = new AdjacencyList();
		try {
			File file = new File(pathname);
			Scanner in = new Scanner(file);
			int n = in.nextInt();
			int e = in.nextInt();
			for (int i = 0; i < n; i++) {
				int id = in.nextInt();
				double x = in.nextDouble();
				double y = in.nextDouble();
				graph.addNode(new Node(id, y, x));
			}
			for (int i = 0; i < e; i++) {
				int oid = in.nextInt();
				int did = in.nextInt();
				double weight = in.nextDouble();
				graph.addEdge(graph.findNode(oid), graph.findNode(did), weight);
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return graph;

	}

}
