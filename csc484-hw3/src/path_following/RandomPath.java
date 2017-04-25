package path_following;

import general.Kinematic;
import general.SteeringOutput;
import general.Vector;
import graph.AdjacencyList;

public class RandomPath extends PathTo {

	public int upperx;
	public int uppery;

	public RandomPath(Kinematic character, AdjacencyList graph, double maxAcceleration, double maxSpeed, int upperx,
			int uppery) {
		super(character, graph, null, maxAcceleration, maxSpeed);
		this.upperx = upperx;
		this.uppery = uppery;
	}

	@Override
	public SteeringOutput getSteering() {
		destination = new Vector(Math.random() * upperx, Math.random() * uppery);
		return super.getSteering();
	}
}
