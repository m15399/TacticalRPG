package specific_ships_items;

import model.Item;
import model.Ship;
import model.Tile;

public class MagneticShield extends Item{
	private double shieldingBoost ;
	
	public MagneticShield() {
		super("Magnetic Shield", "Gives the ship a shield boost of 15%", false, 0);
		shieldingBoost = 1.15;
	}

	public void useOnShip(Ship ship) {}

	public void useWithoutTarget() {
		getOwner().updateShielding(getOwner().getShielding()*shieldingBoost);

	}

	public void useOnTile(Tile tile) {}


}
