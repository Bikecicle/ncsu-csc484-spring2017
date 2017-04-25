package behavior_tree;

public abstract class BehaviorTreeNode {
	
	public boolean status;
	public String id;

	public abstract BehaviorTreeNode run();
}
