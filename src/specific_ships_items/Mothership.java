package specific_ships_items;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import model.Item;
import model.Ship;
import shipVisuals.ShipVisual;

public class Mothership extends Ship{

	public Mothership(Point newLocation) {
		super(newLocation);
		String description = "Grand mothership,\nstationary and capable\nof unit production.";
		List<Item> items = new ArrayList<Item>();
		items.add(new SpaceMine());
		this.constructorAid("Mothership", 0, 500, 50, 500, 50, 100, items, description, 0, 0, 0, 1);
		
		setVisual(new ShipVisual(this));
		
		// ability - possibly create scout or other unit

	}
	
	/*
	 * Ship basic attack.
	 */
	@Override
	public void attack(Ship target) {
		double damage = this.getDamage();
		if (target.getHull() - target.getFinalDamage(damage) > 0)
			target.setHull(target.getHull() - target.getFinalDamage(damage));
		else
			target.setHull(0);
		this.setCanAttack(false);
	}



}
