package execution;

import general.Actor;
import general.Breadcrumbs;
import general.Kinematic;
import general.Vector;
import graph.AdjacencyList;
import graph.Edge;
import graph.Node;
import path_finding.AStar;
import path_finding.Manhattan;
import path_finding.Path;
import processing.core.PApplet;
import processing.core.PImage;

public class PathFollowing extends PApplet {

	public static final int viewWidth = 800;
	public static final int viewHeight = 600;
	public static final int characterRadius = 10;

	private static PathingActor character;
	private static Kinematic center;
	private static Breadcrumbs breadcrumbs;

	public static PImage img;
	public static AdjacencyList tileGraph;

	public static AStar aStar;

	private static long timestamp;

	public static void main(String[] args) {
		center = new Kinematic(viewWidth / 2, viewHeight / 2);
		character = new PathingActor(center.position.x, center.position.y, 100);
		breadcrumbs = new Breadcrumbs(200, 0.1);

		aStar = new AStar();

		PApplet.main("execution.PathFollowing");
	}

	public void settings() {
		size(viewWidth, viewHeight);
	}

	public void setup() {
		img = loadImage("living_room1.png");
		image(img, 0, 0);
		buildTileGraph();
		timestamp = System.nanoTime();
	}

	public void draw() {
		long timestampPrev = timestamp;
		timestamp = System.nanoTime();
		double dt = (timestamp - timestampPrev) / 1000000000.0;
		image(img, 0, 0);
		//renderTileGraph();

		character.update(dt);
		breadcrumbs.add(character.getKinematic().position, timestamp);
		renderBreadcrumbs(breadcrumbs);
		renderActor(character);
	}

	public void mousePressed() {
		Path path = aStar.path(tileGraph, tileGraph.closestTo(character.getKinematic().position),
				tileGraph.closestTo(new Vector(mouseX, viewHeight - mouseY)), new Manhattan());
		System.out.println("Path found");
		character.setPath(path);
	}

	private void renderActor(Actor agent) {
		float x = (float) agent.getKinematic().position.x;
		float y = (float) (viewHeight - agent.getKinematic().position.y);
		float a = (float) agent.getKinematic().orientation;
		fill(0);
		triangle((float) (x - Math.sin(a) * characterRadius), (float) (y + Math.cos(a) * characterRadius),
				(float) (x + Math.sin(a) * characterRadius), (float) (y - Math.cos(a) * characterRadius),
				(float) (x + 2 * Math.cos(a) * characterRadius * 0.75),
				(float) (y + 2 * Math.sin(a) * characterRadius * 0.75));
		fill(255);
		ellipse(x, y, 2.0f * characterRadius, 2.0f * characterRadius);
	}

	private void renderBreadcrumbs(Breadcrumbs breadcrumbs) {
		fill(0);
		for (Vector crumb : breadcrumbs) {
			ellipse((float) crumb.x, (float) (viewHeight - crumb.y), 0.3f * characterRadius, 0.3f * characterRadius);
		}
	}

	@SuppressWarnings("unused")
	private void renderTileGraph() {
		for (Node node : tileGraph.nodeList) {
			ellipse((float) node.position.x, (float) (viewHeight - node.position.y), 3f, 3f);
		}
		for (Edge edge : tileGraph.edgeList) {
			line((float) edge.origin.position.x, (float) (viewHeight - edge.origin.position.y),
					(float) edge.destination.position.x, (float) (viewHeight - edge.destination.position.y));
		}
	}

	private void buildTileGraph() {
		int tileWidth = 2 * characterRadius;
		int xtiles = viewWidth / tileWidth;
		int ytiles = viewHeight / tileWidth;
		tileGraph = new AdjacencyList();
		boolean[][] tiles = new boolean[xtiles][ytiles];

		loadPixels();
		for (int i = 0; i < viewHeight; i++) {
			for (int j = 0; j < viewWidth; j++) {
				if (pixels[i * viewWidth + j] < color(100)) {
					tiles[j / tileWidth][i / tileWidth] = true;
				}
			}
		}
		for (int i = 0; i < ytiles; i++) {
			for (int j = 0; j < xtiles; j++) {
				if (!tiles[j][i]) {
					tileGraph.addNode(new Node(i * xtiles + j, (ytiles - i - 1) * tileWidth + characterRadius,
							j * tileWidth + characterRadius));
				}
			}
		}
		for (Node node : tileGraph.nodeList) {
			int id = node.id;
			if (id % xtiles != 0)
				tileGraph.addDoubleEdge(id, id - 1);
			if (id % xtiles != xtiles - 1)
				tileGraph.addDoubleEdge(id, id + 1);
			if (id >= xtiles)
				tileGraph.addDoubleEdge(id, id - xtiles);
			if (id < xtiles * (ytiles - 1))
				tileGraph.addDoubleEdge(id, id + xtiles);
		}
	}
}
