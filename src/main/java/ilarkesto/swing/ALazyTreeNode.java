package ilarkesto.swing;

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public abstract class ALazyTreeNode extends DefaultMutableTreeNode {

	private boolean childrenLoaded;

	protected abstract void loadChildren();

	public ALazyTreeNode(Object userObject, boolean allowesChildren) {
		super(userObject, allowesChildren);
	}

	private synchronized void loadChildrenInternal() {
		if (childrenLoaded) return;
		childrenLoaded = true;
		loadChildren();
	}

	@Override
	public int getChildCount() {
		loadChildrenInternal();
		return super.getChildCount();
	}

	@Override
	public TreeNode getChildAt(int index) {
		loadChildrenInternal();
		return super.getChildAt(index);
	}

	@Override
	public Enumeration children() {
		loadChildrenInternal();
		return super.children();
	}

}
