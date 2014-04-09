package model;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class GameObject {
	private List<GameObject> children;
	private GameObject parent;

	public GameObject() {
		/*
		 * Initializing private instance variables here
		 */
		children = new ArrayList<GameObject>();
		parent = null;
	}

	public final void addChild(GameObject object) {
		children.add(object);
		object.parent = this;
	}

	public final void removeChild(GameObject object) {
		children.remove(object);
		object.parent = null;
	}

	/*
	 * Remove object from the tree
	 */
	public final void destroy() {
		onDestroy();
		
		if (parent != null) {
			parent.removeChild(this);
		}
	}
	
	public void onDestroy(){
		// override
	}

	public void update() {
		// override
	}

	public void draw(Graphics g) {
		// override
	}
	
	/*
	 * Recursively update tree of objects
	 */
	public final void updateSelfAndChildren() {
		// update self
		update();

		// update children recursively
		int numChildren = children.size();
		if (numChildren > 0) {
			// its possible for the child to remove itself from the list while
			// it's updating, so we have to make a copy of the list first
			ArrayList<GameObject> childrenCopy = new ArrayList<GameObject>(children);
			for (int i = 0; i < numChildren; i++) {
				childrenCopy.get(i).updateSelfAndChildren();
			}
		}
	}

	public final void drawSelfAndChildren(Graphics g) {
		// draw self
		draw(g);

		// draw children recursively
		int numChildren = children.size();
		for (int i = 0; i < numChildren; i++) {
			children.get(i).drawSelfAndChildren(g);
		}
	}

	/*
	 * Setters and Getters for private instance variables.
	 */

	public final List<GameObject> getChildren() {
		return children;
	}

	public final void setChildren(List<GameObject> newChildren) {
		children = newChildren;
	}

	public final GameObject getParent() {
		return parent;
	}
}
