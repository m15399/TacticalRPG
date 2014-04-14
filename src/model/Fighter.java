package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import shipVisuals.ShipVisual;

public class Fighter extends Ship{

	public Fighter(Point newLocation) {
		super(newLocation);
		String description = "Anti-air fighter,\ncapable of long\nrange attacks.";
		List<Item> items = new ArrayList<Item>();
		items.add(new SpaceMine());
		this.constructorAid("Fighter", 3, 75, 25, 75, 25, 80, items, description, 40, 50, 25, 2);
		
		setVisual(new ShipVisual(this));
	}
}
