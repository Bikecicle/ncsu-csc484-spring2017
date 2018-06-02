package execution;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import behavior_tree.BehaviorTree;
import behavior_tree.Inverter;
import behavior_tree.ModifyBehavior;
import behavior_tree.Selector;
import behavior_tree.Sequence;
import behavior_tree.TestAttribute;
import decision_tree.Action;
import decision_tree.Decision;
import decision_tree.DecisionTree;
import decision_tree.Attribute;
import general.Actor;
import general.Apartment;
import general.Breadcrumbs;
import general.Kinematic;
import general.Room;
import general.Vector;
import graph.AdjacencyList;
import graph.Edge;
import graph.Node;
import learning.Example;
import path_finding.AStar;
import path_finding.Euclidian;
import path_finding.Path;
import path_following.Chase;
import path_following.PathTo;
import path_following.SteeringBehavior;
import processing.core.PApplet;
import processing.core.PImage;

public class BehaviorTreeView extends PApplet {

	public static final int viewWidth = 700;
	public static final int viewHeight = 700;
	public static final int characterRadius = 10;
	public static final int pingRadius = 50;
	public static final double pingRate = 0.9996;
	public static final double wanderRate = 0.996;
	public static final int maxAcceleration = 800;
	public static final int maxSpeed = 400;
	public static final int hearDistance = 15;

	private static DecisionActor character;
	private static DecisionTree characterTree;
	private static DecisionActor monster;
	private static BehaviorTree monsterTree;

	private static HashMap<String, Attribute> attributeDict;
	private static HashMap<String, SteeringBehavior> behaviorDict;

	private static boolean fridgePing;
	private static boolean tvPing;
	private static boolean hvacPing;
	private static Kinematic interest;

	private static Vector fridgePos;
	private static Vector tvPos;
	private static Vector hvacPos;
	private static Vector breakerPos;
	private static Vector characterSpawn;
	private static Vector monsterSpawn;

	private static Apartment apartment;

	private static Room livingRoom;
	private static Room hallway;
	private static Room kitchen;
	private static Room foyer;
	private static Room balcony;
	private static Room bedroom1;
	private static Room bathroom1;
	private static Room closet1;
	private static Room bedroom2;
	private static Room bathroom2;
	private static Room closet2;

	private static Breadcrumbs breadcrumbs;

	public static PImage img;
	public static AdjacencyList tileGraph;

	private static long initTimestamp;
	private static long timestamp;
	private static double dt;
	private static int frameCount = 0;

	private static ObjectOutputStream dataOut;
	private static String dataFileName = "data.txt";
	private static int dataCount = 0;

