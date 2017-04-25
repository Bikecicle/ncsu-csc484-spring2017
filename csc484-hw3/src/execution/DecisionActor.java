package execution;

import general.Actor;
import general.Kinematic;
import path_following.SteeringBehavior;

public class DecisionActor implements Actor {

	private Kinematic kinematic;
	private SteeringBehavior behavior;
	private double maxSpeed;
	private boolean isFollowingPath;

	public DecisionActor(double posx, double posy, double maxSpeed) {
		this.maxSpeed = maxSpeed;
		kinematic = new Kinematic(posx, posy);
		behavior = null;
		isFollowingPath = false;
	}

	public Kinematic getKinematic() {
		return kinematic;
	}

	public void update(double time) {
		if (behavior != null)
			kinematic.update(behavior.getSteering(), maxSpeed, time);
		if (kinematic.velocity.magnitude() > 0)
			kinematic.orientation = kinematic.velocity.direction();
	}

	public void setBehavior(SteeringBehavior behavior) {
		this.behavior = behavior;
	}

	public boolean isFollowingPath() {
		return isFollowingPath;
	}
}
