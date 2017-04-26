package general;

import java.util.ArrayList;

public class Apartment extends ArrayList<Room> {

	private static final long serialVersionUID = -1350763416122183590L;

	public Room locate(Vector position) {
		for (Room room : this) {
			if (room.contains(position))
				return room;
		}
		return null;
	}
}
