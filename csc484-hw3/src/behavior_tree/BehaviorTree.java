package behavior_tree;

import java.util.Stack;

public class BehaviorTree {

	private BehaviorTreeNode root;
	private Stack<BehaviorTreeNode> stack;

	public BehaviorTree(BehaviorTreeNode root) {
		this.root = root;
		stack = new Stack<BehaviorTreeNode>();
	}

	public void step() {
		if (stack.isEmpty())
			stack.push(root);
		BehaviorTreeNode next = stack.peek().run();
		if (next == null)
			stack.pop();
		else
			stack.push(next);
	}
}
