package model;

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

	public Level(int width, int height) {
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
		map = new Map(width, height);
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
				if (tile.getHighlight() == Tile.Highlight.BLUE) {
					moveSelectedShipTo(x, y);
				}
			}
		}
	}

	protected void tileHovered(int x, int y) {
		Tile tile = map.getTile(x, y);

		// if mouse moved to a different tile,
		// let the old tile know it's not moused over
		// and let the new tile know it is moused over
		if (tileHovered != tile) {
			if (tileHovered != null)
				tileHovered.setMousedOver(false);

			tileHovered = tile;
			if (tileHovered != null)
				tileHovered.setMousedOver(true);
		}

		// place hover view next to ship if tile is a ship tile
		hoveredShipView.setShip(null);
		hoveredShipBorder.setShip(null);
		if (tile != null && tile.getHasShip() == true) {
			Ship ship = tile.getShip();
			if (ship != selectedShip) {
				hoveredShipView.setShip(ship);
				Point p = camera.convertFromCameraSpace(Map
						.mapToPixelCoords(new Point(x + 1, y)));
				hoveredShipView.setLocation((int) p.getX(), (int) p.getY());
				hoveredShipBorder.setShip(ship);
			}
		}

	}

	public void selectShip(Ship ship) {
		selectedShip = ship;
		selectedShipView.setShip(ship);
		selectedShipBorder.setShip(ship);

		map.clearHighLights();
		if (selectedShip != null)
			map.highlightPossibleMoves(selectedShip);
	}

	public void moveSelectedShipTo(int mapX, int mapY) {
		// disable mouse input whiel moving
		levelButton.disable();

		// update map
		Tile oldTile = map.getTile(selectedShip.getLocation());
		Tile newTile = map.getTile(mapX, mapY);

		// move the ship and pass in the observer
		selectedShip.moveWithDirections(enableInputObserver, mapX, mapY, map
				.shortestPath(selectedShip.getLocation(),
						new Point(mapX, mapY), selectedShip.getMoves()));

		oldTile.setEmpty();
		newTile.setHasShip(true, selectedShip);

		map.clearHighLights();

	}

	public void addShipToMap(Ship ship) {
		map.getTile(ship.getLocation()).setHasShip(true, ship); // testing
		shipHolder.addChild(ship);
	}

	public void onDestroy() {
		Input.getInstance().removeButton(levelButton);
	}

	public Camera getCamera() {
		return camera;
	}

}
