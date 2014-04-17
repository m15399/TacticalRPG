package model;

import input.Button;
import input.Input;

import java.awt.Point;
import java.util.List;

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

	/*
	 * Current state - attacking, moving, etc
	 */
	private enum TurnState {
		DEFAULT, ANIMATING, MOVING, ATTACKING;
	}

	private Map map;
	private Starfield starfield;
	private Camera camera;
	private LevelBackgroundButton levelButton;

	private GameObject shipHolder; // parent object for the ships
	private GameObject enemyShipHolder;

	private int currentTeam;

	private Tile tileHovered;

	private Ship selectedShip;

	private SelectedShipView selectedShipView;
	private SelectedEnemyShip hoveredShipView;

	private SelectedShipBorder selectedShipBorder;
	private SelectedShipBorder hoveredShipBorder;

	private SelectedShipButtons shipButtons;

	private Observer enterDefaultStateObserver;

	private TurnState state;

	public Level(int width, int height) {
		selectedShip = null;
		tileHovered = null;
		currentTeam = 0;

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
		enemyShipHolder = new GameObject();
		camera.addChild(enemyShipHolder);

		// Ship buttons
		shipButtons = new SelectedShipButtons();
		addChild(shipButtons);

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

		// Background button for mouse input on the map
		levelButton = new LevelBackgroundButton(this);
		Input.getInstance().addButton(levelButton);

		enterDefaultStateObserver = new Observer() {
			public void notified(Observable sender) {
				enterDefaultState();
			}
		};

		enterDefaultState();
	}

	public void update() {
		// keep the buttons in the right place
		if (selectedShip != null) {
			int x = selectedShip.getLocation().x;
			int y = selectedShip.getLocation().y;
			Point p = camera.convertFromCameraSpace(Map
					.mapToPixelCoords(new Point(x + 1, y)));
			shipButtons.setLocation((int) p.getX(), (int) p.getY());
		}

	}

	public List<GameObject> getShips(int team) {
		List<GameObject> ships;
		if (team == 0) {
			ships = shipHolder.getChildren();
		} else {
			ships = enemyShipHolder.getChildren();
		}
		return ships;
	}

	public void startTurn(int team) {
		currentTeam = team;
		List<GameObject> ships = getShips(team);
		for (GameObject o : ships) {
			Ship s = (Ship) o;
			s.startTurn();
		}
		selectShip(getNextShipWithMoves());
	}

	public Ship getNextShipWithMoves() {
		List<GameObject> ships = getShips(currentTeam);
		for (GameObject o : ships) {
			Ship s = (Ship) o;
			if (!s.getIsWaiting()) {
				return s;
			}
		}
		return null;
	}

	public void selectNextShip() {
		Ship nextShip = getNextShipWithMoves();
		if (nextShip != null) {
			selectShip(nextShip);
			
		} else { // no more ships, turn is over
			int nextTeam = currentTeam + 1;
			if (nextTeam > 1)
				nextTeam = 0;
			startTurn(nextTeam);
		}
		return;

	}

	/*
	 * 
	 * States
	 */

	/*
	 * Default state - if a ship is selected, shows its buttons, otherwise does
	 * nothing
	 */
	public void enterDefaultState() {
		levelButton.enable();

		shipButtons.setShip(selectedShip);

		if (selectedShip != null) {

			if (selectedShip.getCanMove()) {
				shipButtons.addButton("Move", new Button() {
					public void mouseReleased() {
						enterMoveState();
					}
				});
			}
			if (selectedShip.getCanAttack()) {
				shipButtons.addButton("Attack", new Button() {
					public void mouseReleased() {
						enterAttackState();
					}
				});
			}
			shipButtons.addButton("Wait", new Button() {
				public void mouseReleased() {
					selectedShip.setIsWaiting(true);
					selectNextShip();
				}
			});

		}

		map.clearHighLights();
		state = TurnState.DEFAULT;
	}

	/*
	 * Animating state
	 */
	public void enterAnimatingState() {
		map.clearHighLights();
		levelButton.disable();
	}

	/*
	 * Called when player clicks on the Move button
	 */
	public void enterMoveState() {
		if (selectedShip == null) {
			System.out
					.println("Error: Entered move state with no ship selected");
			System.exit(1);
		}

		// highlight tiles
		map.clearHighLights();
		if (selectedShip != null) {
			map.highlightTilesAroundShip(selectedShip, selectedShip.getMoves(),
					true, Tile.Highlight.BLUE);
		}
		
		// turn off buttons
		shipButtons.setShip(null);

		state = TurnState.MOVING;

		// if ship can't move, just go back to default state
		if (selectedShip != null && !selectedShip.getCanMove()) {
			enterDefaultState();
		}
	}

	public void enterAttackState() {
		if (selectedShip == null) {
			System.out
					.println("Error: Entered attack state with no ship selected");
			System.exit(1);
		}

		// highlight attack range
		map.clearHighLights();
		if (selectedShip != null)
			map.highlightTilesAroundShip(selectedShip, selectedShip.getRange(),
					false, Tile.Highlight.RED);

		// turn off buttons
		shipButtons.setShip(null);

		state = TurnState.ATTACKING;
	}

	/*
	 * 
	 * Handling player actions
	 */

	public void selectShip(Ship ship) {
		selectedShip = ship;
		selectedShipView.setShip(ship);
		selectedShipBorder.setShip(ship);

		enterMoveState();
	}

	public void unselectShip() {
		selectedShip = null;
		selectedShipView.setShip(null);
		selectedShipBorder.setShip(null);

		enterDefaultState();
	}

	public void moveSelectedShipTo(int mapX, int mapY) {
		enterAnimatingState();

		// update map
		Tile oldTile = map.getTile(selectedShip.getLocation());
		Tile newTile = map.getTile(mapX, mapY);

		// move the ship and pass in the observer
		selectedShip.moveWithDirections(enterDefaultStateObserver, mapX, mapY,
				map.shortestPath(selectedShip.getLocation(), new Point(mapX,
						mapY), selectedShip.getMoves()));

		oldTile.setEmpty();
		newTile.setHasShip(true, selectedShip);
	}

	public void attackShipAt(int mapX, int mapY) {
		enterAnimatingState();

		selectedShip.attack(map.getTile(mapX, mapY).getShip());

		// play animation - TODO
		enterDefaultState();
	}

	public void addShipToMap(Ship ship) {
		ship.setTeam(0);
		map.getTile(ship.getLocation()).setHasShip(true, ship); // testing
		shipHolder.addChild(ship);
	}

	public void addEnemyShipToMap(Ship ship) {
		ship.setTeam(1);
		map.getTile(ship.getLocation()).setHasShip(true, ship); // testing
		enemyShipHolder.addChild(ship);
	}

	/*
	 * 
	 * Input handling
	 */
	protected void tileClicked(int x, int y) {
		Tile tile = map.getTile(x, y);

		// clicked outside map
		if (tile == null) {
			unselectShip();
			return;
		}

		// clicked on a ship
		if (tile.getHasShip()) {

			Ship ship = tile.getShip();

			if (ship.getTeam() == currentTeam) {
				if (ship == selectedShip) { // clicked on self
					enterDefaultState();
				} else {
					selectShip(ship);
				}
			} else {
				if (state == TurnState.ATTACKING
						&& tile.getHighlight() != Tile.Highlight.NONE) {
					attackShipAt(x, y);
				} else {
					enterDefaultState();
				}
			}

		} else {
			// tile is not a shiptile

			if (tile.getHighlight() == Tile.Highlight.NONE) {
				// clicking on empty tile
				unselectShip();
			} else if (state == TurnState.MOVING) {
				moveSelectedShipTo(x, y);
			} else if (state == TurnState.ATTACKING) {
				enterDefaultState();
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

	public void onDestroy() {
		Input.getInstance().removeButton(levelButton);
	}

	public Camera getCamera() {
		return camera;
	}

	public Map getMap() {
		return map;
	}

}
