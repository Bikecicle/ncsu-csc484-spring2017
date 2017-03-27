package path_following;

import general.Kinematic;
import general.SteeringOutput;
import general.Vector;
import path_finding.Path;

public class FollowPath extends Seek implements SteeringBehavior {
	
	public Path path;
	public int pathOffset = 1;
	public double currentParam;
	public double predictTime = 0.1;

	public FollowPath(Kinematic character, Path path, double maxAcceleration) {
		super(character, new Kinematic(), maxAcceleration);
		this.path = path;
	}

	@Override
	public SteeringOutput getSteering() {
		Vector futurePos = character.position.add(character.velocity.scale(predictTime));
		currentParam = path.getParam(futurePos, currentParam);
		double targetParam = currentParam + pathOffset;
		target.position = path.getPosition(targetParam);
		return super.getSteering();
	}
}
