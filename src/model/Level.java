package model;

import input.Button;
import input.Input;

import java.awt.Point;

import utils.Observable;
import utils.Observer;
import view.Camera;
import view.SelectedEnemyShip;
import view.SelectedShipBorder;
import view.SelectedShipBorder.SelectionType;
import view.SelectedShipButtons;
import view.SelectedShipView;
import view.Starfield;

/*
 * Root object for the main gameplay
 */
public class Level extends GameObject {

	private Map map;
	private Starfield starfield;
	private Camera camera;
	private LevelBackgroundButton levelButton;

	private GameObject shipHolder; // parent object for the ships
	
	Tile tileHovered;
	
	private Ship selectedShip;
	
	private SelectedShipView selectedShipView;
	private SelectedEnemyShip hoveredShipView;
	
	private SelectedShipBorder selectedShipBorder;
	private SelectedShipBorder hoveredShipBorder;
	
	private SelectedShipButtons shipButtons;

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
		map = new Map(6, 6);
		camera.addChild(map);
		
		// Ships Holder
		shipHolder = new GameObject();
		camera.addChild(shipHolder);

		
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
		
		// Ship buttons
		shipButtons = new SelectedShipButtons();
		camera.addChild(shipButtons);
		
//		Bomber bomber = new Bomber(new Point(4, 4));
//		bomber.setTeam(1);
//		addShipToMap(bomber);
//		addShipToMap(new Fighter(new Point(4, 2)));


		// Background button for mouse input on the map
		levelButton = new LevelBackgroundButton(this);
		Input.getInstance().addButton(levelButton);

		// Enable input observer - enables input when notified
		enableInputObserver = new Observer() {
			public void notified(Observable sender) {
				levelButton.enable();
			}
		};
	}

	protected void tileClicked(int x, int y) {
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
				if(tile.getHighlight() == Tile.Highlight.BLUE){
					moveSelectedShipTo(x, y);
				}
			}
		}
	}
	
	protected void tileHovered(int x, int y){
		Tile tile = map.getTile(x, y);

		// if mouse moved to a different tile, 
		// let the old tile know it's not moused over
		// and let the new tile know it is moused over
		if(tileHovered != tile){
			if(tileHovered != null)
				tileHovered.setMousedOver(false);
			
			tileHovered = tile;
			if(tileHovered != null)
				tileHovered.setMousedOver(true);
		}
		
		// place hover view next to ship if tile is a ship tile
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
						mapY), selectedShip.getMoves()));

	}

	public void addShipToMap(Ship ship) {
		map.getTile(ship.getLocation()).setHasShip(true, ship); // testing
		shipHolder.addChild(ship);
	}

	public void onDestroy() {
		Input.getInstance().removeButton(levelButton);
	}
	
	public Camera getCamera(){
		return camera;
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
				System.out.println((int) mapCoords.getX() + (int) mapCoords.getY());
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
