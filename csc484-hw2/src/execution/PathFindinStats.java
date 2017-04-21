package execution;

import java.util.List;
import java.util.Scanner;

import graph.AdjacencyList;
import graph.Edge;
import graph.Node;
import path_finding.AStar;
import path_finding.Dijkstra;
import path_finding.Euclidian;
import path_finding.Manhattan;

public class PathFindinStats {

	public static void main(String[] args) {

		Dijkstra dijkstra = new Dijkstra();
		AStar aStar = new AStar();

		Scanner in = new Scanner(System.in);

		System.out.println("Graph File: ");
		String filename = in.next();
		System.out.println("Loading graph...");
		AdjacencyList graph = AdjacencyList.load(filename);
		System.out.println("Loading complete");
		
		long dtime = 0;
		int dvisited = 0;
		double dlength = 0;
		
		long etime = 0;
		int evisited = 0;
		double elength = 0;
		
		long mtime = 0;
		int mvisited = 0;
		double mlength = 0;
		
		int nodes = graph.nodeList.size();
		
		int n = 100;

		for (int i = 0; i < n; i++) {
			System.out.println("Running experiment " + i + "...");
			Node start = graph.nodeList.get((int) (Math.random()*nodes));
			Node end = graph.nodeList.get((int) (Math.random()*nodes));
			
			List<Edge> dpath = dijkstra.path(graph,start , end);
			dtime += dijkstra.time;
			dvisited += dijkstra.visited;
			dlength += dijkstra.length;

			List<Edge> epath = aStar.path(graph, start, end, new Euclidian());
			etime += aStar.time;
			evisited += aStar.visited;
			elength += aStar.length;

			List<Edge> mpath = aStar.path(graph, start, end, new Manhattan());
			mtime += aStar.time;
			mvisited += aStar.visited;
			mlength += aStar.length;

		}
		in.close();
		dtime /= n;
		dvisited /= n;
		dlength /= n;
		etime /= n;
		evisited /= n;
		elength /= n;
		mtime /= n;
		mvisited /= n;
		mlength /= n;
		System.out.println(dtime + " " + dvisited + " " + dlength);
		System.out.println(etime + " " + evisited + " " + elength);
		System.out.println(mtime + " " + mvisited + " " + mlength);
		System.out.println("Exiting...");
	}
}
