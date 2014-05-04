package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

import utils.Position;

/*
 * Listens to mouse movement, objects can ask it 
 * for it's current location and velocity, and it keeps
 * track of a list of Buttons that it sends messages
 * to each update
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
	private double velocityX, velocityY;
	private boolean wasMovedThisFrame, wasPressedThisFrame,
			wasReleasedThisFrame;
	private boolean pressed;

	private ArrayList<Button> buttons;
	private Button lastButton;

	private boolean enabled;
	
	private Input() {
		mouseX = mouseY = prevMouseX = prevMouseY = velocityX = velocityY = 0;
		wasMovedThisFrame = pressed = wasPressedThisFrame = wasReleasedThisFrame = false;
		buttons = new ArrayList<Button>();
		lastButton = new Button();
		enabled = true;
	}

	public double getX() {
		return mouseX;
	}

	public double getY() {
		return mouseY;
	}

	public double getVelocityX() {
		return velocityX;
	}

	public double getVelocityY() {
		return velocityY;
	}

	public boolean getPressed() {
		return pressed;
	}

	public void addButton(Button b) {
		buttons.add(b);
		sortButtons();
	}

	public void removeButton(Button b) {
		buttons.remove(b);
		sortButtons();

	}

	private void setMouseLocation(double x, double y) {
		prevMouseX = mouseX;
		prevMouseY = mouseY;
		mouseX = x;
		mouseY = y;
		wasMovedThisFrame = true;
	}

	/*
	 * Sort buttons by their Z value
	 */
	private void sortButtons() {
		Collections.sort(buttons, new Comparator<Button>() {
			public int compare(Button o1, Button o2) {
				return (o1.getPosition().getZ() - o2.getPosition().getZ() > 0 ? 1
						: -1);
			}
		});
	}

	/*
	 * Return the button that the mouse is in (buttons are sorted by height so
	 * the top-most button that the mouse is in will be returned)
	 */
	private Button getButtonUnderMouse() {
		for (Button b : buttons) {
			Position p = b.getPosition();
			double x = p.getX();
			double y = p.getY();
			double sx = p.getScaleX();
			double sy = p.getScaleY();

			if (mouseX >= x && mouseX < x + sx) {
				if (mouseY >= y && mouseY < y + sy) {
					if (b.getEnabled())
						return b;
				}
			}
		}
		return new Button(); // button that does nothing
	}

	public void update() {
		if(!enabled)
			return;
		
		Button currButton;

		// Figure out what the current button is
		// The current button only changes if the mouse isn't pressed
		if (!pressed && !wasReleasedThisFrame) {
			currButton = getButtonUnderMouse();
			if (currButton != lastButton) {
				if (lastButton.getEnabled())
					lastButton.mouseExited();
				lastButton = currButton;
			}

		} else {
			currButton = lastButton;
		}

		// Update mouse velocity
		if (wasMovedThisFrame) {
			this.velocityX = mouseX - prevMouseX;
			this.velocityY = mouseY - prevMouseY;

			wasMovedThisFrame = false;
		} else {
			this.velocityX = 0;
			this.velocityY = 0;
		}

		// Check if mouse was pressed, released, hovered, or dragged this frame
		if (wasPressedThisFrame) {
			if (currButton.getEnabled())
				currButton.mousePressed();
			wasPressedThisFrame = false;

		} else if (wasReleasedThisFrame) {
			// make sure the mouse is still on the button
			if (getButtonUnderMouse() == currButton) {
				if (currButton.getEnabled())
					currButton.mouseReleased();
			} else {
				if (currButton.getEnabled())
					currButton.mouseReleasedOutsideButton();
			}
			wasReleasedThisFrame = false;

		} else if (pressed) {
			if (currButton.getEnabled())
				currButton.mouseDragged();

		} else {
			if (currButton.getEnabled())
				currButton.mouseHovered();
		}

	}
	
	public void setEnabled(boolean enabled){
		this.enabled = enabled;
	}
	
	public boolean getEnabled(){
		return enabled;
	}
	
	public void printNumButtons(){
		System.out.println("Input # buttons: " + buttons.size());
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
		wasPressedThisFrame = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		pressed = false;
		wasReleasedThisFrame = true;
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
