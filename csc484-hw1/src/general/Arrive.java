package general;

public class Arrive implements SteeringBehavior{
	
	private Static character;
	private Static target;
	private double maxSpeed;
	private double radius;
	private final double timeToTarget = 0.25;
	
	public Arrive(Static character, Static target, double maxSpeed, double radius) {
		this.character = character;
		this.target = target;
		this.maxSpeed = maxSpeed;
		this.radius = radius;
	}

	@Override
	public SteeringOutput getSteering() {
		SteeringOutput steering = new SteeringOutput();
		steering.velocity = target.position.subtract(character.position);
		if (steering.velocity.magnitude() < radius)
			return null;
		steering.velocity.scale(1.0 / timeToTarget);
		if (steering.velocity.magnitude() > maxSpeed)
			steering.velocity = steering.velocity.normalize().scale(maxSpeed);
		if (steering.velocity.magnitude() > 0)
			character.orientation = steering.velocity.direction();
		steering.rotation = 0;
		return steering;
	}
	
	public Static getTarget() {
		return target;
	}
	
	public void setTarget(Static target) {
		this.target = target;
	}

}
