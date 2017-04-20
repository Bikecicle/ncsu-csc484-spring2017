package decision_tree;

import java.io.Serializable;
import java.util.HashMap;

public abstract class DecisionTreeNode implements Serializable {
	
	private static final long serialVersionUID = 8871640978552713157L;
	
	public int id;
	
	public DecisionTreeNode(int id) {
		this.id = id;
	}

	public abstract DecisionTreeNode makeDecision(HashMap<String, Parameter> parameters);
	
}