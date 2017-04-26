package behavior_tree;

public class BehaviorTree {

	private BehaviorTreeNode root;
	//private Stack<BehaviorTreeNode> stack;

	public BehaviorTree(BehaviorTreeNode root) {
		this.root = root;
		//stack = new Stack<BehaviorTreeNode>();
	}
	
	public boolean run() {
		return root.run();
	}

	/**
	public void step() {
		if (stack.isEmpty())
			stack.push(root);
		//System.out.println(stack.peek().id);
		BehaviorTreeNode next = stack.peek().run();
		if (next == null)
			stack.pop();
		else
			stack.push(next);
	}
	*/
}
