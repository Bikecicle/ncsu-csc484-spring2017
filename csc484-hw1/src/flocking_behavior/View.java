package flocking_behavior;

import general.Actor;
import processing.core.PApplet;
import wander_steering.WanderActor;

public class View extends PApplet {

	public static final int viewWidth = 800;
	public static final int viewHeight = 600;
	public static final int characterRadius = 4;
	public static final int boidCount = 200;
	public static final int sharkCount = 10;

	private static Actor[] boids;
	private static Actor[] sharks;

	private static long timestamp;

	public static void main(String[] args) {
		boids = new Actor[boidCount];
		sharks = new Actor[sharkCount];
		for (int i = 0; i < boidCount; i++) {
			boids[i] = new BoidActor(Math.random() * 800, Math.random() * 600, boids, sharks);
		}
		for (int i = 0; i < sharkCount; i++) {
			sharks[i] = new WanderActor(Math.random() * 800, Math.random() * 600);
		}
		for (int i = 0; i < boidCount; i++) {
			((BoidActor) boids[i]).selectTargets();
		}
		PApplet.main("flocking_behavior.View");
	}

	public void settings() {
		size(viewWidth, viewHeight);
	}

	public void setup() {
		fill(120);
		timestamp = System.nanoTime();
	}

	public void draw() {
		long timestampPrev = timestamp;
		timestamp = System.nanoTime();
		double dt = (timestamp - timestampPrev) / 1000000000.0;
		background(120);

		for (Actor boid : boids) {
			boid.update(dt);
			renderActor(boid);
		}
		for (Actor shark : sharks) {
			shark.update(dt);
			renderActor(shark);
		}
	}

	public void mousePressed() {
		for (int i = 0; i < boidCount; i++) {
			((BoidActor) boids[i]).scramble();
		}
	}

	private void renderActor(Actor agent) {
		float x = (float) agent.getKinematic().position.x;
		float y = (float) (viewHeight - agent.getKinematic().position.y);
		float a = (float) agent.getKinematic().orientation;
		fill(0);
		triangle((float) (x - Math.sin(a) * characterRadius), (float) (y - Math.cos(a) * characterRadius),
				(float) (x + Math.sin(a) * characterRadius), (float) (y + Math.cos(a) * characterRadius),
				(float) (x + 2 * Math.cos(a) * characterRadius * 0.75),
				(float) (y - 2 * Math.sin(a) * characterRadius * 0.75));
		fill(255);
		ellipse(x, y, 2.0f * characterRadius, 2.0f * characterRadius);

	}

}
