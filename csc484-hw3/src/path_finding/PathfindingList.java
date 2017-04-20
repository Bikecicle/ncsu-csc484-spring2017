package path_finding;

import java.util.ArrayList;

import graph.Node;

public class PathfindingList extends ArrayList<NodeRecord>{

	private static final long serialVersionUID = 1678122571926967392L;

	public boolean contains( Node node) {
		for (NodeRecord record : this) {
			if (record.node == node)
				return true;
		}
		return false;
	}

	public NodeRecord find(Node node) {
		for (NodeRecord record : this) {
			if (record.node == node)
				return record;
		}
		return null;
	}
}
