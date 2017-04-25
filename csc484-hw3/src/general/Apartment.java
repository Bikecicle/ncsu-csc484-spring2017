package general;

import java.util.ArrayList;

public class Apartment extends ArrayList<Room> {

	public Room locate(Vector position) {
		for (Room room : this) {
			if (room.contains(position))
				return room;
		}
		return null;
	}
}
