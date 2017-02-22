package algorithm;

import java.util.ArrayList;
import java.util.List;

import general.SteeringOutput;

public class BlendedSteering implements SteeringBehavior {

	private List<BehaviorAndWeight> behaviors;
	private double maxAcceleration;
	private double maxRotation;

	public BlendedSteering(double maxAcceleration, double maxRotation) {
		behaviors = new ArrayList<BehaviorAndWeight>();
		this.maxAcceleration = maxAcceleration;
		this.maxRotation = maxRotation;
	}

	public SteeringOutput getSteering() {
		SteeringOutput steering = new SteeringOutput();
		for (BehaviorAndWeight behavior : behaviors) {
			SteeringOutput tempSteering = behavior.steering.getSteering();
			steering.linear = steering.linear.add(tempSteering.linear.scale(behavior.weight));
			steering.angular += tempSteering.angular * behavior.weight;
		}
		if (steering.linear.magnitude() > maxAcceleration)
			steering.linear = steering.linear.normalize().scale(maxAcceleration);
		if (Math.abs(steering.angular) > maxRotation ) 
			steering.angular = steering.angular / Math.abs(steering.angular) * maxRotation;
		return steering;
	}
	
	public void addBehavior(SteeringBehavior behavior, double weight) {
		behaviors.add(new BehaviorAndWeight(behavior, weight));
	}

	public class BehaviorAndWeight {

		public SteeringBehavior steering;
		public double weight;

		public BehaviorAndWeight(SteeringBehavior steering, double weight) {
			this.steering = steering;
			this.weight = weight;
		}
	}
}
