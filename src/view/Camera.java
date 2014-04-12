package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import model.GameObject;

/*
 * Camera class, very simple for now
 */
public class Camera extends GameObject {

	private double translateX, translateY;
	
	public Camera(){
		translateX = translateY = 0;
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
