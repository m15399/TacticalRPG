package specific_ships_items;

import java.awt.Point;

import shipVisuals.TurretVisual;
import model.Ship;

public class Turret extends Ship {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1860199265830333501L;


	public Turret(Point newLocation) {
		super(newLocation);
		String description = "Long range defenses\nof Mothership.";

		this.constructorAid("Turret", 0, 999, 999, 999, 999, 70, description, 40, 45, 10, 7, false, getTeam());

		setVisual(new TurretVisual(this));

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
		this.setCanMove(false);
		this.setCanUseAbility(false);
		this.setCanUseItem(false);
	}
	
}
