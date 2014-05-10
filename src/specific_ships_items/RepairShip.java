package specific_ships_items;

import java.awt.Point;

import model.Ship;
import shipVisuals.ScoutVisual;

public class RepairShip extends Ship {

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
	
	public RepairShip(Point newLocation) {
		super(newLocation);
		String description = "Repair ship,\nSupport not designed\nfor heavy combat.";

		this.constructorAid("RepairShip", 4, 75, 20, 75, 20, 90,
				description, 25, 30, 10, 1, true, 0);

		setVisual(new ScoutVisual(this));
		
		// ability - either let unit attack twice or disable target for a turn
		// i thought its ability was to heal though?
		setAbility(new RepairAbility());
	}

	/*
	 * Ship basic attack. Heal target unit.
	 */

	@Override
	public void attack(Ship target) {
		if (this.isHit()) {
			double damage = this.getDamage();
			if (target.getHull() - target.getFinalDamage(damage) > 0)
				target.setHull(target.getHull() - target.getFinalDamage(damage));
			else
				target.setHull(0);
			setDidMiss(false);
		} else {
			setDidMiss(true);
		}
		this.setCanAttack(false);
	}
}
