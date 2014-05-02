package specific_ships_items;

import java.awt.Point;
import model.Ship;
import shipVisuals.ScoutVisual;

public class Scout extends Ship {

	public Scout(Point newLocation) {
		super(newLocation);
		String description = "Mobile scouting ship,\ngenerates and places\nmines.";

		this.constructorAid("Scout", 5, 50, 20, 50, 20, 60, description,
				30, 35, 15, 3);
		addToItems(new SpaceMine());

		setVisual(new ScoutVisual(this));
		
		// ability - possibly be untargetable for one turn / use mine
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
