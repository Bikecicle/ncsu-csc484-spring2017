package algorithm;

import general.Kinematic;
import general.SteeringOutput;
import general.Vector;

public class Pursue extends Seek {
	
	public double maxPrediction;
	public Kinematic pursueTarget;

	public Pursue(Kinematic character, Kinematic target, double maxAcceleration, double maxPrediction) {
		super(character, new Kinematic(), maxAcceleration);
		this.maxPrediction = maxPrediction;
		this.pursueTarget = target;
	}
	
	public SteeringOutput getSteering() {
		Vector direction = pursueTarget.position.subtract(character.position);
		double distance = direction.magnitude();
		double speed = character.velocity.magnitude();
		double prediction = 0;
		if (speed < distance / maxPrediction)
			prediction = maxPrediction;
		else
			prediction = distance / speed;
		target.position = target.position.add(target.velocity.scale(prediction));
		return super.getSteering();
	}

}
