package general;

public class Wander {
	
	private Static character;
	private double maxSpeed;
	private double maxRotation;
	

	public Wander() {
		// TODO Auto-generated constructor stub
	}

	public KinematicSteeringOutput getSteering() {
		KinematicSteeringOutput steering = new KinematicSteeringOutput();
		steering.velocity = new Vector(character.orientation);
		steering.velocity = steering.velocity.scale(maxSpeed);
		
		
		return null;
	}
	
	private double randomBinomial() {
		return Math.random() - Math.random();
	}
	
}
