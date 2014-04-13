package model;

import input.Button;
import input.Input;
import input.TestButton;

import java.awt.Point;

import model.Tile.Highlight;
import view.Camera;
import view.GraphicsTest;
import view.SelectedShipView;
import view.Starfield;

/*
 * Root object for the main gameplay
 */
public class Level extends GameObject {

	private Map map;
	private Starfield starfield;
	private Camera camera;
	private Button levelButton;

	private Ship selectedShip;
	private SelectedShipView selectedShipView;

	public Level() {
		selectedShip = null;

		// Camera / Starfield
		camera = new Camera();
		camera.setPosition(-100, 0);

		starfield = new Starfield(Game.WIDTH, Game.HEIGHT, 7, 100, 22, 27, 300);
		starfield.setCamera(camera);
		addChild(starfield);

		addChild(camera);

		// Map
		map = new Map();
		camera.addChild(map);
		map.getTile(2, 2).setHasShip(true, new Scout(new Point(2, 2)));

		// Graphics Testing
		camera.addChild(new GraphicsTest());

		/*
		 * Add more stuff here
		 */

		// Selected Ship View
		selectedShipView = new SelectedShipView(new Ship(new Point(1, 1)));
		addChild(selectedShipView);

		// Background button for mouse input on the map
		levelButton = new LevelButton();
		Input.getInstance().addButton(levelButton);

		// Test Button
		TestButton tb = new TestButton(0, 0, 100, 100);
		Input.getInstance().addButton(tb);
		Input.getInstance().removeButton(tb);
	}

	private void tileClicked(int x, int y) {
		Tile tile = map.getTile(x, y);

		// clicked outside map
		if (tile == null) {
			selectShip(null);
			return;
		}

		// select ship clicked on, or unselect if clicked on empty space
		if (tile.getHasShip()) {
			selectShip(tile.getShip());

		} else {
			// tile is either terrain or edge
			selectShip(null);
		}
	}

	public void selectShip(Ship ship) {
		selectedShip = ship;
		selectedShipView.setShip(ship);
	}

	public void onDestroy() {
		Input.getInstance().removeButton(levelButton);
	}

	/*
	 * Button that covers the whole screen. Controls the mouse input for Level
	 */
	private class LevelButton extends Button {

		private double distanceDraggedSinceClick;

		LevelButton() {
			super(0, 0, Game.WIDTH, Game.HEIGHT);
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
			Point mapPoint = camera.convertToCameraSpace(mousePoint);

			// get the tile at those coords
			Point mapCoords = Map.pixelToMapCoords(mapPoint);
			return mapCoords;
		}

		/*
		 * Lets the Tile we're mousing over that it's being moused over
		 */
		public void mouseHovered() {

			Tile tile = map.getTile(mouseToMapCoords());

			// tell the tile it's being moused over
			if (tile != null)
				tile.setMousedOver();

		}

		/*
		 * Scroll the camera
		 */
		public void mouseDragged() {
			// move the camera by the velocity of the mouse
			Input input = Input.getInstance();
			double multiplier = -2;
			camera.moveBy(input.getVelocityX() * multiplier,
					input.getVelocityY() * multiplier);

			distanceDraggedSinceClick += Math.abs(input.getVelocityX())
					+ Math.abs(input.getVelocityY());
		}

		/*
		 * Do stuff for when the mouse is clicked Also move the camera by 0 so
		 * that it's velocity goes back to 0
		 */
		public void mouseReleased() {
			camera.moveBy(0, 0);

			// don't accept clicks if the user was trying to drag the camera
			if (distanceDraggedSinceClick < 2) {
				Point mapCoords = mouseToMapCoords();
				tileClicked((int) mapCoords.getX(), (int) mapCoords.getY());
			}

			distanceDraggedSinceClick = 0;
		}

		public void mouseReleasedOutsideButton() {
			camera.moveBy(0, 0);
			distanceDraggedSinceClick = 0;

		}

	}
}
