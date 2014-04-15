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
		this.setMoves(this.getMoves() - 1);
	}

	/*
	 * Ship special ability
	 */
	public void special(Ship target) {
		// possibly be untargetable for one turn
		target.setAccuracy(100);
		this.setCanUseAbility(false);
		this.setMoves(this.getMoves() - 1);
	}

	public void move() {
		this.setCanMove(false);
	}

	
	public void trade(Ship ally, Item item) {
		// set gui screen for trading
		this.removeFromItems(item);
		ally.addToItems(item);
	}
	
	
	public void useItem(Ship target, Item item) {
		item.useOn(target);
		this.setMoves(this.getMoves() - 1);
	}

	/*
	 * Ship waits turn
	 */
	public void waitTurn() {
		this.setMoves(this.getMoves() - 1);
	}

}
