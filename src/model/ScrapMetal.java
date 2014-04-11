package model;

import model.Item;

public class ScrapMetal extends Item {
	
	private int hpBoost;
	
	public ScrapMetal() {

		hpBoost = 100;
	}

	@Override
	public void useOn(Ship ship) {
	ship.setHealth(ship.getHealth()+hpBoost);
		
	}


}
