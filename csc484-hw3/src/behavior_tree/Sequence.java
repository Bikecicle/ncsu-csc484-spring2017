package behavior_tree;

public class Sequence extends Task {

	@Override
	public BehaviorTreeNode run() {
		if (branch == 0) {
			branch = 1;
			return children.get(0);
		} else if (!children.get(branch - 1).status) {
			branch = 0;
			status = false;
			return null;
		} else if (branch == children.size()) {
			branch = 0;
			status = true;
			return null;
		} else {
			branch++;
			return children.get(branch - 1);
		}
	}

	/**
	 * @Override public boolean run() { for (BehaviorTreeNode child : children)
	 *           { if (!child.run()) return false; } return true; }
	 */
}
