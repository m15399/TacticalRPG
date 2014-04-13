package input;

import utils.Position;

/*
 * Bounding box that can receive mouse inputs
 */
public class Button {
	
	private Position position;
	
	public Button(){
		position = new Position(0,0);
	}
	
	public Button(int x, int y, int width, int height){
		position = new Position(x, y);
		position.setScale(width, height);
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
