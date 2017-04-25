package path_following;

import general.Kinematic;
import general.SteeringOutput;
import graph.AdjacencyList;

public class Chase extends PathTo {
	
	public Kinematic enemy;

	public Chase(Kinematic character, AdjacencyList graph, Kinematic enemy, double maxAcceleration,
			double maxSpeed) {
		super(character, graph, null, maxAcceleration, maxSpeed);
		this.enemy = enemy;
	}
	
	@Override
	public SteeringOutput getSteering() {
		destination = enemy.position;
		return super.getSteering();
	}

}
