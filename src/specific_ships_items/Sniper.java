package specific_ships_items;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import model.Item;
import model.Ship;

public class Sniper extends Ship {

	public Sniper(Point newLocation) {
		super(newLocation);
		String description = "Sniper ship,\nFires safely\nfrom the distance.";
		List<Item> items = new ArrayList<Item>();
		items.add(new SpaceMine());
		this.constructorAid("Sniper", 3, 75, 20, 75, 20, 85, items,
				description, 40, 45, 25, 5);

		// setVisual(new BomberVisual(this));
	}

	/*
	 * Ship basic attack. Ignore armor when ability is used.
	 */

	@Override
	public void attack(Ship target) {
		double damage = this.getDamage();
		if (this.getCanUseAbility() == true) {
			if (target.getHull() - target.getFinalDamage(damage) > 0)
				target.setHull(target.getHull() - target.getFinalDamage(damage));
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

	/*
	 * Ship special ability
	 */

	public void special(Ship target) {
		// charge next attack to pierce shielding
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
