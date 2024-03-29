package wander_steering;

import wander_steering.WanderActor;
import general.Breadcrumbs;
import general.Kinematic;
import general.Vector;
import processing.core.PApplet;

public class View extends PApplet {

	public static final int viewWidth = 800;
	public static final int viewHeight = 600;
	public static final int characterRadius = 10;

	private static WanderActor character;
	private static Kinematic center;
	private static Breadcrumbs breadcrumbs;

	private static long timestamp;

	public static void main(String[] args) {
		center = new Kinematic(viewWidth / 2, viewHeight / 2);
		character = new WanderActor(center.position.x, center.position.y);
		breadcrumbs = new Breadcrumbs(100, 0.1);
		PApplet.main("wander_steering.View");
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

		character.update(dt);
		breadcrumbs.add(character.getKinematic().position, timestamp);
		renderBreadcrumbs(breadcrumbs);
		renderActor(character);
	}

	private void renderActor(WanderActor agent) {
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
	
	private void renderBreadcrumbs(Breadcrumbs breadcrumbs) {
		fill(0);
		for (Vector crumb : breadcrumbs) {
			ellipse((float) crumb.x, (float) (viewHeight - crumb.y), 0.3f * characterRadius, 0.3f * characterRadius);
		}
	}

}
