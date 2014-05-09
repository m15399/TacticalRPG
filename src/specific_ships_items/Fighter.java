package specific_ships_items;

import java.awt.Point;

import model.Ship;
import shipVisuals.FighterVisual;

public class Fighter extends Ship {

	/**
	 * Reminder on what things constructorAid sets to help in balancing
	 * 
	 * @param name
	 *            Fighter
	 * @param moves
	 *            - meant as max movement range 5
	 * @param hull
	 *            55
	 * @param shielding
	 *            20
	 * @param maxHull
	 *            55
	 * @param maxShielding
	 *            25
	 * @param accuracy
	 *            80
	 * @param description
	 *            Anti-air fighter, capable of long range attacks
	 * @param minDamage
	 *            40
	 * @param maxDamage
	 *            50
	 * @param critChance
	 *            25
	 * @param range
	 *            - meant as ship's attack range 2
	 * @param isTargetable
	 *            true
	 * @param team
	 *            0
	 */

	public Fighter(Point newLocation) {
		super(newLocation);
		String description = "Anti-air fighter,\ncapable of long\nrange attacks.";
		this.constructorAid("Fighter", 5, 55, 20, 55, 25, 80, description, 40,
				50, 25, 2, true, 0);
		addToItems(new SpaceMine());

		setVisual(new FighterVisual(this));
	}

	/*
	 * Ship basic attack. 1.5 damage against bombers
	 */
	@Override
	public void attack(Ship target) {
		if (this.isHit()) {
			double damage = this.getDamage();
			if (target.getName().equals("Bomber")) {
				damage *= 1.5;
				if (target.getHull() - target.getFinalDamage(damage) > 0)
					target.setHull(target.getHull()
							- target.getFinalDamage(damage));
				else
					target.setHull(0);
			} else {
				if (target.getHull() - target.getFinalDamage(damage) > 0)
					target.setHull(target.getHull()
							- target.getFinalDamage(damage));
				else
					target.setHull(0);
			}
		} else {
			System.out.println("missed");
		}
		this.setCanAttack(false);
	}

}