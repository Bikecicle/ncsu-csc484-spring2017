package behavior_tree;

import java.util.HashMap;

import decision_tree.Parameter;

public class TestParameter extends BehaviorTreeNode {
	
	public Parameter parameter;
	
	public TestParameter(Parameter parameter) {
		this.parameter = parameter;
	}

	@Override
	public BehaviorTreeNode run() {
		status = parameter.getValue();
		return null;
	}

}
