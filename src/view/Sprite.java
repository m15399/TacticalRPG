package view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import resources.ImageLibrary;

import model.Entity;

public class Sprite extends Entity {
	protected BufferedImage image;
	
	public Sprite(){}
	
	public Sprite(String filename){
		image = ImageLibrary.getInstance().getImage(filename);
	}
	
	public Sprite(String filename, int x, int y){
		super(x, y);
		image = ImageLibrary.getInstance().getImage(filename);
	}
	
	/*
	 * Setters and Getters for private instance variables.  Please add other methods 
	 * above these so they are easier to find.
	 */
	
	
	
	public BufferedImage getImage(){
		return image;
	}
	
	public void setImage(BufferedImage newImage){
		image = newImage;
	}
	
	public void draw(Graphics g){
		getPosition().transform(g);
		
		g.drawImage(image, -image.getWidth()/2,-image.getHeight()/2, null);
		
		getPosition().untransform(g);
	}
	
	
}
