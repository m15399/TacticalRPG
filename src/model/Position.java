package model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/*
 * Position class that has a lot of info about an objects
 * location, rotation, and scale, and lets it have a parent 
 * Position that it follows (might be useful for animation)
 */
public class Position extends Observable implements Observer {

	private double localX, localY, localZ, globalX, globalY, globalZ;
	private double localScaleX, localScaleY, globalScaleX, globalScaleY;
	// private double localRot, globalRot;
	private boolean localMirrored, globalMirrored;
	private double rotation;

	private Position parent;
	
	AffineTransform saved;


	public Position(double x, double y) {
		init();
		setLocation(x, y);
	}

	public Position(Position other) {
		init();
		setLocation(other.getX(), other.getY());
		setRotation(other.getRotation());
		setScale(other.getScaleX(), other.getScaleY());
		setMirrored(other.getMirrored());
		setParent(other.getParent());
	}

	private void init() {
		localX = localY = localZ = globalX = globalY = globalZ = 0;
		localScaleX = localScaleY = globalScaleX = globalScaleY = 1;
		// localRot = globalRot = 0;
		localMirrored = globalMirrored = false;
		rotation = 0;
		parent = null;
	}

	public double getX() {
		return globalX;
	}

	public double getY() {
		return globalY;
	}

	public double getZ() {
		return globalZ;
	}

	public double getScaleX() {
		return globalScaleX;
	}

	public double getScaleY() {
		return globalScaleY;
	}

	public double getRotation() {
		// return globalRot;
		return rotation;
	}

	public boolean getMirrored() {
		return globalMirrored;
	}

	/*
	 * update global properties using parent
	 */
	private void updated() {
		if (parent == null) {
			globalX = localX;
			globalY = localY;
			globalZ = localZ;
			globalScaleX = localScaleX;
			globalScaleY = localScaleY;
			// globalRot = localRot;
			globalMirrored = localMirrored;
		} else {
			// use parent position to make new coordinates

			double x = localX;
			if (parent.getMirrored())
				x *= -1;
			globalX = x * parent.getScaleX() + parent.getX();
			globalY = localY * parent.getScaleY() + parent.getY();
			globalZ = localZ + parent.getZ();

			globalScaleX = localScaleX * parent.getScaleX();
			globalScaleY = localScaleY * parent.getScaleY();

			// globalRot = localRot + parent.getRotation();

			globalMirrored = localMirrored;
			if (parent.getMirrored())
				globalMirrored = !globalMirrored;
		}

		notifyObservers();
	}

	public void setX(double x) {
		localX = x;
		updated();
	}

	public void setY(double y) {
		localY = y;
		updated();
	}

	public void setZ(double z) {
		localZ = z;
		updated();
	}

	public void setLocation(double x, double y) {
		localX = x;
		localY = y;
		updated();
	}

	public void moveBy(double x, double y) {
		setLocation(localX + x, localY + y);
	}

	public void setRotation(double r) {
		// localRot = r;
		rotation = r;
		updated();
	}

	public void rotateBy(double r) {
		// setRotation(localRot + r);
		setRotation(rotation + r);
	}

	public void setScale(double sx, double sy) {
		localScaleX = sx;
		localScaleY = sy;
		updated();
	}

	public void setMirrored(boolean m) {
		localMirrored = m;
		updated();
	}

	public void mirror() {
		setMirrored(!localMirrored);
	}

	public void scaleBy(double sx, double sy) {
		setScale(localScaleX * sx, localScaleY * sy);
	}

	/*
	 * Parent position updated so we have to update our global coords
	 */
	public void notified(Observable sender) {
		updated();
	}

	public Position getParent() {
		return parent;
	}

	public void setParent(Position p) {
		parent = p;
		p.addObserver(this);
		updated();
	}

	public void removeFromParent() {
		if(parent == null)
			return;
		parent.removeObserver(this);
		parent = null;
		updated();
	}
	
	/*
	 * drawing 
	 */
	public void transform(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		saved = g2.getTransform();
				
		g2.translate((int)getX(), (int)getY());
		g2.rotate(getRotation());
		g2.scale(getScaleX()* (getMirrored() ? -1 : 1), getScaleY());
		
	}
	
	public void unstransform(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.setTransform(saved);		
	}

}
