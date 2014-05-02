package specific_ships_items;

import model.Item;
import model.Ship;
import model.Tile;

public class ScrapMetal extends Item {
	
	private double hullBoost;

	public ScrapMetal() {
		super("Scrap Metal", "Heals the ship's hull for 100 points", true, 2);
		hullBoost = 100;
	}

	@Override
	public void useOnShip(Ship ship) {
		ship.updateHull(hullBoost);
		
	}

	@Override
	public void useWithoutTarget() {
		
	}

	@Override
	public void useOnTile(Tile tile) {
		
	}



}
