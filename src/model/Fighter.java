package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import shipVisuals.ScoutVisual;

public class Fighter extends Ship {

	public Fighter(Point newLocation) {
		super(newLocation);
		String description = "Anti-air fighter,\ncapable of long\nrange attacks.";
		List<Item> items = new ArrayList<Item>();
		items.add(new SpaceMine());
		this.constructorAid("Fighter", 3, 75, 25, 75, 25, 80, items,
				description, 40, 50, 25, 2);

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
			if (target.getHull() - target.getFinalDamage(damage) > 0)
				target.setHull(target.getHull() - target.getFinalDamage(damage));
			else
				target.setHull(0);
		} else {
			if (target.getHull() - target.getFinalDamage(damage/2) > 0)
				target.setHull(target.getHull() - target.getFinalDamage(damage/2));
			else
				target.setHull(0);
		}
		this.setMoves(this.getMoves() - 1);
	}

	/*
	 * Ship special ability
	 */
	public void special(Ship target) {
		// deal true damage to target mothership
		//target.setHull
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
