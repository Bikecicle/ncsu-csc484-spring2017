package general;

public class Wander implements SteeringBehavior{
	
	private Static character;
	private double maxSpeed;
	private double maxRotation;
	

	public Wander() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public SteeringOutput getSteering() {
		SteeringOutput steering = new SteeringOutput();
		steering.velocity = new Vector(character.orientation);
		steering.velocity = steering.velocity.scale(maxSpeed);
		
		
		return null;
	}
	
	private double randomBinomial() {
		return Math.random() - Math.random();
	}
	
}
