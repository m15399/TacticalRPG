package input;

import java.io.Serializable;

import utils.Position;

/*
 * Bounding box that can receive mouse inputs
 */
public class Button implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -665811486838955958L;
	private Position position;
	private boolean enabled;
	
	public Button(){
		position = new Position(0,0);
		enabled = true;
	}
	
	public Button(int x, int y, int width, int height){
		position = new Position(x, y);
		position.setScale(width, height);
		enabled = true;
	}
	
	public boolean getEnabled(){
		return enabled;
	}
	
	public void enable(){
		enabled = true;
	}
	
	public void disable(){
		mouseExited();
		enabled = false;
	}
	
	public Position getPosition(){
		return position;
	}
	
	public void mousePressed(){
		
	}
	
	public void mouseReleased(){
		
	}
	
	public void mouseReleasedOutsideButton(){
		
	}
	
	public void mouseHovered(){
		
	}
	
	public void mouseDragged(){
		
	}
	
	public void mouseExited(){
		
	}
}
