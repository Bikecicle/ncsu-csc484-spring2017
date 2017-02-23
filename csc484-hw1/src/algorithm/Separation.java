package algorithm;

import java.util.List;

import general.Kinematic;
import general.SteeringOutput;
import general.Vector;

public class Separation implements SteeringBehavior {

	public Kinematic character;
	public List<Kinematic> targets;
	public double threshold;
	public double decayCoefficient;
	public double maxAcceleration;

	public Separation(Kinematic character, List<Kinematic> targets, double threshold, double decayCoefficient, double maxAcceleration) {
		this.character = character;
		this.targets = targets;
		this.threshold = threshold;
		this.decayCoefficient = decayCoefficient;
		this.maxAcceleration = maxAcceleration;
	}

	@Override
	public SteeringOutput getSteering() {
		SteeringOutput steering = new SteeringOutput();
		for (Kinematic target : targets) {
			Vector direction = target.position.subtract(character.position);
			double distance = direction.magnitude();
			if (distance < threshold) {
				double strength = Math.min( decayCoefficient / (distance * distance), maxAcceleration);
				steering.linear = steering.linear.add(direction.normalize().scale(-strength));
			}
		}
		return steering;
	}

}
