package general;

import general.util;

public class Align {
	
	private Kinematic character;
	private Kinematic target;
	
	private double maxAngularAcceleration;
	private double maxRotation;
	private double targetRadius;
	private double slowRadius;
	private double timeToTarget = 0.1;
	
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
		rotation = util.mapToRange(rotation);
		double rotationMagnitude = Math.abs(rotation);
		if (rotationMagnitude < targetRadius)
			return null;
		double targetRotation = 0;
		if (rotationMagnitude < slowRadius)
			targetRotation = maxRotation;
		else
			targetRotation = maxRotation * rotationMagnitude / slowRadius;
		targetRotation *= rotation / rotationMagnitude;
		
	}

}
