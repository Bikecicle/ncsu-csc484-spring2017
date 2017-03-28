package execution;

import general.Actor;
import general.Kinematic;
import path_finding.Path;
import path_following.FollowPath;

public class PathingActor implements Actor {

	private Kinematic kinematic;
	private FollowPath follow;
	private double maxSpeed;

	public PathingActor(double posx, double posy, double maxSpeed) {
		this.maxSpeed = maxSpeed;
		kinematic = new Kinematic(posx, posy);
		follow = new FollowPath(kinematic);
	}

	public Kinematic getKinematic() {
		return kinematic;
	}

	public void update(double time) {
		kinematic.update(follow.getSteering(), maxSpeed, time);
		if (kinematic.velocity.magnitude() > 0)
			kinematic.orientation = kinematic.velocity.direction();
	}
	
	public void setPath(Path path) {
		follow.path = path;
	}
}
