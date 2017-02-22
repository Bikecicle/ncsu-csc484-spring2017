package algorithm;

import general.Kinematic;
import general.SteeringOutput;
import general.Vector;
import general.Util;

public class Align {
	
	public Kinematic character;
	public Kinematic target;
	
	public double maxAngularAcceleration;
	public double maxRotation;
	public double targetRadius;
	public double slowRadius;
	public double timeToTarget = 0.1;
	
	public Align(Kinematic character, Kinematic target, double maxAngularAcceleration, double maxRotation, double targetRadius, double slowRadius) {
		this.character = character;
		this.target = target;
		this.maxAngularAcceleration = maxAngularAcceleration;
		this.maxRotation = maxRotation;
		this.targetRadius = targetRadius;
		this.slowRadius = slowRadius;
	}
	
	public SteeringOutput getSteering() {
		SteeringOutput steering = new SteeringOutput();
		double rotation = target.orientation - character.orientation;
		rotation = Util.mapToRange(rotation);
		double rotationMagnitude = Math.abs(rotation);
		if (rotationMagnitude < targetRadius) {
			steering.linear = new Vector(0,0);
			steering.angular = 0;
			return steering;
		}
		double targetRotation = 0;
		if (rotationMagnitude < slowRadius)
			targetRotation = maxRotation;
		else
			targetRotation = maxRotation * rotationMagnitude / slowRadius;
		targetRotation *= rotation / rotationMagnitude;
		steering.angular = (targetRotation - character.rotation) / timeToTarget;
		double angularAcceleration = Math.abs(steering.angular);
		if (angularAcceleration > maxAngularAcceleration)
			steering.angular = steering.angular / angularAcceleration * maxAngularAcceleration;
		steering.linear = new Vector(0,0);
		return steering;
	}

}
