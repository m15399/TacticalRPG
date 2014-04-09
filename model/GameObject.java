package model;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class GameObject {
	private List<GameObject> children;
	
	public GameObject(){
		/*
		 * Initializing private instance variables here
		 */
		children = new ArrayList<GameObject>();
	}
	
	public void addChild(GameObject object){
		children.add(object);
	}
	
	public void removeChild(GameObject object){
		children.remove(object);
	}
	
	public void destory(){
		//TODO implement
	}
	
	public void update(){
		//TODO implement
	}
	
	public void draw(Graphics g){
		//TODO implement
	}
	
	/*
	 * Setters and Getters for private instance variables.
	 */
	
	public List<GameObject> getChildren(){
		return children;
	}
	
	public void setChildren(List<GameObject> newChildren){
		children = newChildren;
	}
}
