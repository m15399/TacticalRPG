package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.List;

public class VictoryArea extends Entity{
	
	private static final long serialVersionUID = 8849323830448725521L;
	int leftX, upY, rightX, downY;
	
	public VictoryArea(int upperLeftBoundX, int upperLeftBoundY, int lowerRightBoundX, int lowerRightBoundY){
		leftX = upperLeftBoundX; 
		upY = upperLeftBoundY;
		rightX = lowerRightBoundX;
		downY = lowerRightBoundY;
	}
	
	public boolean checkIfAllShipsAreInVictoryArea(List<Ship> shipsToCheck){
		Point pt;
		for(Ship ship: shipsToCheck){
			pt = ship.getLocation();
			if(pt.x < leftX || pt.x > rightX || pt.y < upY || pt.y > downY){
				return false;
			}
		}
		return true;
	}
	
	public boolean checkIfAShipsIsInVictoryArea(List<Ship> shipsToCheck){
		Point pt;
		for(Ship ship: shipsToCheck){
			pt = ship.getLocation();
			if(pt.x >= leftX && pt.x <= rightX && pt.y >= upY && pt.y <= downY){
				return true;
			}
		}
		return false;
	}
	
	public void onDestroy() {
		// override
	}

	public void update() {
		// override
	}

	public void draw(Graphics g) {
		//TODO: Not quite sure how to get it to draw itself onto the map.
//		int width = Map.TILESIZE * (rightX - leftX);
//		int height = Map.TILESIZE * (upY - downY);
//		
//		getPosition().transform(g);
//		
//		int border = 3;
//		
//		// border
//		g.setColor(Color.BLUE);
//		
//		((Graphics2D) g).setStroke(new BasicStroke(1.3f));
//		g.drawRect(-width/2+border, -height/2+border, width-border*2, height-border*2);
//		getPosition().untransform(g);
	}
}
