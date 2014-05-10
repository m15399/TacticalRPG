package specific_ships_items;

import java.awt.Point;

import shipVisuals.BomberVisual;
import model.Ship;

public class Sniper extends Ship {

	private boolean charged;

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

	public Sniper(Point newLocation) {
		super(newLocation);
		String description = "Sniper ship,\nFires safely\nfrom the distance.";
		this.constructorAid("Sniper", 3, 75, 20, 75, 20, 85, description, 40,
				45, 25, 5, true, 0);

		setVisual(new BomberVisual(this));

		charged = false;

		// ability - charge next attack to pierce shielding
		setAbility(new SniperChargeAbility());
	}

	public boolean getCharged() {
		return charged;
	}

	public void setCharged(boolean charged) {
		this.charged = charged;
		this.setCanUseAbility(charged);
	}

	/*
	 * Ship basic attack. Ignore armor when ability is used.
	 */

	@Override
	public void attack(Ship target) {
		if (this.isHit()) {
			double damage = this.getDamage();
			if (charged) {
				damage *= 1.5;
				charged = false;
			}

			if (this.getCanUseAbility() == true) {
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
