package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import resources.ImageLibrary;

import model.GameObject;
import model.Position;

public class Sprite extends GameObject{
	private Position position;
	private BufferedImage image;
	
	public Sprite(){}
	
	public Sprite(String filename){
		image = ImageLibrary.getInstance().getImage(filename);
		position = new Position(0,0);
	}
	
	public Sprite(String filename, int x, int y){
		image = ImageLibrary.getInstance().getImage(filename);
		position = new Position(x, y);
	}
	
	/*
	 * Setters and Getters for private instance variables.  Please add other methods above these so they are easier to find.
	 */
	
	public Position getPosition(){
		return position;
	}
	
	public BufferedImage getImage(){
		return image;
	}
	
	public void setImage(BufferedImage newImage){
		image = newImage;
	}
	
	public void draw(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform saved = g2.getTransform();
		
		g2.translate((int)position.getX(), (int)position.getY());
		g2.rotate(position.getRotation());
		g2.scale(position.getScaleX()* (position.getMirrored() ? -1 : 1), position.getScaleY());
		g2.drawImage(image, -image.getWidth()/2,-image.getHeight()/2, null);
		
		g2.setTransform(saved);
	}
	
	
}
