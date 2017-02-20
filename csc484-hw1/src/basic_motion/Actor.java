package basic_motion;

import general.Kinematic;
import general.Seek;
import general.Static;

public class Actor {

	private Kinematic kinematic;
	private Seek seek;

	public Actor(double posx, double posy, Static target) {
		kinematic = new Kinematic(posx, posy);
		seek = new Seek(kinematic, target, 200);

	}

	public Kinematic getKinematic() {
		return kinematic;
	}

	public Static getTarget() {
		return seek.getTarget();
	}

	public void update(double time) {
		kinematic.update(seek.getSteering(), time);
	}

	public void setTarget(Static target) {
		seek.setTarget(target);
	}
}
