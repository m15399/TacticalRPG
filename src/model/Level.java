package model;

import input.Button;
import input.Input;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import actions.TimerAction;
import model.Tile.Highlight;
import specific_ships_items.WarpGateShip;
import strategies.ImprovedStrategy;
import strategies.Strategy;
import terrains.Terrain;
import utils.Direction;
import utils.Observable;
import utils.Observer;
import view.Camera;
import view.Explosion;
import view.FoundItemsPopupScreen;
import view.MenuButton;
import view.SelectedEnemyShip;
import view.ShipOutline;
import view.EndOfLevelGraphic.WinnerType;
import view.ShipOutline.SelectionType;
import view.BouncingStat;
import view.EndOfLevelGraphic;
import view.PauseMenu;
import view.SelectedShipButtons;
import view.SelectedShipView;
import view.ShipSelectionScreen;
import view.Starfield;
import view.Tooltip;

/*
 * Root object for the main gameplay
 */
public class Level extends GameObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3739455927210352402L;

	/*
	 * Current state - attacking, moving, etc
	 */
	private enum TurnState {
		DEFAULT, ANIMATING, MOVING, ATTACKING, CASTING;
	}

	private TurnState state;

	private boolean isOver;
	
	private Map map;
	private Starfield starfield;
	private Camera camera;
	private LevelBackgroundButton levelButton;

	private GameObject terrainHolder;
	private GameObject shipHolder; // parent object for the ships
	private GameObject enemyShipHolder;
	

	private int currentTeam;
	private int numHumans;
	private int winner;

	private Tile tileHovered;

	private Ship selectedShip;
	private Castable currentCastable;
	private Ship currentShipTarget;
	private Tile currentTileTarget;

	// UI elements
	private SelectedShipView selectedShipView;
	private SelectedEnemyShip hoveredShipView;
	private SelectedShipButtons shipButtons;
	private ShipSelectionScreen shipSelectionScreen;
	private FoundItemsPopupScreen foundItemsScreen;
	private String fileName;

	private Strategy aiStrategy;

	private Ship shipWarpingIn;
	private WarpGateShip warper;
	
	private List<Button> debugButtons;
	private MenuButton menuButton;

	public Level(int width, int height) {

		map = new Map(width, height);
		
		fileName = "";

		init();
	}

	public Level(String filename) {
		
		BuildTileMapFromTextFile builtMap = new BuildTileMapFromTextFile(
				filename);

		Tile[][] tiles = builtMap.getTiles();
		fileName = filename;
		map = new Map(tiles);
		init();

		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				Tile t = map.getTile(x, y);
				if(t.getHasTerrain() && t.getTerrain() != null){
					addTerrainToMap(t.getTerrain());
				}
				if (t.getHasShip()) {

					Ship ship = t.getShip();
					if (ship instanceof WarpGateShip && ship.getTeam() == 0) {
						addShipToMap(ship);
					} else {
						addEnemyShipToMap(ship);
					}
				}
			}
		}

	}

	private void init() {
		isOver = false;
		
		selectedShip = null;
		tileHovered = null;
		currentTeam = 0;
		numHumans = 1;

		// Camera / Starfield
		camera = new Camera(map.getWidth(), map.getHeight());
		camera.setPosition(0, 0);

		starfield = new Starfield(Game.WIDTH, Game.HEIGHT, 7, 100, 22, 27, 300);
		starfield.setCamera(camera);
		addChild(starfield);

		addChild(camera);

		// Map
		camera.addChild(map);

		//Terrains Holder
		terrainHolder = new GameObject();
		camera.addChild(terrainHolder);
		
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

		// Ship Selection View
		shipSelectionScreen = new ShipSelectionScreen(this);
		addChild(shipSelectionScreen);
		
		//Found Items View
		foundItemsScreen = new FoundItemsPopupScreen();
		addChild(foundItemsScreen);

		// Background button for mouse input on the map
		levelButton = new LevelBackgroundButton(this);
		Input.getInstance().addButton(levelButton);

		// AI
		aiStrategy = new ImprovedStrategy();
		
		// menu button
		menuButton = new MenuButton(camera);
		addChild(menuButton);
		

		// test zoom buttons
		debugButtons = new ArrayList<Button>();
		if(Game.DEBUG){
			int bsize = 20;
			final double sfac = 2;
			
			Button b1 = new Button(0, 0, bsize, bsize) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 4749604349270799816L;

				public void mouseReleased() {
					camera.setZoomTarget(camera.getZoom() * sfac);
				}
			};
			Input.getInstance().addButton(b1);
			debugButtons.add(b1); 
			
			Button b2 = new Button(0, bsize, bsize, bsize) {
				/**
				 * 
				 */
				private static final long serialVersionUID = -4460365434247909711L;

				public void mouseReleased() {
					camera.setZoomTarget(camera.getZoom() / sfac);
				}
			};
			Input.getInstance().addButton(b2);
			debugButtons.add(b2); 
			
			Button b3 = new Button(Game.WIDTH - bsize, 0, bsize, bsize){
				/**
				 * 
				 */
				private static final long serialVersionUID = 6366047185169964945L;

				public void mouseReleased(){
					onTeamWin(0);
				}
			};
			Input.getInstance().addButton(b3);
			debugButtons.add(b3); 
			
			Button b4 = new Button(Game.WIDTH - bsize, bsize, bsize, bsize){
				/**
				 * 
				 */
				private static final long serialVersionUID = -4272063537944131233L;

				public void mouseReleased(){
					onTeamWin(1);
				}
			};
			Input.getInstance().addButton(b4);
			debugButtons.add(b4); 

			
		}

		map.checkTileLocations();

		enterDefaultState();
	}

	public void onDestroy() {
		Input.getInstance().removeButton(levelButton);
		for(Button b : debugButtons){
			Input.getInstance().removeButton(b);
		}
	}
	
	public void exitLevel(){
		isOver = true;
		
		TimerAction timer = new TimerAction(90, new Observer(){
			public void notified(Observable sender){
				Game.getInstance().transitionTo(getNextRoot());
			}
		});
		addChild(timer);
		timer.start();
	}

	public void updateButtons() {
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

		// if(isAITurn()){
		// if(state == TurnState.DEFAULT){
		// if(getNextShipWithMoves() == null)
		// endTurn();
		// else
		// takeNextAIMove();
		// }
		// }

		updateButtons();

		// figure out how to outline ships
		List<Ship> ships = getShips();
		for (Ship s : ships) {
			ShipOutline o = s.getVisual().getOutline();
			o.setSelectionType(SelectionType.NONE);
		}
		if (!isAITurn() && state != TurnState.ANIMATING) {
			ships = getShips(currentTeam);
			for (Ship s : ships) {
				ShipOutline o = s.getVisual().getOutline();
				if (s == selectedShip) {
					o.setSelectionType(SelectionType.SELECTED);
				} else if (!s.getIsWaiting()) {
					o.setSelectionType(SelectionType.ACTIONSLEFT);
				}
			}
		}

		
		
	}

	public int getWinner(){
		return winner;
	}
	
	public void onTeamWin(int team){
		
		winner = team;
		
		isOver = true;

		WinnerType wt = WinnerType.SINGLEPLAYER;
		
		if(numHumans == 2){
			if(team == 0)
				wt = WinnerType.PLAYER1;
			else
				wt = WinnerType.PLAYER2;
		} else {
			if(team == 0)
				wt = WinnerType.SINGLEPLAYER;
			else
				wt = WinnerType.ENEMY;
		}
		
		addChild(new EndOfLevelGraphic(wt));
		
		exitLevel();

	}
	
	public GameObject getNextRoot(){
		return new TitleMenu();
	}
	
	public void shipFlyingThroughTile(Ship ship, int mapX, int mapY){
		Tile tile = map.getTile(mapX, mapY);
		//System.out.println("# Tile Items: " + tile.getItems().size());
		if(tile.getItems().size() > 0){
			//System.out.println("Items before addition: " + ship.getItems().size());
			ArrayList<Item> tileItems = new ArrayList<Item>();
			for(Item i : tile.getItems()){
				tileItems.add(i);
			}
			for(Item i : tileItems){
				tile.removeFromItems(i);
				ship.addToItems(i);
			}
			
			foundItemsScreen.setVisible(true);
			//System.out.println("Items after addition: " + ship.getItems().size());
		}
		if(tile.getHasTerrain()){
			Terrain terrain = tile.getTerrain();
			terrain.applyEffect(ship);
//			System.out.println("flying through terrain: " + terrain.toString());
		}
	}
	
	/*
	 * 
	 * Turn logic
	 */

	public boolean isAITurn() {
		return currentTeam >= numHumans;
	}

	public void startTurn(int team) {
		currentTeam = team;
		List<Ship> ships = getShips(team);
		for (Ship s : ships) {
			s.startTurn();
		}

		enterDefaultState();

		if (!isAITurn()) {
			selectPlayerNextShip();
		}
		
		if(numHumans > 1){
			int time = 90;
			if(currentTeam == 0){
				addChild(new EndOfLevelGraphic(WinnerType.STARTBLUETURN, time));
			} else {
				addChild(new EndOfLevelGraphic(WinnerType.STARTREDTURN, time));

			}
		}
			

	}

	public void endTurn() {

		int nextTeam = currentTeam + 1;
		if (nextTeam > 1)
			nextTeam = 0;

		startTurn(nextTeam);
	}

	public void checkForDestroyedUnits() {
		for (Ship s : getShips()) {
			if (s.isShipDead()) {
				camera.addChild(new Explosion(2.0, 20, s.getVisual()));
				removeShipFromMap(s);
			}
		}
		if(!getIsOver()){
			// check objective
			if(getTargettableShips(1).size() == 0){
				onTeamWin(0);
			} else if(getTargettableShips(0).size() == 0){
				onTeamWin(1);
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

	public void printAllUnits() {
		System.out.println("\n\nSTATUS:\n");
		for (Ship s : getShips()) {
			System.out.println(s.toString());
		}
	}

	/*
	 * 
	 * AI Management
	 */

	public void takeNextAIMove() {
		for (Ship s : getShips()) {
			if (!s.getIsWaiting()) {
				selectShip(s);
				aiStrategy.doNextAction(s, this);

				if (state != TurnState.ANIMATING)
					enterDefaultState();

				return;
			}
		}
		endTurn();
	}

	public List<Ship> getPossibleTargetsForAI(Ship s) {

		List<Ship> targets = new ArrayList<Ship>();
		if (!s.getCanAttack())
			return targets;

		List<Tile> tiles = map.getTilesWithinRange(s.getLocation(),
				s.getRange(), false);

		List<Ship> enemies = getShips(0); // get player's ships

		for (Ship e : enemies) {
			if (e.getIsTargetable() && tiles.contains(map.getTile(e.getLocation()))) {
				targets.add(e);
			}
		}

		return targets;
	}

	public List<Point> getPossibleMovesForAI(Ship s) {

		List<Point> moves = new ArrayList<Point>();
		if (!s.getCanMove())
			return moves;

		List<Tile> tiles = map.getTilesWithinRange(s.getLocation(),
				s.getMovesLeft(), true);

		for (Tile t : tiles) {
			moves.add(t.getLocation());
		}

		return moves;
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
		if (!isAITurn()) {
			levelButton.enable();
			menuButton.enable();
			shipButtons.setShip(selectedShip);
		} else {
			hoveredShipView.setShip(null);
			shipButtons.setShip(null);
		}
		updateButtons();
		shipSelectionScreen.setVisible(false);

		currentCastable = null;

		if (isAITurn())
			takeNextAIMove();
		
		if (selectedShip != null) {

			if (selectedShip instanceof WarpGateShip) {
				shipSelectionScreen.setVisible(true);

			} else {
				if (selectedShip.getCanMove()) {
					shipButtons.addButton("Move", new Button() {
						/**
						 * 
						 */
						private static final long serialVersionUID = 995893818981725034L;

						public void mouseReleased() {
							enterMoveState();
						}
					});
				}
				if (selectedShip.getCanAttack()) {
					shipButtons.addButton("Attack", new Button() {
						/**
						 * 
						 */
						private static final long serialVersionUID = 385166472218886539L;

						public void mouseReleased() {
							enterAttackState();
						}
					});
				}
				if (selectedShip.getCanUseAbility()) {
					Ability ability = selectedShip.getAbility();
					if (ability != null) {
						String name = selectedShip.getAbility().getName();

						if (ability.getCooldownLeft() == 0) {
							shipButtons.addButton(name, new CastableButton(selectedShip.getAbility()));

						} else {
							shipButtons
									.addButton(
											name + " ("
													+ (ability.getCooldown() - ability.getCooldownLeft())
													+ "/"
													+ ability.getCooldown()
													+ ")", new Button());
						}

					}
				}
				if(selectedShip.getCanUseItem() && selectedShip.getItems().size() > 0){
					
					shipButtons.addButton("Items", new Button(){
						/**
						 * 
						 */
						private static final long serialVersionUID = -8756795294514804779L;

						public void mouseReleased(){
							
							shipButtons.setShip(selectedShip);
							
							for(Item i : selectedShip.getItems()){
								
								shipButtons.addButton(i.getName(), new CastableButton(i));
								
							}
							
							shipButtons.addButton("Cancel", new Button(){
								/**
								 * 
								 */
								private static final long serialVersionUID = 435054519683331258L;

								public void mouseReleased(){
									enterDefaultState();
								}
							});
							
						}
					});
					
					
				}
				shipButtons.addButton("Wait", new Button() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 432444498290154821L;

					public void mouseReleased() {
						waitShip(selectedShip);
					}
				});

			}

		}

		map.clearHighLights();
		state = TurnState.DEFAULT;

	}
	
	private class CastableButton extends Button{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1879859896822376397L;
		Tooltip tooltip;
		Castable castable;
		
		public CastableButton(Castable castable){
			tooltip = new Tooltip();
			tooltip.setText(castable.getDescription());
			addChild(tooltip);
			this.castable = castable;
		}
		
		public void mouseHovered(){
			tooltip.setVisible(true);
		}
		
		public void mouseExited(){
			tooltip.setVisible(false);
		}
		
		public void mouseReleased(){
			currentCastable = castable;
			enterCastingState();
		}
		
	}

	/*
	 * Animating state
	 */
	public void enterAnimatingState() {
		map.clearHighLights();
		levelButton.disable();
		menuButton.disable();
		hoveredShipView.setShip(null);
		shipSelectionScreen.setVisible(false);
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
			List<Tile> possibleMoves = map.getTilesWithinRange(
					selectedShip.getLocation(), selectedShip.getMovesLeft(),
					true);
			map.highlightTiles(possibleMoves, Tile.Highlight.BLUE);
		}

		// turn off buttons
		shipButtons.setShip(null);
		shipSelectionScreen.setVisible(false);

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
		if (selectedShip != null) {
			List<Tile> possibleMoves = map.getTilesWithinRange(
					selectedShip.getLocation(), selectedShip.getRange(), false);
			map.highlightTiles(possibleMoves, Tile.Highlight.RED);
		}

		// turn off buttons
		shipButtons.setShip(null);
		shipSelectionScreen.setVisible(false);

		state = TurnState.ATTACKING;
	}

	public void enterCastingState() {
		if (selectedShip == null) {
			System.out
					.println("Error: Entered casting state with no ship selected");
			System.exit(1);
		}

		// highlight attack range
		map.clearHighLights();
		
		if(currentCastable.getTargetType() == Castable.TargetType.NONE){
			useCastableWithoutTarget();
			
		} else {
			int castRange = currentCastable.getCastRange();
			List<Tile> possibleMoves = map.getTilesWithinRange(
					selectedShip.getLocation(), castRange, false);
			map.highlightTiles(possibleMoves, Tile.Highlight.GREEN);
		}

		// turn off buttons
		shipButtons.setShip(null);
		shipSelectionScreen.setVisible(false);

		state = TurnState.CASTING;
	}

	/*
	 * 
	 * Handling player actions
	 */

	public void selectShip(Ship ship) {
		selectedShip = ship;
		selectedShipView.setShip(ship);
		if (!isAITurn())
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

		if (!isAITurn())
			enterDefaultState();
	}

	public void moveShipTo(Ship ship, int mapX, int mapY) {
		enterAnimatingState();

		camera.setFollowTarget(ship.getVisual());

		// update map
		Tile oldTile = map.getTile(ship.getLocation());
		Tile newTile = map.getTile(mapX, mapY);

		// move the ship and pass in the observer
		ship.moveWithDirections(new Observer(){

			public void notified(Observable sender) {
				checkForDestroyedUnits();
				if(selectedShip.isShipDead()){
					unselectShip();
					selectPlayerNextShip();
				}
				enterDefaultState();
				
			}
			
		}, mapX, mapY,
				map.shortestPath(ship.getLocation(), new Point(mapX, mapY)),
				camera);

		oldTile.setHasShip(false, null);
		newTile.setHasShip(true, ship);

		// printAllUnits();
	}

	public void attackShip(Ship attacker, Ship defender) {
		enterAnimatingState();

		camera.setFollowTarget(attacker.getVisual());

		attacker.attack(defender);
		// printAllUnits();

		attacker.getVisual().attack(new Observer() {
			public void notified(Observable sender) {
				enterDefaultState();
			}
		}, new NotifyWhenAttackedObserver(attacker, defender), defender, 0, false, false, camera);
	}
	
	private class NotifyWhenAttackedObserver implements Observer{
		Ship attacker;
		Ship defender;
		
		public NotifyWhenAttackedObserver(Ship a, Ship d){
			attacker = a;
			defender = d;
		}
		
		public void notified(Observable sender) {
			checkForDestroyedUnits();
			if(attacker.getDidMiss()){
				camera.addChild(new BouncingStat("Missed!", defender.getVisual()));
			}
		}
		
	}

	public void waitShip(Ship ship) {
		enterAnimatingState();

		ship.setIsWaiting(true);
		if (!isAITurn())
			unselectShip();

		int waitTime = 0;
		if (isAITurn()) {
			waitTime = 20;
		}

		TimerAction timer = new TimerAction(waitTime * Game.FPSMUL,
				new Observer() {
					public void notified(Observable sender) {
						enterDefaultState();
						if (!isAITurn())
							selectPlayerNextShip();
					}
				});
		addChild(timer);
		timer.start();

	}

	public void warpInPlayerShip(Ship ship) {
		warper = (WarpGateShip) selectedShip;
		unselectShip();

		ship.setLocation(new Point(warper.getLocation()));
		ship.getVisual().setPositionToShipCoords();
		ship.setTeam(currentTeam);

		// ship.startTurn();

		shipWarpingIn = ship;

		enterAnimatingState();

		TimerAction timer = new TimerAction(30, new Observer() {
			public void notified(Observable sender) {
				removeShipFromMap(warper);
				if(shipWarpingIn.getTeam() == 0)
					addShipToMap(shipWarpingIn);
				else
					addEnemyShipToMap(shipWarpingIn);

				enterDefaultState();
				if (!isAITurn())
					selectPlayerNextShip();
			}
		});
		addChild(timer);
		timer.start();
	}
	
	public void useEnemyAbility(Ship ship){
		currentCastable = ship.getAbility();
		useCastableWithoutTarget();
	}

	private void useCastableStart() {
		enterAnimatingState();

		camera.setFollowTarget(selectedShip.getVisual());
	}

	private void useCastableEnd() {
		enterDefaultState();
		checkForDestroyedUnits();
		if(selectedShip.isShipDead()){
			unselectShip();
			selectPlayerNextShip();
		}
	}

	public void useCastableWithoutTarget() {
		useCastableStart();
		
		selectedShip.getVisual().cast(new Observer() {
			public void notified(Observable sender) {
				useCastableEnd();
			}
		}, new Observer(){
			public void notified(Observable sender){
				if(currentCastable instanceof Ability){
					selectedShip.useAbilityWithoutTarget(null);					
				} else {
					selectedShip.useItemWithoutTarget((Item)currentCastable, null);
				}
			}
		}, camera);
	}

	public void useCastableOnShip(Ship ship) {
		useCastableStart();
		
		currentShipTarget = ship;

		selectedShip.getVisual().cast(new Observer() {
			public void notified(Observable sender) {
				useCastableEnd();
			}
		}, new Observer(){
			public void notified(Observable sender){
				if(currentCastable instanceof Ability){
					selectedShip.useAbilityOnShip(currentShipTarget, null);					
				} else {
					selectedShip.useItemOnShip((Item)currentCastable,currentShipTarget, null);
				}
			}
		}, camera);
	}

	public void useCastableOnTile(Tile tile) {
		useCastableStart();

		currentTileTarget = tile;

		selectedShip.getVisual().cast(new Observer() {
			public void notified(Observable sender) {
				useCastableEnd();
			}
		}, new Observer(){
			public void notified(Observable sender){
				if(currentCastable instanceof Ability){
					selectedShip.useAbilityOnTile(currentTileTarget, null);					
				} else {
					selectedShip.useItemOnTile((Item)currentCastable,currentTileTarget, null);
				}
			}
		}, camera);
	}

	public void removeShipFromMap(Ship ship) {
		map.getTile(ship.getLocation()).setEmpty();
		ship.destroy();
	}
	
	private void addShipHelper(Ship ship){
		ship.setLevel(this);
		map.getTile(ship.getLocation()).setHasShip(true, ship);
		ship.setCanAttack(false);
		ship.setCanMove(false);
		ship.setCanUseAbility(false);
		ship.setCanUseItem(false);
		ship.setIsWaiting(true);

	}
	
	public void addShipToMap(Ship ship) {
		// ship.setTeam(0); // not sure if needed? 
		addShipHelper(ship);
		shipHolder.addChild(ship);
	}

	public void addEnemyShipToMap(Ship ship) {
		ship.setTeam(1);
		addShipHelper(ship);
		enemyShipHolder.addChild(ship);
	}
	
	public void addTerrainToMap(Terrain terrain){
		terrainHolder.addChild(terrain);
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

			if (state == TurnState.CASTING
					&& ship.getIsTargetable() && tile.getHighlight() != Tile.Highlight.NONE
					&& ((currentCastable.getTargetType() == Castable.TargetType.ALLY && ship
							.getTeam() == currentTeam) || (currentCastable
							.getTargetType() == Castable.TargetType.ENEMY && ship
							.getTeam() != currentTeam))) {

				useCastableOnShip(ship);

			} else if (ship.getTeam() == currentTeam) {
				if (ship == selectedShip) { // clicked on self
					enterDefaultState();
				} else {
					selectShip(ship);
				}
			} else {
				if (state == TurnState.ATTACKING
						&& ship.getIsTargetable() && tile.getHighlight() != Tile.Highlight.NONE) {
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
			} else if (state == TurnState.CASTING
					&& currentCastable.getTargetType() == Castable.TargetType.TILE
					&& !tile.getIsOccupied()) {
				useCastableOnTile(tile);
			} else if (state == TurnState.CASTING) {
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

		if (state == TurnState.MOVING) {
			map.colorGreenHighlightsToBlue();
			if (tile != null && !tile.getIsOccupied() && selectedShip != null
					&& !isAITurn()
					&& tileHovered.getHighlight() == Highlight.BLUE) {
				List<Direction> list = new ArrayList<Direction>();
				list = map.shortestPath(selectedShip.getLocation(),
						tileHovered.getLocation());
				map.highlightTilesWithoutClearingPrevious(
						convertDirectionsToPoints(list), Highlight.MOVECOLOR);
			}
		}

	}

	/*
	 * Used in highlighting possible prospective path to a location in green
	 * highlighting.
	 */

	private List<Tile> convertDirectionsToPoints(List<Direction> dirs) {
		List<Point> list = new ArrayList<Point>();
		Point curr = new Point(selectedShip.getLocation().x,
				selectedShip.getLocation().y);
		list.add(selectedShip.getLocation());
		for (Direction dir : dirs) {
			if (dir == Direction.UP) {
				curr = new Point(curr.x, curr.y - 1);
				list.add(curr);
			} else if (dir == Direction.DOWN) {
				curr = new Point(curr.x, curr.y + 1);
				list.add(curr);
			} else if (dir == Direction.LEFT) {
				curr = new Point(curr.x - 1, curr.y);
				list.add(curr);
			} else if (dir == Direction.RIGHT) {
				curr = new Point(curr.x + 1, curr.y);
				list.add(curr);
			} else {
				System.out.println("Something went wrong in the conversion.");
			}
		}
		List<Tile> tiles = new ArrayList<Tile>();
		for (Point pt : list) {
			tiles.add(map.getTile(pt));
		}
		return tiles;
	}

	/*
	 * 
	 * Getters / Setters
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

		for (GameObject o : ships) {
			Ship s = (Ship) o;
			list.add(s);
		}

		return list;
	}
	
	public List<Ship> getTargettableShips(int team){
		List<GameObject> ships;
		if (team == 0) {
			ships = shipHolder.getChildren();
		} else {
			ships = enemyShipHolder.getChildren();
		}

		List<Ship> list = new ArrayList<Ship>();

		for (GameObject o : ships) {
			Ship s = (Ship) o;
			if(s.getIsTargetable())
				list.add(s);
		}

		return list;
	}

	public List<Ship> getShips() {
		List<Ship> ships;
		List<Ship> list = new ArrayList<Ship>();

		for (int i = 0; i < 2; i++) {
			ships = getShips(i);

			for (Ship s : ships) {
				list.add(s);
			}
		}

		return list;
	}
	
	public List<Ship> getTargettableShipsWithinCircularArea(Ship src, int range, int team){
		List<GameObject> ships;
		if (team == 0) {
			ships = shipHolder.getChildren();
		} else {
			ships = enemyShipHolder.getChildren();
		}

		List<Ship> list = new ArrayList<Ship>();

		for (GameObject o : ships) {
			Ship s = (Ship) o;
			if(!s.getIsTargetable())
				continue;
			
			double dx = src.getLocation().x - s.getLocation().x;
			double dy = src.getLocation().y - s.getLocation().y;
			double distance = Math.sqrt(dx * dx + dy * dy);
			
			if(distance <= range)
				list.add(s);
		}

		return list;
	}
	
	public List<Tile> getEmptyTilesWithinCircularArea(Ship src, int range){
		List<Tile> tiles = new ArrayList<Tile>();
		
		Tile[][] mapTiles = map.getTiles();
		for(int i = 0; i < mapTiles.length; i++){
			for(int j = 0; j < mapTiles[0].length; j++){
				
				Tile ct = mapTiles[i][j];
				
				double dx = src.getLocation().x - i;
				double dy = src.getLocation().y - j;
				double distance = Math.sqrt(dx * dx + dy * dy);
				
				if(distance <= range && !ct.getIsOccupied()){
					tiles.add(ct);
				}
					
			}
		}
		
		return tiles;
	}

	public boolean getIsOver(){
		return isOver;
	}
	
	public void setToMultiplayer(){
		numHumans = 2;
	}
	
	public String getFileName(){
		return fileName;
	}
		
}
