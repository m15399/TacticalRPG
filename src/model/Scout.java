package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Scout extends Ship{

	public Scout(Point newLocation) {
		super(newLocation);
		String description = "Mobile scouting ship,\ngenerates and places\nmines.";
		List<Item> items = new ArrayList<Item>();
		items.add(new SpaceMine());
		this.constructorAid(7, 50, 20, 50, 20, 60, items, description, 30, 35, 15);
	}
}