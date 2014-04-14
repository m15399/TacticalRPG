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
	 * Ship basic attack
	 */
	public void attack(Ship target) {
		//ship.setEnergy(ship.getEnergy() - 150);
		if (target.getMaxHull() - target.getHull() >= 150)
			target.setHull(target.getHull() + 150);
		else
			target.setHull(target.getMaxHull());
		this.setMoves(this.getMoves() - 1);
	}

	/*
	 * Ship special ability
	 */
	public void special(Ship target) {
		//ship.setEnergy(ship.getEnergy() - 200);
		// possibly be untargetable for one turn
		target.setMoves(target.getMoves() + 1);
		this.setMoves(this.getMoves() - 1);
	}

	public void move() {
		//this.setCanMove(false);
	}

	
	public void trade(Ship ally) {
		// set gui screen for trading

	}
	
	
	public void useItem() {
		
	}

	/*
	 * Ship waits turn
	 */
	public void waitTurn() {
		this.setMoves(this.getMoves() - 1);
	}

}
