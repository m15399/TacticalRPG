package view;

import java.awt.Color;
import java.awt.Graphics;

import model.Entity;
import model.Map;

public class ShipOutline extends Entity {

	public enum SelectionType {
		NONE, SELECTED, HOVER, ACTIONSLEFT;
	}
	
	SelectionType type;
	
	public ShipOutline(){
		type = SelectionType.NONE;
	}
	
	public void setSelectionType(SelectionType newType){
		type = newType;
	}
	
	public void draw(Graphics g){
		if(type == SelectionType.NONE)
			return;
		
		getPosition().transform(g);
				
		int width = Map.TILESIZE;
		int height = Map.TILESIZE;
		
		int border = 3;
		
		// border
		if(type == SelectionType.SELECTED){
			g.setColor(Color.green);
		} else if(type == SelectionType.ACTIONSLEFT) {
			g.setColor(Color.cyan);
		} else {
			g.setColor(Color.gray);
		}
		
		g.drawRect(-width/2+border, -height/2+border, width-border*2, height-border*2);
		
		getPosition().untransform(g);

	}
	
	
}
