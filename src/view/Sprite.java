package src.view;

import java.awt.Image;
import java.awt.Point;

import src.model.GameObject;

public class Sprite extends GameObject{
	private Point location;
	private Image image;
	
	public Sprite(){}
	
	public Sprite(Image setImage){
		image = setImage;
	}
	
	public Sprite(Image setImage, Point setLocation){
		image = setImage;
		location = setLocation;
	}
	
	/*
	 * Setters and Getters for private instance variables.  Please add other methods above these so they are easier to find.
	 */
	
	public Point getLocation(){
		return location;
	}
	
	public void setLocation(Point newLocation){
		location = newLocation;
	}
	
	public Image getImage(){
		return image;
	}
	
	public void setImage(Image newImage){
		image = newImage;
	}
}
