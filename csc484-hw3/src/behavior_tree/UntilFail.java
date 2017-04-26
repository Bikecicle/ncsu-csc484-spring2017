package behavior_tree;

public class UntilFail extends Decorator {

	public int count = 0;

	/**
	 * @Override public BehaviorTreeNode run() { if (count == 0 || child.status)
	 *           { count++; return child; } count = 0; status = true; return
	 *           null; }
	 */

	@Override
	public boolean run() {
		while (true) {
			if (!child.run())
				break;
		}
		return true;
	}

}
