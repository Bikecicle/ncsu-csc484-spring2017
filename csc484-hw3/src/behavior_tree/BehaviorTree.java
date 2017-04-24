package behavior_tree;

import java.util.Stack;

public class BehaviorTree {

	private BehaviorTreeNode root;
	private Stack<BehaviorTreeNode> stack;
	private boolean ret;
	
	public BehaviorTree(BehaviorTreeNode root) {
		this.root = root;
		stack = new Stack<BehaviorTreeNode>();
		stack.push(root);
		ret = false;
	}
	
	public void step() {
		ret = stack.peek().run();
	}
}
