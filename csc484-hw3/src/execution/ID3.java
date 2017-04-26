package execution;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import decision_tree.Action;
import decision_tree.Decision;
import decision_tree.DecisionTreeNode;
import learning.Example;

public class ID3 {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("data.txt"));
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("learnedTree.txt"));
			List<String> attributes = null;
			List<Example> examples = new ArrayList<>();

			try {
				attributes = (List<String>) in.readObject();
				while (true) {
					examples.add((Example) in.readObject());
				}
			} catch (ClassNotFoundException | java.io.EOFException e) {
				System.out.println(examples.size() + " data samples retrieved");
			}

			Decision root = new Decision(null, "R");
			makeTree(examples, attributes, root);

			System.out.println("Writing...");
			out.writeObject(root);
			out.reset();
			System.out.println("Done");

			in.close();
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void makeTree(List<Example> examples, List<String> attributes, DecisionTreeNode decision) {
		System.out.println(decision.id);
		System.out.println(attributes);
		double initialEntropy = entropy(examples);

		int exampleCount = examples.size();

		double bestGain = 0;
		String bestSplitAttribute = null;
		List<Example> bestTrueSet = null;
		List<Example> bestFalseSet = null;

		for (String attribute : attributes) {
			List<Example> trueSet = new ArrayList<Example>();
			List<Example> falseSet = new ArrayList<Example>();
			for (Example example : examples) {
				// System.out.println(attributes);
				if (example.attributes.get(attribute))
					trueSet.add(example);
				else
					falseSet.add(example);
			}
			double overallEntropy = 0;
			overallEntropy -= trueSet.size() / exampleCount * entropy(trueSet);
			overallEntropy -= falseSet.size() / exampleCount * entropy(falseSet);
			double gain = initialEntropy - overallEntropy;
			if (gain >= bestGain) {
				bestGain = gain;
				bestSplitAttribute = attribute;
				bestTrueSet = trueSet;
				bestFalseSet = falseSet;
			}
		}
		((Decision) decision).attribute = bestSplitAttribute;
		List<String> newAttributes = new ArrayList<String>(attributes);
		newAttributes.remove(bestSplitAttribute);

		System.out.println(bestSplitAttribute + " " + bestTrueSet.size() + " " + bestFalseSet.size());

		if (bestTrueSet.size() == 0 || bestFalseSet.size() == 0) {
			if (newAttributes.size() == 0) {
				decision = new Action(examples.get(0).action, examples.get(0).action);
			} else {
				makeTree(examples, newAttributes, decision);
			}
			return;
		}

		if (newAttributes.size() == 0) {
			((Decision) decision).trueNode = new Action(bestTrueSet.get(0).action, decision.id + "T");
			System.out.println(decision.id + "T");
			System.out.println(bestTrueSet.get(0).action);
			((Decision) decision).falseNode = new Action(bestFalseSet.get(0).action, decision.id + "F");
			System.out.println(decision.id + "F");
			System.out.println(bestFalseSet.get(0).action);
		} else {
			if (entropy(bestTrueSet) <= 0) {
				((Decision) decision).trueNode = new Action(bestTrueSet.get(0).action, bestTrueSet.get(0).action);
				System.out.println(bestTrueSet.get(0).action);
			} else {
				((Decision) decision).trueNode = new Decision(null, decision.id + "T");
				makeTree(bestTrueSet, newAttributes, ((Decision) decision).trueNode);
			}
			if (entropy(bestFalseSet) <= 0) {
				((Decision) decision).falseNode = new Action(bestFalseSet.get(0).action, bestFalseSet.get(0).action);
				System.out.println(bestFalseSet.get(0).action + "derp");
			} else {
				((Decision) decision).falseNode = new Decision(null, decision.id + "F");
				makeTree(bestFalseSet, newAttributes, ((Decision) decision).falseNode);
			}

		}
	}

	private static double entropy(List<Example> examples) {
		int exampleCount = examples.size();
		if (exampleCount == 1)
			return 0;
		HashMap<String, Integer> actionTallies = new HashMap<String, Integer>();
		for (Example example : examples) {
			int tally = 1;
			if (actionTallies.get(example.action) != null)
				tally = actionTallies.get(example.action).intValue() + 1;
			actionTallies.put(example.action, tally);
		}
		int actionCount = actionTallies.size();
		if (actionCount == 1)
			return 0;
		double entropy = 0;
		for (Entry<String, Integer> entry : actionTallies.entrySet()) {
			double proportion = 1.0 * entry.getValue() / exampleCount;
			entropy -= proportion * Math.log(proportion);
		}
		return entropy;
	}

}
