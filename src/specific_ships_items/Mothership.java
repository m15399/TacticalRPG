package specific_ships_items;

import java.awt.Point;
import model.Ship;
import shipVisuals.ShipVisual;

public class Mothership extends Ship{

	/**
	 * Reminder on what things constructorAid sets to help in balancing
	 * 
	 * @param name
	 * @param moves - meant as max movement range
	 * @param hull
	 * @param shielding
	 * @param maxHull
	 * @param maxShielding
	 * @param accuracy
	 * @param description
	 * @param minDamage
	 * @param maxDamage
	 * @param critChance
	 * @param range - meant as ship's attack range
	 * @param isTargetable
	 * @param team
	 */		
	
	public Mothership(Point newLocation) {
		super(newLocation);
		String description = "Grand mothership,\nstationary and capable\nof unit production.";
		this.constructorAid("Mothership", 0, 500, 50, 500, 50, 100, description, 0, 0, 0, 1, true, 0);
		addToItems(new SpaceMine());
		
		setVisual(new ShipVisual(this));
		
		// ability - possibly create scout or other unit

	}
	
	/*
	 * Ship basic attack.
	 */
	@Override
	public void attack(Ship target) {
		double damage = this.getDamage();
		if (target.getHull() - target.getFinalDamage(damage) > 0)
			target.setHull(target.getHull() - target.getFinalDamage(damage));
		else
			target.setHull(0);
		this.setCanAttack(false);
	}



}
