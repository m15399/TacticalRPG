package shipVisuals;

import view.Sprite;
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
}
