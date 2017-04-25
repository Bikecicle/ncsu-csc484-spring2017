package behavior_tree;

import java.util.ArrayList;
import java.util.List;

public abstract class Task extends BehaviorTreeNode {

	public List<BehaviorTreeNode> children;
	public int branch;

	public Task() {
		children = new ArrayList<BehaviorTreeNode>();
		branch = 0;
	}
}
