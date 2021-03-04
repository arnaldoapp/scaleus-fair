package pt.ua.scaleus.kbqa.nlp;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;

import pt.ua.scaleus.kbqa.datastructures.KBQAQuestion;
import pt.ua.scaleus.kbqa.util.JSONStatusBuilder;

public class MutableTreePruner {
	Logger log = LoggerFactory.getLogger(MutableTreePruner.class);

	public MutableTree prune(KBQAQuestion q) {
		log.debug(q.getTree().toString());
		removalRules(q);
		removalBasedOnDependencyLabels(q);
		/*
		 * interrogative rules last else each interrogative word has at least
		 * two children, which can't be handled yet by the removal
		 */
		applyInterrogativeRules(q);
		sortTree(q.getTree());
		log.debug(q.getTree().toString());
		q.setTree_pruned(JSONStatusBuilder.treeToJSON(q.getTree()));
		return q.getTree();
	}

	private void sortTree(MutableTree tree) {
		Queue<MutableTreeNode> queue = new LinkedList<MutableTreeNode>();
		queue.add(tree.getRoot());
		while (!queue.isEmpty()) {
			MutableTreeNode tmp = queue.poll();
			Collections.sort(tmp.getChildren());
			queue.addAll(tmp.getChildren());
		}

	}

	private void removalBasedOnDependencyLabels(KBQAQuestion q) {
		for (String depLabel : Lists.newArrayList("auxpass", "aux")) {
			inorderRemovalBasedOnDependencyLabels(q.getTree().getRoot(), q.getTree(), depLabel);
		}
	}

	private boolean inorderRemovalBasedOnDependencyLabels(MutableTreeNode node, MutableTree tree, String depLabel) {
		if (node.depLabel.matches(depLabel)) {
			tree.remove(node);
			return true;
		} else {
			for (Iterator<MutableTreeNode> it = node.getChildren().iterator(); it.hasNext();) {
				MutableTreeNode child = it.next();
				if (inorderRemovalBasedOnDependencyLabels(child, tree, depLabel)) {
					it = node.getChildren().iterator();
				}
			}
			return false;
		}
	}

	private void applyInterrogativeRules(KBQAQuestion q) {
		MutableTreeNode root = q.getTree().getRoot();
		// GIVE ME will be deleted
		if (root.label.equals("Give")) {
			for (Iterator<MutableTreeNode> it = root.getChildren().iterator(); it.hasNext();) {
				MutableTreeNode next = it.next();
				if (next.label.equals("me")) {
					it.remove();
					q.getTree().remove(root);
				}
			}
		}
		// LIST will be deleted
		if (root.label.equals("List")) {
			q.getTree().remove(root);
		}
		// GIVE will be deleted
		if (root.label.equals("Give")) {
			q.getTree().remove(root);
		}

	}

	/**
	 * removes: * punctuations (.) * wh- words(WDT|WP$) * PRP($) * DT * BY and
	 * IN (possessive) pronouns * PDT predeterminer all both
	 * 
	 * Who,Where WP|WRB stays in
	 * 
	 * @param q
	 */
	private void removalRules(KBQAQuestion q) {
		MutableTreeNode root = q.getTree().getRoot();
		for (String posTag : Lists.newArrayList(".", "WDT", "POS", "WP\\$", "PRP\\$", "RB", "PRP", "DT", "IN", "PDT")) {
			Queue<MutableTreeNode> queue = Queues.newLinkedBlockingQueue();
			queue.add(root);
			while (!queue.isEmpty()) {
				MutableTreeNode tmp = queue.poll();
				if (tmp.posTag.matches(posTag)) {
					q.getTree().remove(tmp);
				}
				for (MutableTreeNode n : tmp.getChildren()) {
					queue.add(n);
				}
			}
		}

	}

}
