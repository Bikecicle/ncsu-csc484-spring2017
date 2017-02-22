package algorithm;

import java.util.List;

import general.Kinematic;
import general.SteeringOutput;
import general.Vector;

public class CollisionAvoidance implements SteeringBehavior {

	public Kinematic character;
	public List<Kinematic> targets;
	public double maxAcceleration;
	public double radius = 5;

	public CollisionAvoidance(Kinematic character, List<Kinematic> targets, double maxAcceleration) {
		this.character = character;
		this.targets = targets;
		this.maxAcceleration = maxAcceleration;
	}

	public SteeringOutput getSteering() {
		SteeringOutput steering = new SteeringOutput();
		double shortestTime = Double.POSITIVE_INFINITY;
		Kinematic firstTarget = null;
		double firstMinSeparation = 0;
		double firstDistance = 0;
		Vector firstRelativePos = null;
		Vector firstRelativeVel = null;
		for (Kinematic target : targets) {
			Vector relativePos = target.position.subtract(character.position);
			Vector relativeVel = target.velocity.subtract(character.velocity);
			double relativeSpeed = relativeVel.magnitude();
			double timeToCollision = -relativePos.innerProduct(relativeVel) / (relativeSpeed * relativeSpeed);
			double distance = relativePos.magnitude();
			double minSeparation = relativePos.add(relativeVel.scale(timeToCollision)).magnitude();
			if (minSeparation > 2 * radius)
				continue;
			if (timeToCollision > 0 && timeToCollision < shortestTime) {
				shortestTime = timeToCollision;
				firstTarget = target;
				firstMinSeparation = minSeparation;
				firstDistance = distance;
				firstRelativePos = relativePos;
				firstRelativeVel = relativeVel;
			}
		}
		if (firstTarget == null)
			return steering;
		Vector relativePos = null;
		if (firstMinSeparation <= 0 || firstDistance < 2 * radius)
			relativePos = firstTarget.position.subtract(character.position);
		else
			relativePos = firstRelativePos.add(firstRelativeVel.scale(shortestTime));
		steering.linear = relativePos.normalize().scale(maxAcceleration);
		return steering;
	}

}
