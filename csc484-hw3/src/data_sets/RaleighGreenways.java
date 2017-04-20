package data_sets;

import graph.AdjacencyList;
import graph.Node;

public class RaleighGreenways {

	public static void main(String[] args) {
		AdjacencyList graph = new AdjacencyList();
		
		graph.addNode(new Node(31, 22, 9));
		graph.addNode(new Node(32, 23, 10));
		graph.addNode(new Node(34, 23, 11));
		graph.addNode(new Node(77, 23, 14));
		graph.addNode(new Node(78, 21, 14));
		graph.addNode(new Node(35, 22, 15));
		graph.addNode(new Node(76, 23, 16));
		graph.addNode(new Node(70, 22, 17));
		graph.addNode(new Node(74, 23, 17));
		graph.addNode(new Node(39, 23, 20));
		graph.addNode(new Node(42, 24, 25));
		graph.addNode(new Node(90, 29, 29));
		graph.addNode(new Node(41, 22, 24));
		graph.addNode(new Node(85, 6, 22));
		graph.addNode(new Node(1, 4, 21));
		graph.addNode(new Node(4, 8, 20));
		graph.addNode(new Node(6, 9, 21));
		graph.addNode(new Node(7, 8, 17));
		graph.addNode(new Node(19, 15, 14));
		graph.addNode(new Node(24, 15, 13));
		graph.addNode(new Node(15, 15, 10));
		graph.addNode(new Node(94, 18, 11));
		graph.addNode(new Node(28, 19, 9));
		
		graph.addDoubleEdge(31, 32);
		graph.addDoubleEdge(32, 34);
		graph.addDoubleEdge(34, 77);
		graph.addDoubleEdge(77, 78);
		graph.addDoubleEdge(78, 35);
		graph.addDoubleEdge(35, 76);
		graph.addDoubleEdge(76, 74);
		graph.addDoubleEdge(74, 70);
		graph.addDoubleEdge(74, 39);
		graph.addDoubleEdge(39, 42);
		graph.addDoubleEdge(42, 90);
		graph.addDoubleEdge(42, 41);
		graph.addDoubleEdge(85, 1);
		graph.addDoubleEdge(85, 4);
		graph.addDoubleEdge(4, 6);
		graph.addDoubleEdge(41, 19);
		graph.addDoubleEdge(19, 7);
		graph.addDoubleEdge(19, 24);
		graph.addDoubleEdge(24, 15);
		graph.addDoubleEdge(24, 94);
		graph.addDoubleEdge(94, 28);
		graph.addDoubleEdge(94, 78);
		graph.addDoubleEdge(76, 77);
		graph.addDoubleEdge(41, 85);
		
		graph.save("raleigh_greenways.txt");
	}
}
