package general;

import java.util.LinkedList;

public class Breadcrumbs extends LinkedList<Vector> {

	private static final long serialVersionUID = 751767195483117862L;

	private int maxSize;
	private long timeStamp;
	private long interval;

	public Breadcrumbs(int maxSize, double interval) {
		super();
		this.maxSize = maxSize;
		this.interval = (long) (1000000000 * interval);
	}

	public boolean add(Vector v, long timeStamp) {
		if (timeStamp - this.timeStamp >= interval) {
			this.timeStamp = timeStamp;
			if (this.size() > maxSize - 1)
				removeFirst();
			return super.add(v);
		}
		return false;
	}

}
