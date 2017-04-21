package path_following;

import general.Kinematic;
import general.SteeringOutput;
import general.Vector;
import path_finding.Path;

public class FollowPath extends Arrive implements SteeringBehavior {

	public Path path;
	public int pathOffset = 2;
	public int currentParam;
	public double predictTime = 0.1;

	public FollowPath(Kinematic character) {
		super(character, new Kinematic(), 800, 400, 1, 60);
		target.position = character.position.copy();
		path = null;
	}
	
	public FollowPath(Kinematic character, Path path) {
		super(character, new Kinematic(), 800, 400, 1, 60);
		target.position = character.position.copy();
		this.path = path;
	}

	@Override
	public SteeringOutput getSteering() {
		if (path != null) {
			Vector futurePos = character.position.add(character.velocity.scale(predictTime));
			currentParam = path.getParam(futurePos, currentParam);
			int targetParam = currentParam + pathOffset;
			Vector newTarget = path.getPosition(targetParam);
			if (newTarget == null)
				path = null;
			else
				target.position = newTarget;
		}
		return super.getSteering();
	}
}
