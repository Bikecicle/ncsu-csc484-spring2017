package general;

public class KinematicSeek {

	private Static character;
	private Static target;
	private double maxSpeed;

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

	public Static getCharacter() {
		return character;
	}

	public void setCharacter(Static character) {
		this.character = character;
	}

	public Static getTarget() {
		return target;
	}

	public void setTarget(Static target) {
		this.target = target;
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

}
