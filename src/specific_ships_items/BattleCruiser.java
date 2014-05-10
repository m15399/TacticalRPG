package specific_ships_items;

import java.awt.Point;
import model.Ship;
import shipVisuals.BomberVisual;

public class BattleCruiser extends Ship {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7719840939455565674L;

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

	public BattleCruiser(Point newLocation) {
		super(newLocation);
		String description = "Battle Cruiser,\nMade for battle\ncruisin' to victory.\nCan periodically\nspawn a fighter.";
		this.constructorAid("BattleCruiser", 2, 150, 30, 150, 30, 75,
				description, 50, 55, 25, 2, true, 0);

		setVisual(new BomberVisual(this));

		// ability - deal true damage to target mothership
		setAbility(new PlaceFighterAbility());
	}

	/*
	 * Ship basic attack. Ignore scout armor.
	 */

	@Override
	public void attack(Ship target) {
		if (this.isHit()) {
			double damage = this.getDamage();
			if (!target.getName().equals("Scout")) {
				if (target.getHull() - target.getFinalDamage(damage) > 0)
					target.setHull(target.getHull()
							- target.getFinalDamage(damage));
				else
					target.setHull(0);
			} else {
				if (target.getHull() - damage > 0)
					target.setHull(target.getHull() - damage);
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
