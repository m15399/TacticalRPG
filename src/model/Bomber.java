package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

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
	
	
}
