package behavior_tree;

public class Selector extends Task {

	@Override
	public boolean run() {
		for (BehaviorTreeNode child : children) {
			if (child.run())
				return true;
		}
		return false;
	}

}
