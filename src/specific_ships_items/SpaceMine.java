package specific_ships_items;

import model.Item;
import model.Ship;

public class SpaceMine extends Item{
	private int damage;
	public SpaceMine() {
		this.constructorAid("Space Mine", "The ship drops a space mine in a specific location that will be detonated by any enemy ship that passes through.");
		damage = 100;
	}

	@Override
	public void useOn(Ship ship) {
		ship.updateHull(-damage);
	}
	
}
