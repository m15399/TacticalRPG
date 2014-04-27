package specific_ships_items;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import model.Item;
import model.Ship;
import shipVisuals.FighterVisual;

public class Fighter extends Ship {

	public Fighter(Point newLocation) {
		super(newLocation);
		String description = "Anti-air fighter,\ncapable of long\nrange attacks.";
		List<Item> items = new ArrayList<Item>();
		items.add(new SpaceMine());
		this.constructorAid("Fighter", 3, 75, 25, 75, 25, 80, items,
				description, 40, 50, 25, 2);

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

	/*
	 * Ship special ability
	 */
	
	public void special(Ship target) {
		// able to move twice
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
		this.setCanAttack(false);
		this.setCanUseAbility(false);
		this.setCanUseItem(false);
		item.useOn(target);
	}

	/*
	 * Ship waits turn
	 */
	public void waitTurn() {
	}

}