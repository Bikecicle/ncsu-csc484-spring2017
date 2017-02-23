package general;

public class Vector {

	public double x;
	public double y;

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector(double a) {
		x = Math.cos(a);
		y = Math.sin(a);
	}

	public double magnitude() {
		return Math.sqrt(x * x + y * y);
	}

	public double direction() {
		return Math.atan2(-y, x);
	}

	public Vector add(Vector other) {
		return new Vector(this.x + other.x, this.y + other.y);
	}

	public Vector subtract(Vector other) {
		return new Vector(this.x - other.x, this.y - other.y);
	}

	public Vector scale(double s) {
		return new Vector(x * s, y * s);
	}

	public Vector normalize() {
		double m = magnitude();
		return new Vector(x / m, y / m);
	}

	public boolean isCloseTo(Vector other, double radius) {
		return Math.abs(this.x - other.x) < radius && Math.abs(this.y - other.y) < radius;
	}
	
	public double distance(Vector other) {
		return other.subtract(this).magnitude();
	}
	
	public double innerProduct(Vector other) {
		return this.x * other.x + this.y * other.y;
	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	public boolean equals(Vector other) {
		return this.x == other.x && this.y == other.y;
	}
	
	public Vector copy() {
		return new Vector(x, y);
	}
}
