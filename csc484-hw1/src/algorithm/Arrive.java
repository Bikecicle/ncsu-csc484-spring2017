package algorithm;

import general.Kinematic;
import general.SteeringOutput;
import general.Vector;

public class Arrive implements SteeringBehavior {

	public Kinematic character;
	public Kinematic target;
	public double maxAcceleration;
	public double maxSpeed;
	public double targetRadius;
	public double slowRadius;
	public final double timeToTarget = 0.01;

	public Arrive(Kinematic character, Kinematic target, double maxAccleration, double maxSpeed, double targetRadius,
			double slowRadius) {
		this.character = character;
		this.target = target;
		this.maxAcceleration = maxAccleration;
		this.maxSpeed = maxSpeed;
		this.targetRadius = targetRadius;
		this.slowRadius = slowRadius;
	}

	public SteeringOutput getSteering() {
		SteeringOutput steering = new SteeringOutput();
		Vector direction = target.position.subtract(character.position);
		double distance = direction.magnitude();
		if (distance < targetRadius)
			return steering;
		double targetSpeed = 0;
		if (distance > slowRadius)
			targetSpeed = maxSpeed;
		else
			targetSpeed = maxSpeed * distance / slowRadius;
		Vector targetVelocity = direction.normalize().scale(targetSpeed);
		steering.linear = targetVelocity.subtract(character.velocity).scale(1.0 / timeToTarget);
		if (steering.linear.magnitude() > maxAcceleration)
			steering.linear = steering.linear.normalize().scale(maxAcceleration);
		steering.angular = 0;
		return steering;
	}
}
