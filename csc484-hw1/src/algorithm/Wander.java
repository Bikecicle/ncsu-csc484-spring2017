package algorithm;

import general.KinematicSteeringOutput;
import general.Static;
import general.Vector;

public class Wander {
	
	public Static character;
	public double maxSpeed;
	public double maxRotation;
	

	public Wander() {
		// TODO Auto-generated constructor stub
	}

	public KinematicSteeringOutput getSteering() {
		KinematicSteeringOutput steering = new KinematicSteeringOutput();
		steering.velocity = new Vector(character.orientation);
		steering.velocity = steering.velocity.scale(maxSpeed);
		
		
		return null;
	}
	
	public double randomBinomial() {
		return Math.random() - Math.random();
	}
	
}
