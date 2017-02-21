package arrive_steering;

import general.Arrive;
import general.Kinematic;
import general.Static;

public class Actor {

	private Kinematic kinematic;
	private Arrive arrive;
	private double maxSpeed;

	public Actor(double posx, double posy, Kinematic target, double maxSpeed) {
		this.maxSpeed = maxSpeed;
		kinematic = new Kinematic(posx, posy);
		arrive = new Arrive(kinematic, target, 800, maxSpeed, 1, 200);

	}

	public Kinematic getKinematic() {
		return kinematic;
	}

	public void update(double time) {
		kinematic.update(arrive.getSteering(), maxSpeed, time);
	}

	public Static getTarget() {
		return arrive.getTarget();
	}

	public void setTarget(Kinematic target) {
		arrive.setTarget(target);
	}
}
