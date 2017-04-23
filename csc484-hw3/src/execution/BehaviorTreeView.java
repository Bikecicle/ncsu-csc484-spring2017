package execution;

import java.util.HashMap;

import decision_tree.Action;
import decision_tree.Decision;
import decision_tree.DecisionTree;
import decision_tree.Parameter;
import general.Actor;
import general.Breadcrumbs;
import general.Vector;
import graph.AdjacencyList;
import graph.Edge;
import graph.Node;
import path_finding.AStar;
import path_finding.Euclidian;
import path_following.PathTo;
import path_following.SteeringBehavior;
import processing.core.PApplet;
import processing.core.PImage;

public class BehaviorTreeView extends PApplet {

	public static final int viewWidth = 700;
	public static final int viewHeight = 700;
	public static final int characterRadius = 10;
	public static final int pingRadius = 50;

	private static DecisionActor character;
	private static DecisionTree characterTree;

	private static HashMap<String, Parameter> paramDict;
	private static HashMap<String, SteeringBehavior> behaviorDict;

	private static boolean fridgePing;
	private static boolean tvPing;
	private static boolean hvacPing;

	private static Vector fridgePos;
	private static Vector tvPos;
	private static Vector hvacPos;
	private static Vector breakerPos;
	private static Vector safezonePos;

	private static Breadcrumbs breadcrumbs;

	public static PImage img;
	public static AdjacencyList tileGraph;

	public static AStar aStar;
	public static Euclidian euclidian;

	private static long timestamp;
	private static double dt;
	private static double aidt;

	public static void main(String[] args) {
		fridgePing = false;
		tvPing = false;
		hvacPing = false;

		fridgePos = new Vector(125, viewHeight - 160);
		tvPos = new Vector(280, viewHeight - 305);
		hvacPos = new Vector(545, viewHeight - 85);
		breakerPos = new Vector(92, viewHeight - 233);
		safezonePos = new Vector(200, viewHeight - 385);

		character = new DecisionActor(safezonePos.x, safezonePos.y, 100);
		breadcrumbs = new Breadcrumbs(20, 0.1);

		PApplet.main("execution.BehaviorTreeView");
	}

	private void constructDictionaries() {
		paramDict = new HashMap<String, Parameter>();
		paramDict.put("fridge", new Parameter() {

			@Override
			public boolean getValue() {
				return fridgePing;
			}
		});
		paramDict.put("tv", new Parameter() {

			@Override
			public boolean getValue() {
				return tvPing;
			}
		});
		paramDict.put("hvac", new Parameter() {

			@Override
			public boolean getValue() {
				return hvacPing;
			}
		});

		behaviorDict = new HashMap<String, SteeringBehavior>();
		behaviorDict.put("goToFridge", new PathTo(character.getKinematic(), tileGraph, fridgePos));
		behaviorDict.put("goToTv", new PathTo(character.getKinematic(), tileGraph, tvPos));
		behaviorDict.put("goToHvac", new PathTo(character.getKinematic(), tileGraph, hvacPos));
		behaviorDict.put("goToBreaker", new PathTo(character.getKinematic(), tileGraph, breakerPos));
		behaviorDict.put("goToSafezone", new PathTo(character.getKinematic(), tileGraph, safezonePos));
	}

	private void buildCharacterTree() {
		characterTree = new DecisionTree(new Decision("fridge", "a"));
		characterTree.add(new Decision("tv", "a1"), "a", true);
		characterTree.add(new Action("goToBreaker", "a1b"), "a1", true);
		characterTree.add(new Decision("hvac", "a2"), "a1", false);
		characterTree.add(new Action("goToBreaker", "a2b"), "a2", true);
		characterTree.add(new Action("goToFridge", "a3"), "a2", false);
		characterTree.add(new Decision("tv", "b"), "a", false);
		characterTree.add(new Decision("hvac", "b1"), "b", true);
		characterTree.add(new Action("goToBreaker", "b1b"), "b1", true);
		characterTree.add(new Action("goToTv", "b2"), "b1", false);
		characterTree.add(new Decision("hvac", "c"), "b", false);
		characterTree.add(new Action("goToHvac", "c1"), "c", true);
		characterTree.add(new Action("goToSafezone", "d"), "c", false);
	}

	public void settings() {
		size(viewWidth, viewHeight);
	}

	public void setup() {
		img = loadImage("living_room.png");
		image(img, 0, 0);
		buildTileGraph();
		renderTileGraph();
		constructDictionaries();
		buildCharacterTree();
		timestamp = System.nanoTime();
		character.setBehavior(behaviorDict.get(characterTree.makeDecision(paramDict)));
	}

	public void draw() {
		long timestampPrev = timestamp;
		timestamp = System.nanoTime();
		dt = (timestamp - timestampPrev) / 1000000000.0;
		aidt += dt;
		image(img, 0, 0);
		
		if (aidt > 0.5) {
			character.setBehavior(behaviorDict.get(characterTree.makeDecision(paramDict)));
			randomizePings();
			aidt = 0;
		}
		
		character.update(dt);
		breadcrumbs.add(character.getKinematic().position, timestamp);
		updatePings();
		renderPings();
		renderBreadcrumbs(breadcrumbs);
		renderActor(character);
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

	private void renderTileGraph() {
		for (Node node : tileGraph.nodeList) {
			ellipse((float) node.position.x, (float) (viewHeight - node.position.y), 3f, 3f);
		}
		for (Edge edge : tileGraph.edgeList) {
			line((float) edge.origin.position.x, (float) (viewHeight - edge.origin.position.y),
					(float) edge.destination.position.x, (float) (viewHeight - edge.destination.position.y));
		}
	}

	private void renderPings() {
		if (fridgePing)
			ellipse((int) fridgePos.x, (int) (viewHeight - fridgePos.y), pingRadius, pingRadius);
		if (tvPing)
			ellipse((int) tvPos.x, (int) (viewHeight - tvPos.y), pingRadius, pingRadius);
		if (hvacPing)
			ellipse((int) hvacPos.x, (int) (viewHeight - hvacPos.y), pingRadius, pingRadius);
	}
	
	private void randomizePings() {
		double p = 0.98;
		if (Math.random() > p)
			fridgePing = true;
		if (Math.random() > p)
			tvPing = true;
		if (Math.random() > p)
			hvacPing = true;
	}
	
	private void updatePings() {
		if (character.getKinematic().position.isCloseTo(fridgePos, 10))
			fridgePing = false;
		if (character.getKinematic().position.isCloseTo(tvPos, 10))
			tvPing = false;
		if (character.getKinematic().position.isCloseTo(hvacPos, 10))
			hvacPing = false;
		if (character.getKinematic().position.isCloseTo(breakerPos, 10)) {
			fridgePing = false;
			tvPing = false;
			hvacPing = false;
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
