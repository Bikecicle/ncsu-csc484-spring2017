package execution;

import general.Actor;
import general.Kinematic;
import path_finding.Path;
import path_following.FollowPath;

public class PathingActor implements Actor {

	private Kinematic kinematic;
	private FollowPath follow;
	private Path path;
	private double maxSpeed;

	public PathingActor(double posx, double posy, Path path, double maxSpeed) {
		this.maxSpeed = maxSpeed;
		kinematic = new Kinematic(posx, posy);
		this.path = path;
		follow = new FollowPath(kinematic, path, 100);
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
		this.path = path;
	}
}
