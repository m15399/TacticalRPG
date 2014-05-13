package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
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
		
		getPosition().setLocation(leftX * Map.TILESIZE, upY * Map.TILESIZE);
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
		
		int width = Map.TILESIZE * (rightX - leftX);
		int height = Map.TILESIZE * (downY - upY);
		
		getPosition().transform(g);
		
		int border = 4;
		
		// border
		g.setColor(new Color(.3f, 1f, .3f, .3f));
		
		
		((Graphics2D) g).setStroke(new BasicStroke(1.3f));
		g.fillRect(border, border, width-border*2, height-border*2);
		
		int oy = -4;
		
		g.setFont(new Font("Arial", Font.PLAIN, 14));
		g.setColor(Color.white);
		g.drawString("Move all your ships to this", 140, height/2 + oy);
		g.drawString("area to win the mission", 148, height/2 + 20 + oy);
		
		getPosition().untransform(g);
	}
}
