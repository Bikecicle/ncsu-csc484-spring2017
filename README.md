# Repo for work done in CSC484, game AI

Assignments involved implementing various game-AI techniques from scratch (from basic movement to A*-based pathfinding algorithms), culminating in a decision-tree learning system capable of mimicking the behavior of a scripted character. All assignments were individual and completely open-ended in design and implementation.

# Executables included:
- Basic Motion: Character simply follows the edge of the view
- Wander Steering: Character wanders randomly
- Arrive Steering: Character moves to mouse click
- Flocking Behavior: My own implementation of the Boids algorithm
- Path Following: Builds a tilegraph based on an image and then uses a heuristic A* algorithm to path the character to mouse clicks
- Behavior Tree / Learning Decision Tree:
  1. In the behavior tree application, the character on the left (C1) tries to turn off appliances (when a white circle appears), or hit the circuit breaker when more than one is on - then return to the closet where it is safe. C2 wanders while idle, investigates when it hears C1 in an adjacent room, and chases C1 when they are visible.
  2. Run this for a couple of minutes while the application outputs a log to data.txt.
  3. The learning decision tree application runs an ID3 algorithm on the data to train a decision tree for C2. If enough data is provided, C2's behavior will be indistinguishable to that in part 1.
