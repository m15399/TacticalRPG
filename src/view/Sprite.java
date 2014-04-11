package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import resources.ImageLibrary;

import model.Entity;
import model.Position;

public class Sprite extends Entity {
	private BufferedImage image;
	
	public Sprite(){}
	
	public Sprite(String filename){
		image = ImageLibrary.getInstance().getImage(filename);
	}
	
	public Sprite(String filename, int x, int y){
		super(x, y);
		image = ImageLibrary.getInstance().getImage(filename);
	}
	
	/*
	 * Setters and Getters for private instance variables.  Please add other methods above these so they are easier to find.
	 */
	
	
	
	public BufferedImage getImage(){
		return image;
	}
	
	public void setImage(BufferedImage newImage){
		image = newImage;
	}
	
	public void draw(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform saved = g2.getTransform();
		
		Position position = getPosition();
		
		g2.translate((int)position.getX(), (int)position.getY());
		g2.rotate(position.getRotation());
		g2.scale(position.getScaleX()* (position.getMirrored() ? -1 : 1), position.getScaleY());
		g2.drawImage(image, -image.getWidth()/2,-image.getHeight()/2, null);
		
		g2.setTransform(saved);
	}
	
	
}
