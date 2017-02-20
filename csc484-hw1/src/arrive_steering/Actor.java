package arrive_steering;

import general.Arrive;
import general.Kinematic;
import general.Seek;
import general.Static;

public class Actor {

	private Kinematic kinematic;
	private Arrive arrive;

	public Actor(double posx, double posy, Static target) {
		kinematic = new Kinematic(posx, posy);
		arrive = new Arrive(kinematic, target, 400, 1);

	}

	public Kinematic getKinematic() {
		return kinematic;
	}

	public void update(double time) {
		kinematic.update(arrive.getSteering(), time);
	}

	public Static getTarget() {
		return arrive.getTarget();
	}
	
	public void setTarget(Static target) {
		arrive.setTarget(target);
	}
}
