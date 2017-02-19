package general;

public class Vector {

	public double x;
	public double y;

	public double magnitude() {
		return Math.sqrt(x * x + y * y);
	}
}
