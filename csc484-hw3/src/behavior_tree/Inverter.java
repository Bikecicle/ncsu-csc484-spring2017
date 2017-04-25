package behavior_tree;

public class Inverter extends Decorator {

	@Override
	public BehaviorTreeNode run() {
		if (passed) {
			status = !child.status;
			passed = false;
			return null;
		}
		passed = true;
		return child;
	}

}
