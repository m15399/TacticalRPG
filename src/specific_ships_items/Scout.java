package specific_ships_items;

import java.awt.Point;

import model.Ship;
import shipVisuals.ScoutVisual;

public class Scout extends Ship {

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

	public Scout(Point newLocation) {
		super(newLocation);
		String description = "Mobile scouting ship,\ngenerates and places\nmines.";

		this.constructorAid("Scout", 5, 50, 20, 50, 20, 70, description, 30,
				35, 15, 3, true, 0);

		setVisual(new ScoutVisual(this));

		// ability - possibly be untargetable for one turn / use mine
		setAbility(new PlaceMineAbility());
	}

	/*
	 * Ship basic attack.
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
