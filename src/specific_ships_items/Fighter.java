package specific_ships_items;

import java.awt.Point;
import model.Ship;
import shipVisuals.FighterVisual;

public class Fighter extends Ship {

	public Fighter(Point newLocation) {
		super(newLocation);
		String description = "Anti-air fighter,\ncapable of long\nrange attacks.";
		this.constructorAid("Fighter", 5, 75, 25, 75, 25, 80,
				description, 40, 50, 25, 2, true, 0);
		addToItems(new SpaceMine());
		
		setVisual(new FighterVisual(this));
	}

	/*
	 * Ship basic attack.
	 * 1.5 damage against bombers
	 */
	@Override
	public void attack(Ship target) {
		double damage = this.getDamage();
		if (target.getName().equals("Bomber")) {
			damage *= 1.5;
			if (target.getHull() - target.getFinalDamage(damage) > 0)
				target.setHull(target.getHull() - target.getFinalDamage(damage));
			else
				target.setHull(0);
		} else {
			if (target.getHull() - target.getFinalDamage(damage) > 0)
				target.setHull(target.getHull() - target.getFinalDamage(damage));
			else
				target.setHull(0);
		}
		this.setCanAttack(false);
	}


}