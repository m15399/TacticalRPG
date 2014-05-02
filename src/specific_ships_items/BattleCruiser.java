package specific_ships_items;

import java.awt.Point;
import model.Ship;
import shipVisuals.BomberVisual;

public class BattleCruiser extends Ship {

	public BattleCruiser(Point newLocation) {
		super(newLocation);
		String description = "Battle Cruiser,\nMade for battle\ncruisin' to victory.";
		this.constructorAid("BattleCruiser", 3, 150, 30, 150, 30, 75,
				description, 50, 55, 25, 2);
		addToItems(new SpaceMine());

		setVisual(new BomberVisual(this));
		
		// ability - deal true damage to target mothership
	}

	/*
	 * Ship basic attack. Ignore scout armor.
	 */

	@Override
	public void attack(Ship target) {
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
		this.setCanAttack(false);
	}


}
