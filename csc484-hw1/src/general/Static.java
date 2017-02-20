package general;

public class Static {

	public Vector position;
	public double orientation;

	public Static(double posx, double posy) {
		position = new Vector(posx, posy);
	}

	public Static(Vector position, double orientation) {
		this.position = position;
		this.orientation = orientation;
	}

	public boolean equals(Static other) {
		return this.position.equals(other.position) && this.orientation == other.orientation;
	}
}
