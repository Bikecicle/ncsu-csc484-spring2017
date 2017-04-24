package behavior_tree;

import java.util.Stack;

public class Selector extends Task {

	/**
	 * @Override public boolean run() { for (BehaviorTreeNode child : children)
	 *           { if (child.run()) return true; } return false; }
	 */

	@Override
	public boolean run(Stack<BehaviorTreeNode> stack, boolean ret) {

		if (status == 0) {
			stack.push(children.get(0));
		} else if (ret) {
			stack.pop();
			return true;
		} else if (status == children.size()){
			return false;
		}
	}

}