	public static void main(String[] args) {
		fridgePing = false;
		tvPing = false;
		hvacPing = false;
		interest = new Kinematic();
		interest.position = new Vector(viewWidth * Math.random(), viewHeight * Math.random());

		constructWorld();

		character = new DecisionActor(characterSpawn.x, characterSpawn.y, 100);
		monster = new DecisionActor(monsterSpawn.x, monsterSpawn.y, 80);
		breadcrumbs = new Breadcrumbs(20, 0.1);
		
		try {
			dataOut = new ObjectOutputStream(new FileOutputStream(dataFileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PApplet.main("execution.BehaviorTreeView");
	}

	private static void constructWorld() {
		fridgePos = new Vector(125, viewHeight - 160);
		tvPos = new Vector(280, viewHeight - 305);
		hvacPos = new Vector(545, viewHeight - 85);
		breakerPos = new Vector(92, viewHeight - 233);
		characterSpawn = new Vector(200, viewHeight - 385);
		monsterSpawn = new Vector(530, viewHeight - 230);

		livingRoom = new Room(new Vector(239, viewHeight - 521), new Vector(468, viewHeight - 19));
		hallway = new Room(new Vector(29, viewHeight - 256), new Vector(239, viewHeight - 187));
		kitchen = new Room(new Vector(70, viewHeight - 187), new Vector(240, viewHeight - 20));
		foyer = new Room(new Vector(469, viewHeight - 100), new Vector(572, viewHeight - 21));
		balcony = new Room(new Vector(238, viewHeight - 666), new Vector(423, viewHeight - 524));
		bedroom1 = new Room(new Vector(29, viewHeight - 670), new Vector(238, viewHeight - 426));
		bathroom1 = new Room(new Vector(31, viewHeight - 424), new Vector(136, viewHeight - 258));
		closet1 = new Room(new Vector(135, viewHeight - 425), new Vector(235, viewHeight - 315));
		bedroom2 = new Room(new Vector(469, viewHeight - 522), new Vector(668, viewHeight - 272));
		bathroom2 = new Room(new Vector(575, viewHeight - 272), new Vector(670, viewHeight - 104));
		closet2 = new Room(new Vector(471, viewHeight - 272), new Vector(572, viewHeight - 165));

		apartment = new Apartment();
		apartment.add(livingRoom);
		apartment.add(hallway);
		apartment.add(kitchen);
		apartment.add(foyer);
		apartment.add(balcony);
		apartment.add(bedroom1);
		apartment.add(bathroom1);
		apartment.add(closet1);
		apartment.add(bedroom2);
		apartment.add(bathroom2);
		apartment.add(closet2);
	}

	private void constructDictionaries() {
		attributeDict = new HashMap<String, Attribute>();
		attributeDict.put("fridge", new Attribute() {

			@Override
			public boolean getValue() {
				return fridgePing;
			}
		});
		attributeDict.put("tv", new Attribute() {

			@Override
			public boolean getValue() {
				return tvPing;
			}
		});
		attributeDict.put("hvac", new Attribute() {

			@Override
			public boolean getValue() {
				return hvacPing;
			}
		});
		attributeDict.put("exposed", new Attribute() {

			@Override
			public boolean getValue() {
				return !closet1.contains(character.getKinematic().position);
			}
		});
		attributeDict.put("audible", new Attribute() {

			@Override
			public boolean getValue() {
				AStar astar = new AStar();
				Euclidian euclidian = new Euclidian();
				Path path = astar.path(tileGraph, tileGraph.closestTo(monster.getKinematic().position),
						tileGraph.closestTo(character.getKinematic().position), euclidian);
				return path.size() <= hearDistance;
			}
		});
		attributeDict.put("visible", new Attribute() {

			@Override
			public boolean getValue() {
				return apartment.locate(character.getKinematic().position) == apartment
						.locate(monster.getKinematic().position);
			}
		});
		
		try {
			List<String> attributes = new ArrayList<String>();
			attributes.add("exposed");
			attributes.add("audible");
			attributes.add("visible");
			dataOut.writeObject(attributes);
			dataOut.reset();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		behaviorDict = new HashMap<String, SteeringBehavior>();
		behaviorDict.put("goToFridge",
				new PathTo(character.getKinematic(), tileGraph, fridgePos, maxAcceleration, maxSpeed));
		behaviorDict.put("goToTv", new PathTo(character.getKinematic(), tileGraph, tvPos, maxAcceleration, maxSpeed));
		behaviorDict.put("goToHvac",
				new PathTo(character.getKinematic(), tileGraph, hvacPos, maxAcceleration, maxSpeed));
		behaviorDict.put("goToBreaker",
				new PathTo(character.getKinematic(), tileGraph, breakerPos, maxAcceleration, maxSpeed));
		behaviorDict.put("goToSafezone",
				new PathTo(character.getKinematic(), tileGraph, characterSpawn, maxAcceleration, maxSpeed));
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

	private void buildMonsterTree() {
		Selector selector1 = new Selector();
		Sequence sequence1 = new Sequence();
		Sequence sequence2 = new Sequence();
		Sequence sequence3 = new Sequence();
		Inverter inverter1 = new Inverter();
		TestAttribute exposed = new TestAttribute(attributeDict.get("exposed"));
		TestAttribute visible = new TestAttribute(attributeDict.get("visible"));
		TestAttribute audible = new TestAttribute(attributeDict.get("audible"));
		ModifyBehavior chase = new ModifyBehavior(new Chase(monster.getKinematic(), tileGraph, character.getKinematic(),
				maxAcceleration / 2, maxSpeed / 2), monster);
		ModifyBehavior investigate = new ModifyBehavior(new Chase(monster.getKinematic(), tileGraph,
				character.getKinematic(), maxAcceleration / 8, maxSpeed / 8), monster);
		ModifyBehavior wander = new ModifyBehavior(
				new Chase(monster.getKinematic(), tileGraph, interest, maxAcceleration / 2, maxSpeed / 2), monster);

		selector1.children.add(sequence1);
		sequence1.children.add(inverter1);
		inverter1.child = exposed;
		sequence1.children.add(wander);
		selector1.children.add(sequence3);
		sequence3.children.add(visible);
		sequence3.children.add(chase);
		selector1.children.add(sequence2);
		sequence2.children.add(audible);
		sequence2.children.add(investigate);

		wander.id = "wander";
		investigate.id = "investigate";
		chase.id = "chase";

		monsterTree = new BehaviorTree(selector1);
	}

	public void settings() {
		size(viewWidth, viewHeight);
	}

	public void setup() {
		frameRate(1000);
		img = loadImage("living_room2.png");
		image(img, 0, 0);
		buildTileGraph();
		renderTileGraph();
		constructDictionaries();
		buildCharacterTree();
		buildMonsterTree();
		timestamp = System.nanoTime();
		initTimestamp = timestamp;
	}

	public void draw() {
		long timestampPrev = timestamp;
		timestamp = System.nanoTime();
		dt = (timestamp - timestampPrev) / 1000000000.0;
		frameCount++;
		System.out.println(((timestamp - initTimestamp) / frameCount));
		image(img, 0, 0);

		character.setBehavior(behaviorDict.get(characterTree.makeDecision(attributeDict)), "boop");
		monsterTree.run();
		randomizePings();
		
		if (monster.action != null) {
			Example example = new Example(monster.action);
			example.attributes.put("exposed", attributeDict.get("exposed").getValue());
			example.attributes.put("audible", attributeDict.get("audible").getValue());
			example.attributes.put("visible", attributeDict.get("visible").getValue());

			try {
				dataOut.writeObject(example);
				dataOut.reset();
				System.out.println(dataCount++);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		character.update(dt);
		monster.update(dt);
		breadcrumbs.add(character.getKinematic().position, timestamp);
		handleCollisions();
		renderPings();
		// renderBreadcrumbs(breadcrumbs);
		renderActor(character);
		renderActor(monster);
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

	@SuppressWarnings("unused")
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
		if (Math.random() > pingRate)
			fridgePing = true;
		if (Math.random() > pingRate)
			tvPing = true;
		if (Math.random() > pingRate)
			hvacPing = true;
		if (Math.random() > wanderRate)
			interest.position = new Vector(viewWidth * Math.random(), viewHeight * Math.random());

	}

	private void handleCollisions() {
		if (character.getKinematic().position.isCloseTo(fridgePos, 12))
			fridgePing = false;
		if (character.getKinematic().position.isCloseTo(tvPos, 12))
			tvPing = false;
		if (character.getKinematic().position.isCloseTo(hvacPos, 12))
			hvacPing = false;
		if (character.getKinematic().position.isCloseTo(breakerPos, 10)) {
			fridgePing = false;
			tvPing = false;
			hvacPing = false;
		}
		if (character.getKinematic().position.isCloseTo(monster.getKinematic().position, 10)) {
			character.getKinematic().position = characterSpawn.copy();
			character.setBehavior(null, null);
			monster.getKinematic().position = monsterSpawn.copy();
			monster.setBehavior(null, null);
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
