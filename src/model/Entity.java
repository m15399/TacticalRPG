package model;

import utils.Position;


/*
 * GameObject with a Position
 */
public class Entity extends GameObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8962361879638817771L;
	private Position position;
	
	public Entity(){
		position = new Position(0,0);
	}
	
	public Entity(int x, int y){
		position = new Position(x, y);
	}
	
	public void onDestroy(){
		position.removeFromParent();
	}
	
	public Position getPosition(){
		return position;
	}
	
	public void setSpatialParent(Entity e){
		getPosition().setParent(e.getPosition());
	}
	
}
