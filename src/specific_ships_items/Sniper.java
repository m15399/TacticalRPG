package specific_ships_items;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import shipVisuals.BomberVisual;

import model.Item;
import model.Ship;

public class Sniper extends Ship {

	public Sniper(Point newLocation) {
		super(newLocation);
		String description = "Sniper ship,\nFires safely\nfrom the distance.";
		List<Item> items = new ArrayList<Item>();
		items.add(new SpaceMine());
		this.constructorAid("Sniper", 3, 75, 20, 75, 20, 85, items,
				description, 40, 45, 25, 5);

		 setVisual(new BomberVisual(this));

		// ability - charge next attack to pierce shielding

	}

	/*
	 * Ship basic attack. Ignore armor when ability is used.
	 */

	@Override
	public void attack(Ship target) {
		double damage = this.getDamage();
		if (this.getCanUseAbility() == true) {
			if (target.getHull() - target.getFinalDamage(damage) > 0)
				target.setHull(target.getHull() - target.getFinalDamage(damage));
			else
				target.setHull(0);
		} else {
			if (target.getHull() - damage > 0)
				target.setHull(target.getHull() - damage);
			else
				target.setHull(0);
		}
		this.setCanAttack(false);
	}



}
