package algorithm;

import general.Kinematic;
import general.SteeringOutput;

public class LookWhereYouAreGoing extends Align implements SteeringBehavior{

	public LookWhereYouAreGoing(Kinematic character, double maxAngularAcceleration,
			double maxRotation, double targetRadius, double slowRadius) {
		super(character, new Kinematic(), maxAngularAcceleration, maxRotation, targetRadius, slowRadius);
	}
	
	public SteeringOutput getSteering() {
		if (character.velocity.magnitude() == 0)
			return new SteeringOutput();
		target.orientation = Math.atan2(character.velocity.y, character.velocity.x);
		return super.getSteering();
	}
}
