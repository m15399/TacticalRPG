package specific_ships_items;

import java.awt.Point;

import shipVisuals.SniperVisual;
import model.Ship;

public class Sniper extends Ship {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3978318903134958929L;
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
		this.constructorAid("Sniper", 3, 65, 20, 65, 20, 85, description, 30,
				35, 15, 6, true, 0);

		setVisual(new SniperVisual(this));

		charged = false;

		// ability - charge next attack to pierce shielding
		setAbility(new SniperChargeAbility());
	}

	public boolean getCharged() {
		return charged;
	}

	public void setCharged(boolean charged) {
		
		if(charged != this.charged){
			SniperVisual visual = (SniperVisual)getVisual();
			if(charged)
				visual.chargeRings();
			else
				visual.dimRings();
			
			this.charged = charged;
			this.setCanUseAbility(charged);
		}
		

	}

	/*
	 * Ship basic attack. Ignore armor when ability is used.
	 */

	@Override
	public void attack(Ship target) {
		
		SniperVisual visual = (SniperVisual) getVisual();
		visual.setTarget(target.getVisual().getPosition());
		
		if (this.isHit()) {
			double damage = this.getDamage();
			if (charged) {
				damage *= 1.5;
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
		this.setCanMove(false);
		this.setCanUseAbility(false);
		this.setCanUseItem(false);
		this.setCanAttack(false);
		
		if(charged){
			setCharged(false);
		}
	}

}
