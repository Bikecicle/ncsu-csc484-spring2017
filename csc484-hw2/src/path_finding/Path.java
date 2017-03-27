package path_finding;

import java.util.ArrayList;
import general.Vector;
import graph.Edge;

public class Path extends ArrayList<Edge> {

	private static final long serialVersionUID = -52890127586107941L;

	public double getParam(Vector position, double lastParam) {
		double shortestDist = Double.POSITIVE_INFINITY;
		double param = 0;
		Edge closest = null;
		for (Edge edge : this) {
			param += edge.weight;
			if (param >= lastParam) {
				double dist = position.subtract(edge.destination.position).magnitude();
				if (dist < shortestDist) {
					shortestDist = dist;
					closest = edge;
				}
			}
		}
		for (Edge edge : this) {
			param += edge.weight;
			if (edge == closest)
				return param;
		}
		return -1;
	}

	public Vector getPosition(double param) {
		double currentParam = 0;
		for (Edge edge : this) {
			currentParam += edge.weight;
			if ( currentParam >= param ) 
				return edge.destination.position.copy();
		}
		return null;
	}

	/**
	 * public double getParam(double x, double y, double lastParam) { double
	 * distance = Double.POSITIVE_INFINITY; double edgeParam = 0; Edge closest =
	 * null; for (int i = currentEdge; i < this.size(); i++) { Edge edge =
	 * get(i); Vector A = new Vector(x - edge.origin.x, y - edge.origin.y);
	 * Vector B = new Vector(edge.destination.x - edge.origin.x,
	 * edge.destination.y - edge.origin.y); double a1 = A.innerProduct(B) /
	 * B.magnitude(); Vector A2 = A.subtract(B.normalize().scale(a1)); if (a1 <
	 * 0) { double a = A.magnitude(); if (a < distance) { closest = edge;
	 * distance = a; edgeParam = 0; } } else if (a1 > B.magnitude()) { double ab
	 * = A.subtract(B).magnitude(); if (ab < distance) { closest = edge;
	 * distance = ab; edgeParam = B.magnitude(); } } else { double a2 =
	 * A2.magnitude(); if (a2 < distance) { closest = edge; distance = a2;
	 * edgeParam = a1; } }
	 * 
	 * } double param = edgeParam; for (Edge edge : this) { if (edge == closest)
	 * break; param += edge.weight; } return param; }
	 */

}
