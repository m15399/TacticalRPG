package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import model.GameObject;
import model.Map;
import model.Ship;

public class SelectedShipBorder extends GameObject {

	public enum SelectionType {
		SELECTED, HOVER;
	}
	
	private Ship ship;
	SelectionType type;
	
	
	public SelectedShipBorder(){
		ship = null;
		type = SelectionType.SELECTED;
	}
	
	public void setSelectionType(SelectionType newType){
		type = newType;
	}
	
	public void setShip(Ship newShip){
		ship = newShip;
		
	}
	
	public void draw(Graphics g1){
		if(ship == null)
			return;
		
		Point p = Map.mapToPixelCoords(ship.getLocation());
		
		Graphics2D g = (Graphics2D) g1;
		g.setStroke(new BasicStroke(1));
				
		// size
		int width = Map.TILESIZE;
		int height = Map.TILESIZE;
		
		// position
		int border = 3;
		int ox = (int)(p.getX())+border;
		int oy = (int)(p.getY())+border;
		
		// border
		if(type == SelectionType.SELECTED){
			g.setColor(Color.green);
		} else {
			g.setColor(Color.gray);
		}
		g.drawRect(ox, oy, width-border*2, height-border*2);
	}
	
	
}
