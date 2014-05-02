package specific_ships_items;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import model.Item;
import model.Ship;
import shipVisuals.ShipVisual;

public class Mothership extends Ship{

	public Mothership(Point newLocation) {
		super(newLocation);
		String description = "Grand mothership,\nstationary and capable\nof unit production.";
		List<Item> items = new ArrayList<Item>();
		items.add(new SpaceMine());
		this.constructorAid("Mothership", 0, 500, 50, 500, 50, 100, items, description, 0, 0, 0, 1);
		
		setVisual(new ShipVisual(this));
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
		// possibly create scout or other unit
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
		item.useOnShip(target);
	}

	/*
	 * Ship waits turn
	 */
	public void waitTurn() {
	}

}
