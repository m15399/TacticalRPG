package model;

import input.Button;
import input.Input;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import actions.TimerAction;

import strategies.RandomStrategy;
import strategies.Strategy;
import utils.Observable;
import utils.Observer;
import view.Camera;
import view.SelectedEnemyShip;
import view.ShipOutline;
import view.ShipOutline.SelectionType;
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
	
	private TurnState state;
	
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

	// UI elements
	private SelectedShipView selectedShipView;
	private SelectedEnemyShip hoveredShipView;
	private SelectedShipButtons shipButtons;

	private Observer enterDefaultStateObserver;
	
	private Strategy aiStrategy;

	public Level(int width, int height) {
		selectedShip = null;
		tileHovered = null;
		currentTeam = 0;
		numHumans = 1;

		// Camera / Starfield
		camera = new Camera(width, height);
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

		// Background button for mouse input on the map
		levelButton = new LevelBackgroundButton(this);
		Input.getInstance().addButton(levelButton);
		
		// AI 
		aiStrategy = new RandomStrategy();
		
		// test zoom buttons
//		int bsize = 20;
//		final double sfac = 1.1;
//		Input.getInstance().addButton(new Button(0, 0, bsize, bsize){
//			public void mouseReleased(){
//				camera.setZoom(camera.getZoom() * sfac);
//			}
//		});
//		Input.getInstance().addButton(new Button(0, bsize, bsize, bsize){
//			public void mouseReleased(){
//				camera.setZoom(camera.getZoom() / sfac);
//			}
//		});
		

		enterDefaultStateObserver = new Observer() {
			public void notified(Observable sender) {
				enterDefaultState();
			}
		};

		enterDefaultState();
	}
	
	
	
	public void onDestroy() {
		Input.getInstance().removeButton(levelButton);
	}

	public void updateButtons(){
		// keep the buttons in the right place
		if (selectedShip != null) {
			int x = selectedShip.getLocation().x;
			int y = selectedShip.getLocation().y;
			Point p = camera.convertFromCameraSpace(Map
					.mapToPixelCoords(new Point(x + 1, y)));
			shipButtons.setLocation((int) p.getX(), (int) p.getY());
		}
	}
	
	public void update() {
				
//		if(isAITurn()){
//			if(state == TurnState.DEFAULT){
//				if(getNextShipWithMoves() == null)
//					endTurn();
//				else
//					takeNextAIMove();
//			}
//		}
		
		updateButtons();

		// figure out how to outline ships
		List<Ship> ships = getShips();
		for(Ship s : ships){
			ShipOutline o = s.getVisual().getOutline();
			o.setSelectionType(SelectionType.NONE);
		}
		if(!isAITurn() && state != TurnState.ANIMATING){
			ships = getShips(currentTeam);
			for(Ship s : ships){
				ShipOutline o = s.getVisual().getOutline();
				if(s == selectedShip){
					o.setSelectionType(SelectionType.SELECTED);
				} else if(!s.getIsWaiting()){
					o.setSelectionType(SelectionType.ACTIONSLEFT);
				}
			}
		}
		
		
	}
	
	
	
	/*
	 * 
	 * Turn logic
	 * 
	 */
	
	public boolean isAITurn(){
		return currentTeam >= numHumans;
	}

	public void startTurn(int team) {
		currentTeam = team;
		List<Ship> ships = getShips(team);
		for (Ship s : ships) {
			s.startTurn();
		}
		
		enterDefaultState();

		if(!isAITurn()){
			selectPlayerNextShip();
		} 
	}
	
	public void endTurn(){
		
		int nextTeam = currentTeam + 1;
		if (nextTeam > 1)
			nextTeam = 0;
		
		startTurn(nextTeam);
	}
	
	public void checkForDestroyedUnits(){
		for(Ship s: getShips()){
			if(s.isShipDead()){
				removeShipFromMap(s);
			}
		}
	}
	
	private Ship getNextShipWithMoves() {
		for (Ship s : getShips(currentTeam)) {
			if (!s.getIsWaiting()) {
				return s;
			}
		}
		return null;
	}

	public void printAllUnits(){
		System.out.println("\n\nSTATUS:\n");
		for(Ship s: getShips()){
			System.out.println(s.toString());
		}
	}
	
	
	/*
	 * 
	 * AI Management
	 * 
	 */
	
	public void takeNextAIMove(){
		for(Ship s : getShips()){
			if(!s.getIsWaiting()){
				selectShip(s);
				aiStrategy.doNextAction(s, this);
				return;
			}
		}
		endTurn();
	}
	
	public List<Ship> getPossibleTargetsForAI(Ship s){
		
		List<Ship> targets = new ArrayList<Ship>();
		if(!s.getCanAttack())
			return targets;
		
		List<Tile> tiles = map.getTilesWithinRange(s.getLocation(), s.getRange(), false);
		
		List<Ship> enemies = getShips(0); // get player's ships
		
		for(Ship e : enemies){
			if(tiles.contains(map.getTile(e.getLocation()))){
				targets.add(e);
			}
		}
		
		return targets;
	}
	
	public List<Point> getPossibleMovesForAI(Ship s){

		List<Point> moves = new ArrayList<Point>();
		if(!s.getCanMove())
			return moves;
		
		List<Tile> tiles = map.getTilesWithinRange(s.getLocation(), s.getMovesLeft(), true);
		
		for(Tile t : tiles){
			moves.add(t.getLocation());
		}
		
		return moves;
	}

	
	
	/*
	 * 
	 * States
	 * 
	 * 
	 */

	/*
	 * Default state - if a ship is selected, shows its buttons, otherwise does
	 * nothing
	 */
	public void enterDefaultState() {
		if(!isAITurn()) {
			levelButton.enable();
			shipButtons.setShip(selectedShip);
		} else {
			hoveredShipView.setShip(null);
		}
		updateButtons();
		
		if(isAITurn())
			takeNextAIMove();
		

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
					waitShip(selectedShip);
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
		state = TurnState.ANIMATING;
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
			List<Tile> possibleMoves = map.getTilesWithinRange(selectedShip.getLocation(), 
					selectedShip.getMovesLeft(), true);
			map.highlightTiles(possibleMoves, Tile.Highlight.BLUE);
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
		if (selectedShip != null){
			List<Tile> possibleMoves = map.getTilesWithinRange(selectedShip.getLocation(), 
					selectedShip.getRange(), false);
			map.highlightTiles(possibleMoves, Tile.Highlight.RED);
		}

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
		if(!isAITurn())
			enterMoveState();
	}
	
	public void selectPlayerNextShip() {
		Ship nextShip = getNextShipWithMoves();
		if (nextShip != null) {
			selectShip(nextShip);
			camera.setFollowTarget(nextShip.getVisual());
		} else { // no more ships, turn is over
			endTurn();
		}
		return;

	}

	public void unselectShip() {
		selectedShip = null;
		selectedShipView.setShip(null);

		if(!isAITurn())
			enterDefaultState();
	}

	public void moveShipTo(Ship ship, int mapX, int mapY) {
		enterAnimatingState();

		camera.setFollowTarget(ship.getVisual());
		
		// update map
		Tile oldTile = map.getTile(ship.getLocation());
		Tile newTile = map.getTile(mapX, mapY);

		// move the ship and pass in the observer
		ship.moveWithDirections(enterDefaultStateObserver, mapX, mapY,
				map.shortestPath(ship.getLocation(), new Point(mapX,
						mapY), ship.getMovesLeft()));

		oldTile.setEmpty();
		newTile.setHasShip(true, ship);
		
//		printAllUnits();
	}

	public void attackShip(Ship attacker, Ship defender) {
		enterAnimatingState();

		camera.setFollowTarget(attacker.getVisual());
		
		attacker.attack(defender);
		
		checkForDestroyedUnits();
//		printAllUnits();

		// play animation 
		enterDefaultState();
	}

	public void waitShip(Ship ship){
		enterAnimatingState();

		ship.setIsWaiting(true);
		if(!isAITurn())
			unselectShip();
		
		int waitTime = 0;
		if(isAITurn()){
			waitTime = 20;
		}
		
		TimerAction timer = new TimerAction(waitTime * Game.FPSMUL, new Observer(){
			public void notified(Observable sender){
				enterDefaultState();
				if(!isAITurn())
					selectPlayerNextShip();
			}
		});
		addChild(timer);
		timer.start();
		
	}
	
	public void removeShipFromMap(Ship ship){
		map.getTile(ship.getLocation()).setEmpty();
		ship.destroy();
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
	 * 
	 * 
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
					unselectShip();
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
		if (tile != null && tile.getHasShip() == true) {
			Ship ship = tile.getShip();
			if (ship != selectedShip) {
				hoveredShipView.setShip(ship);
				Point p = camera.convertFromCameraSpace(Map
						.mapToPixelCoords(new Point(x + 1, y)));
				hoveredShipView.setLocation((int) p.getX(), (int) p.getY());
			}
		}

	}


	
	
	
	/*
	 * 
	 * Getters / Setters
	 * 
	 */
	
	public Camera getCamera() {
		return camera;
	}

	public Map getMap() {
		return map;
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
	

}
