package algorithm;

import general.KinematicSteeringOutput;
import general.Static;

public class KinematicSeek {

	public Static character;
	public Static target;
	public double maxSpeed;

	public KinematicSeek(Static character, Static target, double maxSpeed) {
		this.character = character;
		this.target = target;
		this.maxSpeed = maxSpeed;
	}

	public KinematicSteeringOutput getSteering() {
		KinematicSteeringOutput steering = new KinematicSteeringOutput();
		// Set velocity toward target
		steering.velocity = target.position.subtract(character.position).normalize().scale(maxSpeed);
		// Set orientation in direction of velocity
		if (steering.velocity.magnitude() > 0)
			character.orientation = steering.velocity.direction();
		steering.rotation = 0;
		return steering;
	}
}
