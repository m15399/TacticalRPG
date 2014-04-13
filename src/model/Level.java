package model;

import input.Button;
import input.Input;

import java.awt.Point;

import view.Camera;
import view.GraphicsTest;
import view.SelectedShipView;
import view.Starfield;

/*
 * Root object for the main gameplay
 */
public class Level extends GameObject  {
	
	private Map map;
	private Starfield starfield;
	private Camera camera;
	private Button levelButton;

	public Level(){
		
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
		
		// Graphics Testing
		camera.addChild(new GraphicsTest());
		
		
		/*
		 *  Add more stuff here
		 */
		
		// Selected Ship View
		addChild(new SelectedShipView(new Ship(new Point(1, 1))));


		
		
		// Background button for mouse input on the map
		levelButton = new LevelButton();
		Input.getInstance().addButton(levelButton);
		
		// Test Button
//		Input.getInstance().addButton(new TestButton(0,0,0,0));
	}
	
	public void onDestroy(){
		Input.getInstance().removeButton(levelButton);
	}
	
	/*
	 * Button that covers the whole screen. Controls the mouse input for Level
	 */
	private class LevelButton extends Button {
		
		LevelButton(){
			super(0, 0, Game.WIDTH, Game.HEIGHT);
			getPosition().setZ(100); // make sure it's behind all other buttons (UI, etc)
			
		}
		
		/*
		 * Lets the Tile we're mousing over that it's being moused over
		 */
		public void mouseHovered(){
			// get mouse location on screen
			Input input = Input.getInstance();
			Point mousePoint = new Point();
			mousePoint.setLocation(input.getX(), input.getY());
			
			// convert to camera coordinates
			Point mapPoint = camera.convertToCameraSpace(mousePoint);
			
			// get the tile at those coords
			Point mapCoords = Map.pixelToMapCoords(mapPoint);
			Tile tile = map.getTile(mapCoords);
			
			// tell the tile it's being moused over
			if(tile != null)
				tile.setMousedOver();
		}
		
		/*
		 * Scroll the camera
		 */
		public void mouseDragged(){
			// move the camera by the velocity of the mouse
			Input input = Input.getInstance();
			double multiplier = -2;
			camera.moveBy(input.getVelocityX() * multiplier, input.getVelocityY() * multiplier);
		}
		
		/*
		 * Move the camera by 0 so that it's velocity goes back to 0
		 */
		public void mouseReleased(){
			camera.moveBy(0,0);
		}
		
		public void mouseReleasedOutsideButton(){
			camera.moveBy(0, 0);
		}
		
	}
}
