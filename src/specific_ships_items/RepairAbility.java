package specific_ships_items;

import model.Ability;
import model.Ship;
import model.Tile;

public class RepairAbility extends Ability {

	private static final int HEAL_AMOUNT = 25;
	
	public RepairAbility() {
		super("Repair", "Repairs a ship's hull for " + HEAL_AMOUNT + " points", true, 2, 2);
		
	}

	public void useWithoutTarget() {}

	public void useOnShip(Ship ship) {
		ship.updateHull(HEAL_AMOUNT);
	}

	@Override
	public void useOnTile(Tile tile) {}

}
