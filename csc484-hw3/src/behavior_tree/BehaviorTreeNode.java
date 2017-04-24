package behavior_tree;

import java.util.Stack;

public interface BehaviorTreeNode {

	public boolean run(Stack<BehaviorTreeNode> stack, boolean ret);
}
