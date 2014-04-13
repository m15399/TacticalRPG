package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

import model.GameObject;

/*
 * Camera class, works by transforming the Graphics context before drawing its 
 * children, so any GameObjects under the Camera will be translated and scaled 
 */
public class Camera extends GameObject {

	private double positionX, positionY, prevPositionX, prevPositionY;

	public Camera() {
		positionX = positionY = prevPositionX = prevPositionY = 0;
	}

	public void setPosition(double x, double y) {
		prevPositionX = x;
		prevPositionY = y;
		positionX = x;
		positionY = y;
	}

	public void moveBy(double x, double y) {
		prevPositionX = positionX;
		prevPositionY = positionY;
		positionX += x;
		positionY += y;
	}

	/*
	 * Converts a point to what it would be after translating/scaling
	 */
	public Point convertToCameraSpace(Point p) {
		double x = (p.getX() - positionX);
		double y = (p.getY() - positionY);

		Point ret = new Point();
		ret.setLocation(x, y);
		return ret;
	}

	/*
	 * Converts a translated/scaled point back to normal coords
	 */
	public Point convertFromCameraSpace(Point p) {
		double x = (p.getX() + positionX);
		double y = (p.getY() + positionY);

		Point ret = new Point();
		ret.setLocation(x, y);
		return ret;
	}

	public double getPositionX() {
		return positionX;
	}

	public double getPositionY() {
		return positionY;
	}

	public double getVelocityX() {
		return positionX - prevPositionX;
	}

	public double getVelocityY() {
		return positionY - prevPositionY;
	}

	/*
	 * Override this to first translate the Graphics context before drawing
	 * the children
	 */
	public void drawSelfAndChildren(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform saved = g2.getTransform();

		// g2.scale(3,3);
		g2.translate(-positionX, -positionY);

		super.drawSelfAndChildren(g);

		g2.setTransform(saved);
	}

}
