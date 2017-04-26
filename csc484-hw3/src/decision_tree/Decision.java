package decision_tree;

import java.util.HashMap;

public class Decision extends DecisionTreeNode {

	private static final long serialVersionUID = 8257036947751924860L;

	public DecisionTreeNode trueNode;
	public DecisionTreeNode falseNode;
	public String attribute;

	public Decision(String attribute, String id) {
		super(id);
		this.trueNode = null;
		this.falseNode = null;
		this.attribute = attribute;
	}

	public Decision() {
		super("");
		this.trueNode = null;
		this.falseNode = null;
		this.attribute = null;
	}

	@Override
	public DecisionTreeNode makeDecision(HashMap<String, Attribute> attributes) {
		DecisionTreeNode branch = null;
		//System.out.println(id + " " + attribute);
		boolean b = attributes.get(attribute).getValue();
		if (b)
			branch = trueNode;
		else
			branch = falseNode;
		return branch.makeDecision(attributes);
	}

}
