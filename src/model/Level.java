package model;

import input.Button;
import input.Input;
import input.TestButton;

import java.awt.Point;

import utils.Observable;
import utils.Observer;
import view.Camera;
import view.SelectedEnemyShip;
import view.SelectedShipBorder;
import view.SelectedShipBorder.SelectionType;
import view.SelectedShipView;
import view.Starfield;

/*
 * Root object for the main gameplay
 */
public class Level extends GameObject {

	private Map map;
	private Starfield starfield;
	private Camera camera;
	private LevelButton levelButton;

	private GameObject ships;
	
	Tile tileHovered;
	
	private Ship selectedShip;
	private SelectedShipView selectedShipView;
	private SelectedEnemyShip hoveredShipView;
	private SelectedShipBorder selectedShipBorder;
	private SelectedShipBorder hoveredShipBorder;

	Observer enableInputObserver;

	public Level() {
		selectedShip = null;
		tileHovered = null;
		
		// Camera / Starfield
		camera = new Camera();
		camera.setPosition(0, 0);

		starfield = new Starfield(Game.WIDTH, Game.HEIGHT, 7, 100, 22, 27, 300);
		starfield.setCamera(camera);
		addChild(starfield);

		addChild(camera);

		// Map
		map = new Map(16, 12);
		camera.addChild(map);

		// Graphics Testing
//		camera.addChild(new GraphicsTest());


		// Selected Ship View
		selectedShipView = new SelectedShipView();
		addChild(selectedShipView);
		hoveredShipView = new SelectedEnemyShip();
		addChild(hoveredShipView);

		// Ship Borders
		selectedShipBorder = new SelectedShipBorder();
		camera.addChild(selectedShipBorder);
		hoveredShipBorder = new SelectedShipBorder();
		camera.addChild(hoveredShipBorder);
		hoveredShipBorder.setSelectionType(SelectionType.HOVER);
		
		
		// Ships Holder
		ships = new GameObject();
		camera.addChild(ships);

		// Ship Testing
		Scout scout = new Scout(new Point(2, 2)); 
		scout.updateHull(-30);
		addShipToMap(scout);
		
		Bomber bomber = new Bomber(new Point(4, 4));
		bomber.setTeam(1);
		addShipToMap(bomber);
		addShipToMap(new Fighter(new Point(4, 2)));

		// Background button for mouse input on the map
		levelButton = new LevelButton();
		Input.getInstance().addButton(levelButton);

		// Test Button
		TestButton tb = new TestButton(0, 0, 100, 100);
		Input.getInstance().addButton(tb);
		Input.getInstance().removeButton(tb);

		// Enable input observer
		enableInputObserver = new Observer() {
			public void notified(Observable sender) {
				levelButton.enable();
			}
		};
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
			// selectShip(null);
			if (selectedShip != null) {
				moveSelectedShipTo(x, y);
			}
		}
	}
	
	private void tileHovered(int x, int y){
		Tile tile = map.getTile(x, y);

		// mouse moved out of last tile hovered over
		if(tileHovered != tile){
			if(tileHovered != null)
				tileHovered.setMousedOver(false);
			
			tileHovered = tile;
			if(tileHovered != null)
				tileHovered.setMousedOver(true);
		}
		
		// place hover view if it's a ship tile
		hoveredShipView.setShip(null);
		hoveredShipBorder.setShip(null);
		if(tile != null && tile.getHasShip() == true){
			Ship ship = tile.getShip();
			
			if(ship != selectedShip){
				hoveredShipView.setShip(ship);
				
				Point p = camera.convertFromCameraSpace(Map.mapToPixelCoords(new Point(x+1, y)));
				
				hoveredShipView.setLocation((int)p.getX(),(int)p.getY());
				hoveredShipBorder.setShip(ship);
			}
			
		}

	}

	public void selectShip(Ship ship) {
		selectedShip = ship;
		selectedShipView.setShip(ship);
		
		selectedShipBorder.setShip(ship);
		
		map.clearHighLights();
		if(selectedShip != null)
			map.highlightPossibleMoves(selectedShip);

	}

	public void moveSelectedShipTo(int mapX, int mapY) {
		// disable mouse input whiel moving
		levelButton.disable();

		// update map
		Tile oldTile = map.getTile(selectedShip.getLocation());
		Tile newTile = map.getTile(mapX, mapY);
		oldTile.setEmpty();
		newTile.setHasShip(true, selectedShip);

		map.clearHighLights();
		
		// move the ship and pass in the observer
		selectedShip.moveWithDirections(enableInputObserver, mapX, mapY,
				map.shortestPath(selectedShip.getLocation(), new Point(mapX,
						mapY)));

	}

	public void addShipToMap(Ship ship) {
		map.getTile(ship.getLocation()).setHasShip(true, ship); // testing
		ships.addChild(ship);
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

			Point p = mouseToMapCoords();
			tileHovered((int)p.getX(), (int)p.getY());
			
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
			if (distanceDraggedSinceClick < 1) {
				Point mapCoords = mouseToMapCoords();
				tileClicked((int) mapCoords.getX(), (int) mapCoords.getY());
			}

			distanceDraggedSinceClick = 0;
		}

		public void mouseReleasedOutsideButton() {
			camera.moveBy(0, 0);
			distanceDraggedSinceClick = 0;

		}
		
		public void mouseExited(){
			tileHovered(-1, -1); // pretend we moused off the board to unhiglight tiles
		}

	}
}
