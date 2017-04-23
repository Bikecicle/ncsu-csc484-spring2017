package behavior_tree;

public class UntilFail extends Decorator {

	@Override
	public boolean run() {
		while (true) {
			if (!child.run())
				break;
		}
		return true;
	}
}
