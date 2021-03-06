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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1168494059308354873L;
	private double centerOffsetY;
	private double positionX, positionY, prevPositionX, prevPositionY;
	private double minX, minY, maxX, maxY;
	private Entity followTarget;
//	private double targetDistanceX, targetDistanceY;
	private double zoom, zoomTarget;
	private int ramp;
	
	
	public Camera(int mapWidth, int mapHeight) {
		positionX = positionY = prevPositionX = prevPositionY = 0;
//		targetDistanceX = targetDistanceY = 0;
		followTarget = null;
		
		centerOffsetY = -60;
		
		zoom = zoomTarget = 1;
		
		minX = 0;
		minY = 0;
		maxX = Map.TILESIZE * mapWidth;
		maxY = Map.TILESIZE * mapHeight;
	}
	
	public void update(){
		ramp++;
		
		prevPositionX = positionX;
		prevPositionY = positionY;
		
		if(zoomTarget != zoom){
			double dz = zoomTarget - zoom;
			
			double fac = .3 / Game.FPSMUL;
			double min = .002;

			if(Math.abs(dz) < min){
				zoom = zoomTarget;
			} else {
				zoom += dz * fac;
			}
			
		}
		
		// update the target distance if an object is targeted
		if(followTarget != null){
			Position p = followTarget.getPosition();
			double x = p.getX();
			double y = p.getY();
//			targetDistanceX = x - positionX;
//			targetDistanceY = y - positionY;
			double dx = x - positionX;
			double dy = y - positionY;
			
//			double speed = 1;
			double min = .1 / Game.FPSMUL;
			double max = 35 / Game.FPSMUL / zoom;
			double fac = .23 / Game.FPSMUL; //.9 / Game.FPSMUL;
			
			double dxf = dx * fac;
			double dyf = dy * fac;
			
			double distance = Math.sqrt(dx * dx + dy * dy);
			double distancef = Math.sqrt(dxf * dxf + dyf * dyf);
			
			double dxbr, dybr;
			
			if(distance < min || distancef < min){
				dxbr = dx;
				dybr = dy;
				
			} else if(distancef > max){ 
				dxf /= distancef;
				dxf *= max;
				dyf /= distancef;
				dyf *= max;
				
				dxbr = dxf;
				dybr = dyf;
				
			} else {
				dxbr = dxf;
				dybr = dyf;
			}
			
			double rampFac = ramp / 10.0;
			if(rampFac > 1)
				rampFac = 1;
			
			moveBy(dxbr * rampFac, dybr * rampFac);
			
			
			
		}
		
	}
	
	public boolean getIsMoving(){
		if(getFollowTarget() == null)
			return false;
		
		Position p = getFollowTarget().getPosition();
		double x = p.getX();
		double y = p.getY();
		double dx = x - positionX;
		double dy = y - positionY;		
		
		if(zoom == zoomTarget && dx == 0 && dy == 0){
			return false;
		}
		
		return true;
	}
	
	public double getZoom(){
		return zoom;
	}
	
	public void setZoom(double newZoom){
		zoom = zoomTarget = newZoom;
	}
	
	public void setZoomTarget(double newZoom){
		zoomTarget = newZoom;
	}
	
	public Entity getFollowTarget(){
		return followTarget;
	}
	
	public void setFollowTarget(Entity target){
		if(followTarget != target){
			ramp = 0;
			followTarget = target;
		}
		
		
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
		double y = (p.getY() - Game.HEIGHT/2 - centerOffsetY) / zoom + positionY;

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
		double y = (p.getY() - positionY + centerOffsetY) * zoom + Game.HEIGHT/2;

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

		g2.translate(Game.WIDTH/2, Game.HEIGHT/2 + centerOffsetY / zoom);
		g2.scale(zoom,zoom);
		g2.translate(-positionX, -positionY);

		super.drawSelfAndChildren(g);

		g2.setTransform(saved);
	}

}
