package model;

import input.Button;
import input.Input;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import strategies.RandomStrategy;
import strategies.Strategy;
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
	private int numHumans;

	private Tile tileHovered;

	private Ship selectedShip;

	private SelectedShipView selectedShipView;
	private SelectedEnemyShip hoveredShipView;

	private SelectedShipBorder selectedShipBorder;
	private SelectedShipBorder hoveredShipBorder;

	private SelectedShipButtons shipButtons;

	private Observer enterDefaultStateObserver;

	private TurnState state;
	
	private Strategy aiStrategy;

	public Level(int width, int height) {
		selectedShip = null;
		tileHovered = null;
		currentTeam = 0;
		numHumans = 1;

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
		
		// AI 
		aiStrategy = new RandomStrategy();

		enterDefaultStateObserver = new Observer() {
			public void notified(Observable sender) {
				enterDefaultState();
			}
		};

		enterDefaultState();
	}

	public void update() {
		
		if(isAITurn() && state == TurnState.DEFAULT){
			takeNextAIMove();
		}
		
		// keep the buttons in the right place
		if (selectedShip != null) {
			int x = selectedShip.getLocation().x;
			int y = selectedShip.getLocation().y;
			Point p = camera.convertFromCameraSpace(Map
					.mapToPixelCoords(new Point(x + 1, y)));
			shipButtons.setLocation((int) p.getX(), (int) p.getY());
		}

	}
	
	public List<Ship> getShips(int team) {
		List<GameObject> ships;
		if (team == 0) {
			ships = shipHolder.getChildren();
		} else {
			ships = enemyShipHolder.getChildren();
		}
		
		List<Ship> list = new ArrayList<Ship>();
		
		for(GameObject o : ships){
			Ship s = (Ship) o;
			list.add(s);
		}
		
		return list;
	}
	
	public List<Ship> getShips(){
		List<Ship> ships;
		List<Ship> list = new ArrayList<Ship>();
		
		for(int i = 0; i < 2; i++){
			ships = getShips(i);
			
			for(Ship s : ships){
				list.add(s);
			}
		}		
		
		return list;
	}
	
	public boolean isAITurn(){
		return currentTeam >= numHumans;
	}

	public void startTurn(int team) {
		currentTeam = team;
		List<Ship> ships = getShips(team);
		for (Ship s : ships) {
			s.startTurn();
		}
		
		if(!isAITurn()){
			selectNextShip();
		} else {
			takeNextAIMove();
		}
	}
	
	public void endTurn(){
		int nextTeam = currentTeam + 1;
		if (nextTeam > 1)
			nextTeam = 0;
		startTurn(nextTeam);
	}
	
	public void takeNextAIMove(){
		for(Ship s : getShips()){
			if(!s.getIsWaiting()){
				s.setIsWaiting(true);
				aiStrategy.doNextAction(s, this);
				return;
			}
		}
	}
	
	public List<Ship> getPossibleTargetsForAI(Ship s){
		selectShip(s);
		enterAttackState();
		List<Ship> enemies = getShips(0); // get player's ships
		List<Ship> targets = new ArrayList<Ship>();
		
		for(Ship e : enemies){
			if(map.getTile(e.getLocation()).getHighlight() == Tile.Highlight.RED){
				targets.add(e);
			}
		}
		
		return targets;
	}
	
	public List<Point> getPossibleMovesForAI(Ship s){
		selectShip(s);
		enterMoveState();
		
		List<Point> moves = new ArrayList<Point>();
		
		for(int x = 0; x < map.getWidth(); x++){
			for(int y = 0; y < map.getHeight(); y++){
				Tile t = map.getTile(x, y);
				if(t.getHighlight() == Tile.Highlight.BLUE){
					moves.add(new Point(x, y));
				}
			}
		}
		
		return moves;
	}

	public Ship getNextShipWithMoves() {
		for (Ship s : getShips(currentTeam)) {
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
			endTurn();
		}
		return;

	}
	
	public void checkForDestroyedUnits(){
		for(Ship s: getShips()){
			if(s.isShipDead()){
				s.destroy();
			}
		}
	}

	public void printAllUnits(){
		System.out.println("\n\nSTATUS:\n");
		for(Ship s: getShips()){
			System.out.println(s.toString());
		}
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
		
		if(isAITurn() && getNextShipWithMoves() == null){
			endTurn();
		}
		
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

	public void moveShipTo(Ship ship, int mapX, int mapY) {
		enterAnimatingState();

		// update map
		Tile oldTile = map.getTile(ship.getLocation());
		Tile newTile = map.getTile(mapX, mapY);

		// move the ship and pass in the observer
		ship.moveWithDirections(enterDefaultStateObserver, mapX, mapY,
				map.shortestPath(ship.getLocation(), new Point(mapX,
						mapY), ship.getMoves()));

		oldTile.setEmpty();
		newTile.setHasShip(true, ship);
		
		printAllUnits();
	}

	public void attackShip(Ship attacker, Ship defender) {
		enterAnimatingState();

		attacker.attack(defender);
		
		checkForDestroyedUnits();
		printAllUnits();

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
					attackShip(selectedShip, ship);
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
				moveShipTo(selectedShip, x, y);
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
