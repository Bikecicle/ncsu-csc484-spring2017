package behavior_tree;

public abstract class BehaviorTreeNode {
	
	public boolean status;

	public abstract BehaviorTreeNode run();
}
