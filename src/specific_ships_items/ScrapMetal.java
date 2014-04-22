package specific_ships_items;

import model.Item;
import model.Ship;

public class ScrapMetal extends Item {
	
	private double hullBoost;

	public ScrapMetal() {
		this.constructorAid("Scrap Metal", "Heals the ship's hull for 100 points");
		hullBoost = 100;
	}

	@Override
	public void useOn(Ship ship) {
	ship.updateHull(hullBoost);
		
	}



}
