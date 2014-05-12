package shipVisuals;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import specific_ships_items.MineAbility;
import view.Sprite;
import model.Map;
import model.Ship;

public class MineVisual extends ShipVisual {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6053119457699202873L;

	private int count;
	private boolean on;
	private Sprite top;
	
	public MineVisual(Ship ship) {
		super(ship);
		
		count = 1;
		on = true;
		
		top = new Sprite();
		addChild(top);
		top.setSpatialParent(this);
		
		updateTopImage();
	}

	private void updateTopImage(){
		if(on)
			top.setImage("mine_top_on.png");
		else
			top.setImage("mine_top_off.png");
	}
	
	public void update(){
		count++;
		if(count % 30 == 0){
			on = !on;
			updateTopImage();
		}
	}
	
	public void draw(Graphics g){		
		getPosition().transform(g);
		((Graphics2D) g).setStroke(new BasicStroke(1.5f));
		float b = .7f;
		g.setColor(new Color(b,b,b, .2f));
		int s = Map.TILESIZE * MineAbility.RANGE + 5;
		g.drawArc(-s, -s, s*2, s*2, 0, 360);
		getPosition().untransform(g);
	}
}
