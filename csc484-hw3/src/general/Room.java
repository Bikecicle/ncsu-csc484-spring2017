package general;

public class Room {

	public Vector corner1;
	public Vector corner2;

	public Room(Vector corner1, Vector corner2) {
		this.corner1 = corner1;
		this.corner2 = corner2;
	}
	
	public boolean contains(Vector point) {
		return (point.x > corner1.x && point.x < corner2.x && point.y > corner1.y && point.y < corner2.y);
	}

}
