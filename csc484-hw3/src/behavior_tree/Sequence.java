package behavior_tree;

public class Sequence extends Task {

	@Override
	public boolean run() {
		for (BehaviorTreeNode child : children) {
			if (!child.run())
				return false;
		}
		return true;
	}
}
