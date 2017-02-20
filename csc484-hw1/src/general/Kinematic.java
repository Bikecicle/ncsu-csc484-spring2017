package general;

public class Kinematic {

	private Vector position;
	private double orientation;
	private Vector velocity;
	private double rotation;

	public Kinematic(double posx, double posy) {
		position = new Vector(posx, posy);
		orientation = 0;
		velocity = new Vector(0, 0);
		rotation = 0;

	}

	public Vector getPosition() {
		return position;
	}

	public void setPosition(Vector position) {
		this.position = position;
	}

	public double getOrientation() {
		return orientation;
	}

	public void setOrientation(double orientation) {
		this.orientation = orientation;
	}

	public Vector getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}

	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	public void update(SteeringOutput steering, long time) {
		position = position.add(velocity.scale(time));
		orientation += rotation * time;

		velocity = velocity.add(steering.linear.scale(time));
		rotation = steering.angular * time;
	}

}
