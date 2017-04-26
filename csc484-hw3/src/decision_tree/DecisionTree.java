package decision_tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DecisionTree implements Serializable {

	private static final long serialVersionUID = -1340296414305551855L;

	public DecisionTreeNode root;
	public List<DecisionTreeNode> nodes;

	public DecisionTree(DecisionTreeNode node) {
		root = node;
		nodes = new ArrayList<DecisionTreeNode>();
		nodes.add(node);
	}

	public void add(DecisionTreeNode newNode, String parentId, boolean branch) {
		for (DecisionTreeNode node : nodes) {
			if (node.id.equals(parentId)) {
				if (branch)
					((Decision) node).trueNode = newNode;
				else
					((Decision) node).falseNode = newNode;
				break;
			}
		}
		nodes.add(newNode);
	}

	public String makeDecision(HashMap<String, Attribute> attributes) {
		return ((Action) root.makeDecision(attributes)).behavior;
	}

	public DecisionTreeNode getRoot() {
		return root;
	}
}
