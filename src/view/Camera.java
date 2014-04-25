package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

import utils.Position;

import model.Entity;
import model.Game;
import model.GameObject;
import model.Map;

/*
 * Camera class, works by transforming the Graphics context before drawing its 
 * children, so any GameObjects under the Camera will be translated and scaled 
 */
public class Camera extends GameObject {

	private double positionX, positionY, prevPositionX, prevPositionY;
	private double minX, minY, maxX, maxY;
	private Entity followTarget;
//	private double targetDistanceX, targetDistanceY;
	double zoom;

	public Camera(int mapWidth, int mapHeight) {
		positionX = positionY = prevPositionX = prevPositionY = 0;
//		targetDistanceX = targetDistanceY = 0;
		followTarget = null;
		
		zoom = 1;
		
		minX = 0;
		minY = 0;
		maxX = Map.TILESIZE * mapWidth;
		maxY = Map.TILESIZE * mapHeight;
	}
	
	public void update(){
		prevPositionX = positionX;
		prevPositionY = positionY;
		
		
		// update the target distance if an object is targeted
		if(followTarget != null){
			Position p = followTarget.getPosition();
			double x = p.getX();
			double y = p.getY() + 60 / zoom;
//			targetDistanceX = x - positionX;
//			targetDistanceY = y - positionY;
			double dx = x - positionX;
			double dy = y - positionY;
			
//			double speed = 20;
			double min = 1;
			double distance = Math.sqrt(dx * dx + dy * dy);
			if(distance < min){
				moveBy(dx, dy);
			} else {
				double fac = .4;
				moveBy(dx * fac, dy * fac);
//				dx = dx / distance * speed;
//				dy = dy / distance * speed;
//				moveBy(dx, dy);
			}
			
			
		}
		
	}
	
	public double getZoom(){
		return zoom;
	}
	
	public void setZoom(double newZoom){
		zoom = newZoom;
	}
	
	public Entity getFollowTarget(){
		return followTarget;
	}
	
	public void setFollowTarget(Entity target){
		followTarget = target;
	}

	public void setPosition(double x, double y) {
		prevPositionX = x;
		prevPositionY = y;
		positionX = x;
		positionY = y;
	}

	public void moveBy(double x, double y) {
		
		positionX += x;
		positionY += y;
		
		if(positionX < minX)
			positionX = minX;
		else if (positionX > maxX)
			positionX = maxX;
		
		if(positionY < minY)
			positionY = minY;
		else if(positionY > maxY)
			positionY = maxY;
		
	}

	/*
	 * Converts a point to what it would be after translating/scaling
	 */
	public Point convertToCameraSpace(Point p) {
		// magic
		double x = (p.getX() - Game.WIDTH/2) / zoom + positionX;
		double y = (p.getY() - Game.HEIGHT/2) / zoom + positionY;

		Point ret = new Point();
		ret.setLocation(x, y);
		return ret;
	}

	/*
	 * Converts a translated/scaled point back to normal coords
	 */
	public Point convertFromCameraSpace(Point p) {
		// magic
		double x = (p.getX() - positionX) * zoom + Game.WIDTH/2;
		double y = (p.getY() - positionY) * zoom + Game.HEIGHT/2;

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

		g2.translate(Game.WIDTH/2, Game.HEIGHT/2);
		g2.scale(zoom,zoom);
		g2.translate(-positionX, -positionY);

		super.drawSelfAndChildren(g);

		g2.setTransform(saved);
	}

}
