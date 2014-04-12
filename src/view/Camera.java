package view;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

import model.GameObject;

/*
 * Camera class, very simple for now
 */
public class Camera extends GameObject {

	private double translateX, translateY, prevTranslateX, prevTranslateY;
	
	public Camera(){
		translateX = translateY = prevTranslateX = prevTranslateY = 0;	
	}
	
	public void setPosition(double x, double y){
		prevTranslateX = translateX;
		prevTranslateY = translateY;
		translateX = x;
		translateY = y;
	}
	
	public void moveBy(double x, double y){
		setPosition(translateX + x, translateY + y);
	}
	
	
	public Point convertToCameraSpace(Point p){
		double x = (p.getX() - translateX);
		double y = (p.getY() - translateY);
		
		Point ret = new Point();
		ret.setLocation(x, y);
		return ret;
	}
	
	public Point convertFromCameraSpace(Point p){
		double x = (p.getX() + translateX);
		double y = (p.getY() + translateY);
		
		Point ret = new Point();
		ret.setLocation(x, y);
		return ret;
	}
	
	
	public double getTranslateX(){
		return translateX;
	}
	
	public double getTranslateY(){
		return translateY;
	}
	
	public double getVelocityX(){
		return translateX - prevTranslateX;
	}
	
	public double getVelocityY(){
		return translateY - prevTranslateY;
	}
	
	public void drawSelfAndChildren(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform saved = g2.getTransform();
		
//		g2.scale(3,3);
		g2.translate(translateX, translateY);
		
		super.drawSelfAndChildren(g);
		
		g2.setTransform(saved);		
	}
	
	
	
}
