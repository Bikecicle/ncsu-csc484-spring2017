package behavior_tree;

import decision_tree.Attribute;

public class TestAttribute extends BehaviorTreeNode {

	public Attribute attribute;

	public TestAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	@Override
	public boolean run() {
		return attribute.getValue();
	}

	/**
	 * @Override public BehaviorTreeNode run() { status = attribute.getValue();
	 *           return null; }
	 */

}
