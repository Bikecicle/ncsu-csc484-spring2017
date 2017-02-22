package general;

public class Util {
	
	public static double mapToRange(double deltaOrientation) {
		double r = deltaOrientation % (2 * Math.PI);
		if (Math.abs(r) < Math.PI) {
			return r;
		} else {
			if (r > Math.PI) 
				return  r - 2 * Math.PI;
			else
				return r + 2 * Math.PI;
		}
	}
	
	public static double randomBinomial() {
		return Math.random() - Math.random();
	}

}
