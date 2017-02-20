package general;

public class Kinematic extends Static{

	public Vector velocity;
	public double rotation;

	public Kinematic(double posx, double posy) {
		super(posx, posy);
		velocity = new Vector(0, 0);
		rotation = 0;
	}

	public void update(SteeringOutput steering, double time) {
		position = position.add(velocity.scale(time));
		orientation += rotation * time;
		velocity = steering.velocity;
		rotation = steering.rotation;
	}
	
	public Static getStatic() {
		return new Static(position, orientation);
	}

}
