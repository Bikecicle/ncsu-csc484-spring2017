package flocking_behavior;

import java.util.ArrayList;
import java.util.List;

import algorithm.Arrive;
import algorithm.BlendedSteering;
import algorithm.LookWhereYouAreGoing;
import algorithm.Separation;
import algorithm.VelocityMatch;
import general.Actor;
import general.Kinematic;
import general.Vector;

public class BoidActor implements Actor {

	private Kinematic kinematic;
	private BlendedSteering blendedSteering;

	private Actor[] boids;
	private Actor[] sharks;
	private List<Kinematic> boidTargets;
	private List<Kinematic> sharkTargets;
	private List<Actor> neighborhood;
	private Kinematic neighborhoodCM;
	private double neighborhoodRadius = 40;

	private double maxSpeed = 100;
	private double maxAcceleration = 400;
	private double targetRadius = 20;
	private double slowRadius = 800;
	private double maxRotation = Math.PI * 128;

	public BoidActor(double posx, double posy, Actor[] boids, Actor[] sharks) {
		this.boids = boids;
		this.sharks = sharks;
		boidTargets = new ArrayList<Kinematic>();
		sharkTargets = new ArrayList<Kinematic>();
		kinematic = new Kinematic(posx, posy);
		kinematic.velocity = new Vector(Math.random() * 2 * Math.PI - Math.PI).scale(maxSpeed);

		neighborhood = new ArrayList<Actor>();
		neighborhoodCM = new Kinematic();

		blendedSteering = new BlendedSteering(maxAcceleration, maxRotation);
		blendedSteering.addBehavior(new Separation(kinematic, sharkTargets, 100, 8000000, maxAcceleration), 1);
		blendedSteering.addBehavior(new Separation(kinematic, boidTargets, 30, 80000, maxAcceleration), 1);
		blendedSteering.addBehavior(new VelocityMatch(kinematic, neighborhoodCM, maxAcceleration), 1);
		blendedSteering.addBehavior(
				new Arrive(kinematic, neighborhoodCM, maxAcceleration, maxSpeed, targetRadius, slowRadius), 1);
		blendedSteering.addBehavior(
				new LookWhereYouAreGoing(kinematic, maxRotation, maxRotation / 8, Math.PI / 16, Math.PI), 1);
		//blendedSteering.addBehavior(
		//		new Wander(kinematic, Math.PI * 2, Math.PI, Math.PI / 32, Math.PI / 8, 200, 100, Math.PI / 2, 0, 400),
		//		1);

	}

	public Kinematic getKinematic() {
		return kinematic;
	}

	public void selectTargets() {
		for (Actor actor : boids) {
			if (actor != this)
				boidTargets.add(actor.getKinematic());
		}
		for (Actor actor : sharks) {
			if (actor != this)
				sharkTargets.add(actor.getKinematic());
		}
	}

	public void update(double time) {
		neighborhood.clear();
		neighborhood.add(this);
		neighborhoodCM.position = new Vector(0, 0);
		neighborhoodCM.velocity = new Vector(0, 0);
		for (Actor actor : boids) {
			if (actor != this) {
				double distance = actor.getKinematic().position.subtract(kinematic.position).magnitude();
				if (distance < neighborhoodRadius)
					neighborhood.add(actor);
			}
		}
		for (Actor actor : neighborhood) {
			neighborhoodCM.position = neighborhoodCM.position.add(actor.getKinematic().position);
			neighborhoodCM.velocity = neighborhoodCM.velocity.add(actor.getKinematic().velocity);
		}
		if (!neighborhood.isEmpty()) {
			neighborhoodCM.position = neighborhoodCM.position.scale(1.0 / neighborhood.size());
			neighborhoodCM.velocity = neighborhoodCM.velocity.scale(1.0 / neighborhood.size());
		} else {
			neighborhoodCM.position = kinematic.position;
			neighborhoodCM.velocity = kinematic.velocity;
		}
		kinematic.update(blendedSteering.getSteering(), maxSpeed, time);
	}

	public void scramble() {
		kinematic.velocity = new Vector(Math.random() * 2 * Math.PI - Math.PI).scale(maxSpeed);
	}
}
