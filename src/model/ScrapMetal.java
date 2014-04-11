package model;

import model.Item;

public class ScrapMetal extends Item {
	
	private double hullBoost;
	
	public ScrapMetal() {

		hullBoost = 100;
	}

	@Override
	public void useOn(Ship ship) {
	ship.updateHull(hullBoost);
		
	}


}
