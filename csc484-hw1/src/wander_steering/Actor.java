package wander_steering;

import algorithm.Wander;
import general.Kinematic;

public class Actor {

	private Kinematic kinematic;
	private Wander wander;
	private double maxSpeed;

	public Actor(double posx, double posy, Kinematic target, double maxSpeed) {
		this.maxSpeed = maxSpeed;
		kinematic = new Kinematic(posx, posy);
		wander = new Wander(kinematic, Math.PI * 2, Math.PI / 8, Math.PI / 32, Math.PI / 8, 550, 500, Math.PI / 16, 0,
				400);
	}

	public Kinematic getKinematic() {
		return kinematic;
	}

	public void update(double time) {
		kinematic.update(wander.getSteering(), maxSpeed, time);
	}
}
