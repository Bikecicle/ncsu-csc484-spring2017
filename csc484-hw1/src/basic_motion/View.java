package basic_motion;

import general.Static;
import processing.core.PApplet;

public class View extends PApplet {

	public static final int viewWidth = 800;
	public static final int viewHeight = 600;
	public static final int characterRadius = 10;

	private static Actor character;

	private static long timestamp;
	
	private static Static t1;
	private static Static t2;
	private static Static t3;
	private static Static t4;
	
	public static void main(String[] args) {
		t1 = new Static(780, 20);
		t2 = new Static(780, 580);
		t3 = new Static(20, 580);
		t4 = new Static(20, 20);
		character = new Actor(20, 20, t1);
		PApplet.main("basic_motion.View");
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
		
		if (character.getKinematic().position.isCloseTo(t1.position, 1) && character.getTarget().equals(t1)) {
			character.setTarget(t2);
		} else if (character.getKinematic().position.isCloseTo(t2.position, 1) && character.getTarget().equals(t2)) {
			character.setTarget(t3);
		} else if (character.getKinematic().position.isCloseTo(t3.position, 1) && character.getTarget().equals(t3)) {
			character.setTarget(t4);
		} else if (character.getKinematic().position.isCloseTo(t4.position, 1) && character.getTarget().equals(t4)) {
			character.setTarget(t1);
		}
		
		character.update(dt);
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

}
