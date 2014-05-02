package specific_ships_items;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import model.Item;
import model.Ship;
import shipVisuals.BomberVisual;

public class Bomber extends Ship {

	public Bomber(Point newLocation) {
		super(newLocation);
		String description = "Bomber ship,\nstrong against\nnon-mobile targets.";
		List<Item> items = new ArrayList<Item>();
		items.add(new SpaceMine());
		this.constructorAid("Bomber", 4, 100, 30, 100, 30, 75, items,
				description, 40, 45, 15, 1);

		setVisual(new BomberVisual(this));
		
		// ability - deal true damage to target mothership

	}

	/*
	 * Ship basic attack. Half damage against non-mothership
	 */

	@Override
	public void attack(Ship target) {
		double damage = this.getDamage();
		if (!target.getName().equals("Mothership")) {
			if (target.getHull() - target.getFinalDamage(damage / 1.5) > 0)
				target.setHull(target.getHull()
						- target.getFinalDamage(damage / 1.5));
			else
				target.setHull(0);
		} else {
			if (target.getHull() - target.getFinalDamage(damage) > 0)
				target.setHull(target.getHull() - target.getFinalDamage(damage));
			else
				target.setHull(0);
		}
		this.setCanAttack(false);
	}


}
