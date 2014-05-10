package model;

import input.Button;
import input.Input;

import java.awt.Point;

/*
 * Button that covers the whole screen. Controls the mouse input for Level
 */
public class LevelBackgroundButton extends Button {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8816166403833522598L;

	Level level;
	
	private double distanceDraggedSinceClick;

	LevelBackgroundButton(Level level) {
		super(0, 0, Game.WIDTH, Game.HEIGHT);
		
		this.level = level;
		
		getPosition().setZ(100); // make sure it's behind all other buttons
									// (UI, etc)
		distanceDraggedSinceClick = 0;
	}

	private Point mouseToMapCoords() {
		// get mouse location on screen
		Input input = Input.getInstance();
		Point mousePoint = new Point();
		mousePoint.setLocation(input.getX(), input.getY());

		// convert to camera coordinates
		Point mapPoint = level.getCamera().convertToCameraSpace(mousePoint);

		// get the tile at those coords
		Point mapCoords = Map.pixelToMapCoords(mapPoint);
		return mapCoords;
	}

	/*
	 * Lets the Tile we're mousing over that it's being moused over
	 */
	public void mouseHovered() {

		Point p = mouseToMapCoords();
		level.tileHovered((int)p.getX(), (int)p.getY());
		
	}

	/*
	 * Scroll the camera
	 */
	public void mouseDragged() {
		level.getCamera().setFollowTarget(null);
		
		// move the camera by the velocity of the mouse
		Input input = Input.getInstance();
		double multiplier = -2 / Game.FPSMUL;
		level.getCamera().moveBy(input.getVelocityX() * multiplier,
				input.getVelocityY() * multiplier);

		distanceDraggedSinceClick += Math.abs(input.getVelocityX())
				+ Math.abs(input.getVelocityY());
	}

	/*
	 * Do stuff for when the mouse is clicked 
	 */
	public void mouseReleased() {

		// don't accept clicks if the user was trying to drag the camera
		if (distanceDraggedSinceClick < 1) {
			Point mapCoords = mouseToMapCoords();
			level.tileClicked((int) mapCoords.getX(), (int) mapCoords.getY());
		}

		distanceDraggedSinceClick = 0;
	}

	public void mouseReleasedOutsideButton() {
		distanceDraggedSinceClick = 0;

	}
	
	public void mouseExited(){
		level.tileHovered(-1, -1); // pretend we moused off the board to unhiglight tiles
	}

}