package specific_ships_items;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import model.Item;
import model.Ship;
import shipVisuals.BomberVisual;

public class BattleCruiser extends Ship {

	public BattleCruiser(Point newLocation) {
		super(newLocation);
		String description = "Battle Cruiser,\nMade for battle\ncruisin' to victory.";
		List<Item> items = new ArrayList<Item>();
		items.add(new SpaceMine());
		this.constructorAid("BattleCruiser", 3, 150, 30, 150, 30, 75, items,
				description, 50, 55, 25, 2);

		setVisual(new BomberVisual(this));
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

	/*
	 * Ship special ability
	 */

	public void special(Ship target) {
		// deal true damage to target mothership
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
