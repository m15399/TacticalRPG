package shipVisuals;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.List;

import actions.*;

import model.Entity;
import model.Game;
import model.GameObject;
import model.Map;
import model.Ship;

import utils.Direction;
import utils.Observable;
import utils.Observer;
import utils.Position;
import view.Camera;
import view.Explosion;
import view.ShipOutline;
import view.Sprite;

/*
 * Controls the appearance of ships, 
 * extended by specific visuals for ships like ScoutVisual, FighterVisual, etc
 */
public class ShipVisual extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 621819537126071037L;
	private static final double ZOOM_TARGET = 1.75;
	private static final int FIRE_TIME = 20*Game.FPSMUL;

	
	private Ship ship;
	private ShipOutline outline;
	private double displayHealth;
	private Camera currCamera;
	
	private Sprite sprite;
	private GameObject belowSprite;
	
	private Ship enemy; 
	private transient Observer notifyWhenAttacking;
	
	public ShipVisual(Ship ship) {
		this.ship = ship;
		
		displayHealth = -1;
		updateDisplayHealth();
		
		outline = new ShipOutline();
		outline.getPosition().setParent(getPosition());
		addChild(outline);

		setPositionToShipCoords();
		
		belowSprite = new GameObject();
		addChild(belowSprite);
		
		sprite = new Sprite();
		sprite.setSpatialParent(this);
		addChild(sprite);
		
		refreshSprite();
	}
	
	public void refreshSprite(){
		sprite.setImage(ship.getFileName());
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public void addChildBelowSprite(GameObject o){
		belowSprite.addChild(o);
	}
		
	public void updateDisplayHealth(){
		double newHealth = ship.getHull();
		
		if(newHealth < displayHealth){
			addChild(new Explosion(1.0, 5, this));
		}
		
		displayHealth = newHealth;
	}

	/*
	 * Place the visual on the tile the ship is on
	 */
	public void setPositionToShipCoords() {
		Point coords = Map.mapToPixelCoords(ship.getLocation());
		getPosition().setX(coords.getX() + Map.TILESIZE / 2);
		getPosition().setY(coords.getY() + Map.TILESIZE / 2);
	}

	public ShipOutline getOutline() {
		return outline;
	}

	private class ShipFlyingThroughTileObserver implements Observer {
		
		private Ship ship;
		private int mapX, mapY;
		
		public ShipFlyingThroughTileObserver(Ship ship, int mapX, int mapY){
			this.ship = ship;
			this.mapX = mapX;
			this.mapY = mapY;
		}

		public void notified(Observable sender) {
			ship.getLevel().shipFlyingThroughTile(ship, mapX, mapY);
			updateDisplayHealth();
		}
	}
	
	/*
	 * Create a queue of Actions that move the ship to the required location
	 */
	public void moveWithDirections(Point originalPoint, Observer notifyWhenDone,
			List<Direction> directions, Camera camera) {
		ActionQueue q = new ActionQueue(notifyWhenDone);

		q.addAction(new WaitForCameraAction(camera, null));

		q.addAction(new Action(null) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 3686690698534528856L;

			public void update() {
				if (getStarted()) {
					playMoveAnimation();
					finish();
				}
			}
		});

		int distance = Map.TILESIZE;
		int time = 12 * Game.FPSMUL;

		Position currPosition = new Position(getPosition().getX(),
				getPosition().getY());
		int currMapX = originalPoint.x;
		int currMapY = originalPoint.y;

		for (Direction d : directions) {

			switch (d) {
			case UP:
				currPosition.moveBy(0, -distance);
				currMapY--;
				break;
			case DOWN:
				currPosition.moveBy(0, distance);
				currMapY++;
				break;
			case LEFT:
				currPosition.moveBy(-distance, 0);
				currMapX--;
				break;
			case RIGHT:
				currPosition.moveBy(distance, 0);
				currMapX++;
				break;

			}
			q.addAction(new MoveEntityToAction(this,
					new Position(currPosition), time, new ShipFlyingThroughTileObserver(ship, currMapX, currMapY)));
		}

		q.addAction(new Action(null) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 7122410263172743338L;

			public void update() {
				if (getStarted()) {
					playIdleAnimation();
					finish();
				}
			}
		});

		addChild(q);
		q.start();
	}

	public void attack(Observer notifyWhenDone, Observer newNotifyWhenAttacking, Ship defender,
			int damage, boolean didCrit, boolean didMiss, Camera camera) {
		// zoom
		// play attack animation
		// fly to other ship
		// play hit animation and notify
		// return to first ship
		// unzoom
		
		// set ship facing its target
		int shipX = ship.getLocation().x;
		int enemyX = defender.getLocation().x;
		if(enemyX < shipX){
			getPosition().setMirrored(true);
		} else if(enemyX > shipX){
			getPosition().setMirrored(false);
		}
		
		currCamera = camera;
		enemy = defender;
		notifyWhenAttacking = newNotifyWhenAttacking;
		
		ActionQueue q = new ActionQueue(notifyWhenDone);

		// wait for camera before doing anything
		q.addAction(new WaitForCameraAction(camera, new Observer(){
			
			// zoom in
			public void notified(Observable sender){
				currCamera.setZoomTarget(ZOOM_TARGET);
			}
		}));
		
		// wait for camera again
		q.addAction(new WaitForCameraAction(camera, new Observer(){
			
			// when that's done start attack animation
			public void notified(Observable sender){
				playAttackAnimation(FIRE_TIME);
			}
		}));
		
		// wait while attack animation plays
		q.addAction(new TimerAction(FIRE_TIME, new Observer(){
			
			// then stop attacking 
			public void notified(Observable sender){
				playIdleAnimation();
			}
		}));
		
		// wait another bit
		q.addAction(new TimerAction(15*Game.FPSMUL, new Observer(){
			
			// then  fly to the other ship
			public void notified(Observable sender){
				currCamera.setFollowTarget(enemy.getVisual());
			}
		}));
		
		// wait for camera
		q.addAction(new WaitForCameraAction(camera, new Observer(){
			
			// update the enemy's health and notify level the actual attack is happening
			public void notified(Observable sender){
				enemy.getVisual().updateDisplayHealth();
				notifyWhenAttacking.notified(null);
			}
		}));
		
		// wait while we watch the destruction
		q.addAction(new TimerAction(25*Game.FPSMUL, new Observer(){
			
			// return camera to first ship
			public void notified(Observable sender){
				currCamera.setFollowTarget(ShipVisual.this);
			}
		}));
		
		// wait for camera
		q.addAction(new WaitForCameraAction(camera, new Observer(){
			
			// zoom out
			public void notified(Observable sender){
				currCamera.setZoomTarget(1.0);
			}
		}));
		
		// wait for camera again
		q.addAction(new TimerAction(10,null));
		
		
		addChild(q);
		q.start();
		
		
		
		
		
	}
	
	public void cast(Observer notifyWhenDone, Observer notifyWhenCasting, Camera camera){
		
		ActionQueue q = new ActionQueue(notifyWhenDone);
		
		q.addAction(new WaitForCameraAction(camera, notifyWhenCasting));
		
		q.addAction(new TimerAction(60, null));
		
		
		addChild(q);
		q.start();
		
		
	}
	

	public void playMoveAnimation() {
		// override
	}
	
	public void playAttackAnimation(int time){
		// override
	}

	public void playIdleAnimation() {
		// override
	}
	
	public void draw(Graphics g1) {

		if(ship.getIsTargetable()){
			// health bar
			Graphics2D g = (Graphics2D) g1;
			g.setStroke(new BasicStroke(0));

			// size
			int width = Map.TILESIZE - 6;
			int height = 3;

			// position
			int ox = (int) (getPosition().getX());
			int oy = (int) (getPosition().getY() + Map.TILESIZE / 2 - height + 3);

			// border
			g.setColor(Color.white);
			g.drawRect(ox - width / 2 - 1, oy - height / 2 - 1, width + 2,
					height + 2);

			// color
			Color red = new Color(1.0f, .25f, .15f);
			Color green = new Color(0f, .8f, .0f);
			if (ship.getTeam() == 0) {
				g.setColor(green);
			} else {
				g.setColor(red);
			}

			g.fillRect(ox - width / 2, oy - height / 2,
					(int) (width * displayHealth / ship.getMaxHull()) + 1,
					height + 1);
		}
		
	}

	public Ship getShip(){
		return ship;
	}
	
}
