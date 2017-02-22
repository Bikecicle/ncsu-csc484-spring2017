package algorithm;

import general.Kinematic;
import general.SteeringOutput;

public class Seek {

	public Kinematic character;
	public Kinematic target;
	public double maxAcceleration;
	
	public Seek(Kinematic character, Kinematic target, double maxAcceleration) {
		this.character = character;
		this.target = target;
		this.maxAcceleration = maxAcceleration;
	}
	
	public SteeringOutput getSteering() {
		SteeringOutput steering = new SteeringOutput();
		steering.linear = target.position.subtract(character.position);
		steering.linear = steering.linear.normalize().scale(maxAcceleration);
		steering.angular = 0;
		return steering;
	}
	
}
