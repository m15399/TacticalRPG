package specific_ships_items;

import java.awt.Point;
import model.Ship;
import shipVisuals.ScoutVisual;

public class RepairShip extends Ship {

	public RepairShip(Point newLocation) {
		super(newLocation);
		String description = "Repair ship,\nBattlefield support\ndefenseless.";

		this.constructorAid("RepairShip", 4, 75, 20, 75, 20, 100,
				description, 0, 0, 1, 1, true, 0);
		addToItems(new SpaceMine());

		setVisual(new ScoutVisual(this));
		
		// ability - either let unit attack twice or disable target for a turn
		// i thought its ability was to heal though?
		setAbility(new RepairAbility());
	}

	/*
	 * Ship basic attack. Heal target unit.
	 */

	@Override
	public void attack(Ship target) {
		if(target.getHull() + target.getMaxHull() * (1/3) > target.getMaxHull()) {
			target.setHull(target.getMaxHull());
		}
		else {
			target.setHull(target.getHull() + target.getMaxHull() * (1/3));
		}
		this.setCanAttack(false);
	}

}
