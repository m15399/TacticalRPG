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
import model.Map;
import model.Ship;

import utils.Direction;
import utils.Observable;
import utils.Observer;
import utils.Position;
import view.Camera;
import view.ShipOutline;

/*
 * Controls the appearance of ships, 
 * extended by specific visuals for ships like ScoutVisual, FighterVisual, etc
 */
public class ShipVisual extends Entity {

	private static double ZOOM_TARGET = 1.75;
	
	private Ship ship;
	private ShipOutline outline;
	private double displayHealth;
	private Camera currCamera;
	
	private Ship enemy; 
	private Observer notifyWhenAttacking;
	
	public ShipVisual(Ship ship) {
		this.ship = ship;
		
		updateDisplayHealth();
		
		outline = new ShipOutline();
		outline.getPosition().setParent(getPosition());
		addChild(outline);

		setPositionToShipCoords();
	}
	
	public void updateDisplayHealth(){
		displayHealth = ship.getHull();
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

	/*
	 * Create a queue of Actions that move the ship to the required location
	 */
	public void moveWithDirections(Observer notifyWhenDone,
			List<Direction> directions, Camera camera) {
		ActionQueue q = new ActionQueue(notifyWhenDone);

		q.addAction(new WaitForCameraAction(camera, null));

		q.addAction(new Action(null) {
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
		for (Direction d : directions) {

			switch (d) {
			case UP:
				currPosition.moveBy(0, -distance);
				break;
			case DOWN:
				currPosition.moveBy(0, distance);
				break;
			case LEFT:
				currPosition.moveBy(-distance, 0);
				break;
			case RIGHT:
				currPosition.moveBy(distance, 0);
				break;

			}
			q.addAction(new MoveEntityToAction(this,
					new Position(currPosition), time, null));
		}

		q.addAction(new Action(null) {
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
				playAttackAnimation();
			}
		}));
		
		// wait while attack animation plays
		q.addAction(new TimerAction(20*Game.FPSMUL, new Observer(){
			
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
		q.addAction(new WaitForCameraAction(camera, null));
		
		
		addChild(q);
		q.start();
		
		
		
		
		
	}

	public void explode(Observer notifyWhenDone) {

	}

	public void playMoveAnimation() {
		// override
	}
	
	public void playAttackAnimation(){
		// override
	}

	public void playIdleAnimation() {
		// override
	}

	public void draw(Graphics g1) {

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
