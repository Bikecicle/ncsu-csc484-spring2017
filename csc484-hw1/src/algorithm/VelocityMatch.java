package algorithm;

import general.Kinematic;
import general.SteeringOutput;

public class VelocityMatch implements SteeringBehavior {

	public Kinematic character;
	public Kinematic target;
	public double maxAcceleration;
	public double timeToTarget = 0.1;

	public VelocityMatch(Kinematic character, Kinematic target, double maxAcceleration) {
		this.character = character;
		this.target = target;
		this.maxAcceleration = maxAcceleration;
	}

	public SteeringOutput getSteering() {
		SteeringOutput steering = new SteeringOutput();
		steering.linear = target.velocity.subtract(character.velocity);
		steering.linear = steering.linear.scale(1.0 / timeToTarget);
		if (steering.linear.magnitude() > maxAcceleration)
			steering.linear = steering.linear.normalize().scale(maxAcceleration);
		steering.angular = 0;
		return steering;
	}
}
