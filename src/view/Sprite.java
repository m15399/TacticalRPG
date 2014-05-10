package view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import resources.ImageLibrary;

import model.Entity;

public class Sprite extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -816486410686236510L;
	protected transient BufferedImage image;
	private String filename;
	
	public Sprite(){
		image = null;
	}
	
	public Sprite(String filename){
		setImage(filename);
	}
	
	public Sprite(String filename, int x, int y){
		super(x, y);
		setImage(filename);
	}
	
	public Sprite(int x, int y){
		super(x, y);
		image = null;
	}
	
	/*
	 * Setters and Getters for private instance variables.  Please add other methods 
	 * above these so they are easier to find.
	 */
	
	public BufferedImage getImage(){
		return image;
	}
	
	public String getFilename(){
		return filename;
	}
	
	public void setImage(String filename){
		image = ImageLibrary.getInstance().getImage(filename);
		this.filename = filename;

	}
	
	public void draw(Graphics g){
		getPosition().transform(g);
		
		g.drawImage(image, -image.getWidth()/2,-image.getHeight()/2, null);
		
		getPosition().untransform(g);
	}
	
	private void writeObject(ObjectOutputStream s) throws IOException{
		s.defaultWriteObject();
	}
	
	private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException{
		s.defaultReadObject();
		setImage(filename);
	}
	
	
}
