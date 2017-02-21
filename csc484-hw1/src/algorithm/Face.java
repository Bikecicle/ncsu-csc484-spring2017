package algorithm;

import general.Kinematic;
import general.SteeringOutput;
import general.Vector;

public class Face extends Align {
	
	public Kinematic target;

	public Face(Kinematic character, Kinematic target, double maxAngularAcceleration, double maxRotation,
			double targetRadius, double slowRadius) {
		super(character, new Kinematic(), maxAngularAcceleration, maxRotation, targetRadius, slowRadius);
		this.target = target;
	}

	@Override
	public SteeringOutput getSteering() {
		Vector direction = this.target.position.subtract(character.position);
		super.target.position = direction;
		super.target.orientation = Math.atan2(direction.y, -direction.x);
		return super.getSteering();
	}
}
