package general;

public class Kinematic extends Static {

	public Vector velocity;
	public double rotation;

	public Kinematic(double posx, double posy) {
		super(posx, posy);
		velocity = new Vector(0, 0);
		rotation = 0;
	}
	
	public Kinematic() {
		super(0, 0);
		velocity = new Vector(0, 0);
		rotation = 0;
	}
	
	public void update(SteeringOutput steering, double maxSpeed, double time) {
		if (steering != null) {
			position = position.add(velocity.scale(time));
			orientation += rotation * time;
			velocity = velocity.add(steering.linear.scale(time));
			rotation += steering.angular * time;
			if (velocity.magnitude() > maxSpeed)
				velocity = velocity.normalize().scale(maxSpeed);
			
			if (position.x > 810)
				position.x -= 820;
			else if (position.x < -10)
				position.x += 820;
			
			if (position.y > 610)
				position.y -= 620;
			else if (position.y < -10)
				position.y += 620;
			if (velocity.magnitude() < 0.1)
				velocity = velocity.scale(0);
		}
	}

	public void update(KinematicSteeringOutput steering, double time) {
		if (steering != null) {
			position = position.add(velocity.scale(time));
			orientation += rotation * time;
			velocity = steering.velocity;
			rotation = steering.rotation;
		}
	}

	public Static getStatic() {
		return new Static(position, orientation);
	}

}
