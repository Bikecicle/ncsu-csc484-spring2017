package algorithm;

import general.Kinematic;
import general.SteeringOutput;
import general.Util;
import general.Vector;

public class Wander extends Face implements SteeringBehavior {

	public double wanderOffset;
	public double wanderRadius;
	public double wanderRate;
	public double wanderOrientation;
	public double maxAcceleration;

	public Wander(Kinematic character, double maxAngularAcceleration, double maxRotation, double targetRadius,
			double slowRadius, double wanderOffset, double wanderRadius, double wanderRate, double wanderOrientation,
			double maxAcceleration) {
		super(character, new Kinematic(), maxAngularAcceleration, maxRotation, targetRadius, slowRadius);
		this.wanderOffset = wanderOffset;
		this.wanderRadius = wanderRadius;
		this.wanderRate = wanderRate;
		this.wanderOrientation = wanderOrientation;
		this.maxAcceleration = maxAcceleration;
	}
	
	@Override
	public SteeringOutput getSteering() {
		wanderOrientation += Util.randomBinomial() * wanderRate;
		double targetOrientation = wanderOrientation + character.orientation;
		faceTarget.position = character.position.add(new Vector(character.orientation).scale(wanderOffset));
		faceTarget.position = faceTarget.position.add(new Vector(targetOrientation).scale(wanderRadius));
		SteeringOutput steering = super.getSteering();
		steering.linear = new Vector(character.orientation).scale(maxAcceleration);
		return steering;
	}
}
