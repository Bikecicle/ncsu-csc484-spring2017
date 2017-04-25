package behavior_tree;

import execution.DecisionActor;
import path_following.SteeringBehavior;

public class ModifyBehavior extends Task{
	
	public SteeringBehavior behavior;
	public DecisionActor actor;
	
	public ModifyBehavior(SteeringBehavior behavior, DecisionActor actor) {
		this.behavior = behavior;
		this.actor = actor;
	}

	@Override
	public BehaviorTreeNode run() {
		actor.setBehavior(behavior);
		status = true;
		return null;
	}

}
