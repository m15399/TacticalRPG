package specific_ships_items;

import model.Item;
import model.Ship;
import model.Tile;

public class SpaceMine extends Item{
	private int damage;
	
	public SpaceMine() {
		super("Space Mine", "The ship drops a space mine in a specific location that will be detonated by any enemy ship that passes through.", true, 1);
		damage = 100;
	}

	@Override
	public void useOnShip(Ship ship) {
		ship.updateHull(-damage);
	}

	@Override
	public void useWithoutTarget() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void useOnTile(Tile tile) {
		// TODO Auto-generated method stub
		
	}
	
}
