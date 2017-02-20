package general;

public class Vector {

	public double x;
	public double y;

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double magnitude() {
		return Math.sqrt(x * x + y * y);
	}

	public Vector add(Vector other) {
		return new Vector(this.x + other.x, this.y + other.y);
	}

	public Vector scale(double a) {
		return new Vector(x * a, y * a);
	}
}
