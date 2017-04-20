package decision_tree;

import java.io.Serializable;
import java.util.List;

public class DecisionTree implements Serializable {

	private static final long serialVersionUID = -1340296414305551855L;

	private DecisionTreeNode root;
	private List<DecisionTreeNode> nodes;

	public void add(DecisionTreeNode newNode, int parentId, boolean branch) {
		for (DecisionTreeNode node : nodes) {
			if (node.id == parentId) {
				if (branch)
					((Decision) node).trueNode = newNode;
				else
					((Decision) node).falseNode = newNode;
				break;
			}
		}
		nodes.add(newNode);
	}
}
