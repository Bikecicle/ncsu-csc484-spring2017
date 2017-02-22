package algorithm;

import general.Kinematic;
import general.SteeringOutput;
import general.Vector;

public class Face extends Align {
	
	public Kinematic faceTarget;
	
	public Face(Kinematic character, Kinematic target, double maxAngularAcceleration, double maxRotation,
			double targetRadius, double slowRadius) {
		super(character, new Kinematic(), maxAngularAcceleration, maxRotation, targetRadius, slowRadius);
		faceTarget = target;
	}

	@Override
	public SteeringOutput getSteering() {
		Vector direction = faceTarget.position.subtract(character.position);
		target.orientation = Math.atan2(direction.y, direction.x);
		return super.getSteering();
	}
}
