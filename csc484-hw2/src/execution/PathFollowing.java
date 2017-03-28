package execution;

import general.Actor;
import general.Breadcrumbs;
import general.Kinematic;
import general.Vector;
import graph.AdjacencyList;
import graph.Node;
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

	private static long timestamp;

	public static void main(String[] args) {
		center = new Kinematic(viewWidth / 2, viewHeight / 2);
		// character = new PathingActor(center.position.x, center.position.y,
		// null, 400);
		breadcrumbs = new Breadcrumbs(30, 0.1);

		PApplet.main("execution.PathFollowing");
	}

	public void settings() {
		size(viewWidth, viewHeight);
	}

	public void setup() {
		img = loadImage("living_room.png");
		fill(120);
		timestamp = System.nanoTime();
	}

	public void draw() {
		long timestampPrev = timestamp;
		timestamp = System.nanoTime();
		double dt = (timestamp - timestampPrev) / 1000000000.0;
		image(img, 0, 0);

		// character.update(dt);
		// breadcrumbs.add(character.getKinematic().position, timestamp);
		// renderBreadcrumbs(breadcrumbs);
		// renderActor(character);
	}

	public void mousePressed() {
		// character.setTarget(new Kinematic(mouseX, viewHeight - mouseY));
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

	private void buildTileGraph() {
		int tileWidth = 2 * characterRadius;
		int xtiles = viewWidth / tileWidth;
		int ytiles = viewHeight / tileWidth;
		AdjacencyList tileGraph = new AdjacencyList();
		boolean[][] tiles = new boolean[xtiles][ytiles];

		loadPixels();
		for (int i = 0; i < viewHeight; i++) {
			for (int j = 0; j < viewWidth; j++) {
				if (pixels[i * viewWidth + j] < 100) {
					tiles[j / tileWidth][i / tileWidth] = true;
				}
			}
		}
		for (int i = 0; i < ytiles; i++) {
			for (int j = 0; j < xtiles; j++) {
				if (!tiles[j][i]) {
					tileGraph.addNode(new Node((i + "," + j).hashCode(), (ytiles - i) * tileWidth, j * tileWidth + characterRadius));
				}
			}
		}
	}
}
