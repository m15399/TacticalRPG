package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import shipVisuals.ScoutVisual;

public class Scout extends Ship {

	public Scout(Point newLocation) {
		super(newLocation);
		String description = "Mobile scouting ship,\ngenerates and places\nmines.";
		List<Item> items = new ArrayList<Item>();
		items.add(new SpaceMine());
		this.constructorAid("Scout", 5, 50, 20, 50, 20, 60, items, description,
				30, 35, 15, 3);

		setVisual(new ScoutVisual(this));
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

	/*
	 * Ship special ability
	 */
	
	public void special(Ship target) {
		// possibly be untargetable for one turn
		target.setAccuracy(100);
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
