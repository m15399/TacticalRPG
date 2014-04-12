package model;


/*
 * GameObject with a Position
 */
public class Entity extends GameObject {

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
	
}
