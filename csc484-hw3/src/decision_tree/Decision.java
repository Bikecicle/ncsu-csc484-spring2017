package decision_tree;

import java.util.HashMap;

public class Decision extends DecisionTreeNode {

	private static final long serialVersionUID = 8257036947751924860L;

	public DecisionTreeNode trueNode;
	public DecisionTreeNode falseNode;
	public String parameter;

	public Decision(String parameter, String id) {
		super(id.hashCode());
		this.trueNode = null;
		this.falseNode = null;
		this.parameter = parameter;
	}

	@Override
	public DecisionTreeNode makeDecision(HashMap<String, Parameter> parameters) {
		DecisionTreeNode branch = null;
		boolean b = parameters.get(parameter).getValue();
		if (b)
			branch = trueNode;
		else
			branch = falseNode;
		return branch.makeDecision(parameters);
	}

}
