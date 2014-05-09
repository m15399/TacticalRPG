package specific_ships_items;

import java.awt.Point;

import model.Ship;
import shipVisuals.BomberVisual;

public class Bomber extends Ship {

	/**
	 * Reminder on what things constructorAid sets to help in balancing
	 * 
	 * @param name
	 * @param moves
	 *            - meant as max movement range
	 * @param hull
	 * @param shielding
	 * @param maxHull
	 * @param maxShielding
	 * @param accuracy
	 * @param description
	 * @param minDamage
	 * @param maxDamage
	 * @param critChance
	 * @param range
	 *            - meant as ship's attack range
	 * @param isTargetable
	 * @param team
	 */

	public Bomber(Point newLocation) {
		super(newLocation);
		String description = "Bomber ship,\nstrong against\nnon-mobile targets.";
		this.constructorAid("Bomber", 4, 100, 30, 100, 30, 75, description, 50,
				55, 15, 1, true, 0);
		addToItems(new SpaceMine());

		setVisual(new BomberVisual(this));

		// ability - deal true damage to target mothership

	}

	/*
	 * Ship basic attack. Half damage against non-mothership
	 */

	@Override
	public void attack(Ship target) {
		if (this.isHit()) {
			double damage = this.getDamage();
			if (!target.getName().equals("Mothership")) {
				if (target.getHull() - target.getFinalDamage(damage / 1.5) > 0)
					target.setHull(target.getHull()
							- target.getFinalDamage(damage / 1.5));
				else
					target.setHull(0);
			} else {
				if (target.getHull() - target.getFinalDamage(damage) > 0)
					target.setHull(target.getHull()
							- target.getFinalDamage(damage));
				else
					target.setHull(0);
			}
			setDidMiss(false);
		} else {
			setDidMiss(true);
		}
		this.setCanAttack(false);
	}

}
