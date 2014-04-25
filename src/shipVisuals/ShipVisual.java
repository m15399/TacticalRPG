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
import utils.Observer;
import utils.Position;
import view.ShipOutline;

/*
 * Controls the appearance of ships, 
 * extended by specific visuals for ships like ScoutVisual, FighterVisual, etc
 */
public class ShipVisual extends Entity {
	
	private Ship ship;
	private ShipOutline outline;
	
	public ShipVisual(Ship ship){
		this.ship = ship;
		
		outline = new ShipOutline();
		outline.getPosition().setParent(getPosition());
		addChild(outline);
		
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
	
	public ShipOutline getOutline(){
		return outline;
	}
	
	/*
	 * Create a queue of Actions that move the ship to the required location
	 */
	public void moveWithDirections(Observer notifyWhenDone, List<Direction> directions){
		ActionQueue q = new ActionQueue(notifyWhenDone);
		
		int distance = Map.TILESIZE;
		int time = 12 * Game.FPSMUL;
		
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
	
	public void draw(Graphics g1){

		// health bar
		Graphics2D g = (Graphics2D) g1;
		g.setStroke(new BasicStroke(0));
		
		// size
		int width = Map.TILESIZE-6;
		int height = 3;
		
		// position
		int ox = (int)(getPosition().getX());
		int oy = (int)(getPosition().getY() + Map.TILESIZE/2 - height + 3);
		
		// border
		g.setColor(Color.white);
		g.drawRect(ox -width/2-1, oy -height/2-1, width+2, height+2);
		
		// color
		Color red = new Color(1.0f, .25f, .15f);
		Color green = new Color(0f, .8f, .0f);
		if(ship.getTeam() == 0){
			g.setColor(green);			
		} else {
			g.setColor(red);
		}
		
		g.fillRect(ox -width/2, oy -height/2, (int)(width * ship.getHull() / ship.getMaxHull())+1, height+1);
	}
	
	
}
