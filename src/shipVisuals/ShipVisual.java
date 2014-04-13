package shipVisuals;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

import actions.*;

import model.Entity;
import model.Map;
import model.Ship;

import utils.Direction;
import utils.Observer;
import utils.Position;

/*
 * Controls the appearance of ships, 
 * extended by specific visuals for ships like ScoutVisual, FighterVisual, etc
 */
public class ShipVisual extends Entity {
	
	private Ship ship;
	
	public ShipVisual(Ship ship){
		this.ship = ship;
		setPositionToShipCoords();
	}
	
	/*
	 * Place the visual on the tile the ship is on
	 */
	private void setPositionToShipCoords(){
		Point coords = Map.mapToPixelCoords(ship.getLocation());
		getPosition().setX(coords.getX() + Map.TILESIZE/2);
		getPosition().setY(coords.getY() + Map.TILESIZE/2);
	}
	
	/*
	 * Create a queue of Actions that move the ship to the required location
	 */
	public void moveWithDirections(Observer notifyWhenDone, List<Direction> directions){
		ActionQueue q = new ActionQueue(notifyWhenDone);
		
		int distance = Map.TILESIZE;
		int time = 12;
		
		Position currPosition = new Position(getPosition().getX(), getPosition().getY());
		for(Direction d : directions){
			
			switch(d){
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
			q.addAction(new MoveEntityToAction(this, new Position(currPosition), time, null));
		}
		
		addChild(q);
		q.start();
	}
	
	public void attack(Observer notifyWhenDone, int enemyX, int enemyY, int damage, boolean didCrit){
		
	}
	
	public void explode(Observer notifyWhenDone){
		
	}
	
	
	public void playMoveAnimation(){
		// override
	}
	public void playIdleAnimation(){
		// override
	}
	
	public void draw(Graphics g){
		
		// health bar
		int width = Map.TILESIZE-6;
		int height = 4;
		
		int ox = (int)(getPosition().getX());
		int oy = (int)(getPosition().getY() + Map.TILESIZE/2 - height - 1);
		
		g.setColor(new Color(0f, .8f, .2f));
		g.fillRect(ox -width/2, oy -height/2, width, height);
	}
	
	
}
