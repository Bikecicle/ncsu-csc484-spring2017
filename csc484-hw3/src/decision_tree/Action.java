package decision_tree;

import java.util.HashMap;

import path_following.SteeringBehavior;

public class Action extends DecisionTreeNode {

	private static final long serialVersionUID = 9060833469771762345L;

	public SteeringBehavior behavior;
	
	public Action(SteeringBehavior behavior, int id) {
		super(id);
		this.behavior = behavior;
	}

	@Override
	public DecisionTreeNode makeDecision(HashMap<String, Parameter> parameters) {
		return this;
	}
}
