package specific_ships_items;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import model.Item;
import model.Ship;
import shipVisuals.ScoutVisual;

public class RepairShip extends Ship {

	public RepairShip(Point newLocation) {
		super(newLocation);
		String description = "Repair ship,\nBattlefield support\ndefenseless.";
		List<Item> items = new ArrayList<Item>();
		items.add(new SpaceMine());
		this.constructorAid("RepairShip", 4, 75, 20, 75, 20, 100, items,
				description, 0, 0, 1, 1);

		setVisual(new ScoutVisual(this));
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

	/*
	 * Ship special ability
	 */

	public void special(Ship target) {
		// either let unit attack twice or disable target for a turn
		this.setCanUseAbility(false);
	}

	/*
	 * Ship moves
	 */

	public void move() {
		this.setCanMove(false);
	}

	/*
	 * Ship trades item
	 */

	public void trade(Ship ally, Item item) {
		// set gui screen for trading
		this.removeFromItems(item);
		ally.addToItems(item);
	}

	/*
	 * Ship uses item
	 */

	public void useItem(Ship target, Item item) {
		item.useOn(target);
		this.setCanUseItem(false);
	}

	/*
	 * Ship waits turn
	 */

	public void waitTurn() {
	}

}
