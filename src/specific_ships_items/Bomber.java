package specific_ships_items;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import model.Item;
import model.Ship;
import shipVisuals.ScoutVisual;

public class Bomber extends Ship{

	public Bomber(Point newLocation) {
		super(newLocation);
		String description = "Bomber ship,\nstrong against\nnon-mobile targets.";
		List<Item> items = new ArrayList<Item>();
		items.add(new SpaceMine());
		this.constructorAid("Bomber", 3, 100, 30, 100, 30, 75, items, description, 40, 45, 15, 1);
		
		setVisual(new ScoutVisual(this));
	}
	
	/*
	 * Ship basic attack.
	 * Half damage against non-mothership
	 */
	
	@Override
	public void attack(Ship target) {
		double damage = this.getDamage();
		if (!target.getName().equals("Mothership")) {
			if (target.getHull() - target.getFinalDamage(damage/1.5) > 0)
				target.setHull(target.getHull() - target.getFinalDamage(damage/1.5));
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
