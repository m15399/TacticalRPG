package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


/*
 * Listens to mouse movement, objects can ask it 
 * for it's current location and velocity
 */
public class Input implements MouseMotionListener, MouseListener {
	
	/*
	 * Singleton class
	 */
	private static Input instance = null;
	public static Input getInstance() {
		if (instance == null) {
			instance = new Input();
		}
		return instance;
	}
	
	
	private double mouseX, mouseY, prevMouseX, prevMouseY;
	private double draggedX, draggedY;
	private boolean wasMovedThisFrame;
	private boolean pressed;
	
	private Input(){
		mouseX = mouseY = prevMouseX = prevMouseY = draggedX = draggedY = 0;
		wasMovedThisFrame = pressed = false;
	}
	
	
	
	public double getX(){
		return mouseX;
	}
	
	public double getY(){
		return mouseY;
	}
	
	public double getVelocityX(){
		return draggedX;
	}
	
	public double getVelocityY(){
		return draggedY;
	}
	
	public boolean getPressed(){
		return pressed;
	}
	
	
	private void setMouseLocation(double x, double y){
		prevMouseX = mouseX;
		prevMouseY = mouseY;
		mouseX = x;
		mouseY = y;
		wasMovedThisFrame = true;
	}
	
	public void update(){
		if(wasMovedThisFrame){
			this.draggedX = mouseX - prevMouseX;
			this.draggedY = mouseY - prevMouseY;
			wasMovedThisFrame = false;
		} else {
			this.draggedX = 0;
			this.draggedY = 0;
		}
		
	}
	

	
	
	/*
	 * Mouse listener stuff
	 */
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pressed = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		pressed = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		setMouseLocation(e.getPoint().getX(), e.getPoint().getY());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		setMouseLocation(e.getPoint().getX(), e.getPoint().getY());
	}
	
}
