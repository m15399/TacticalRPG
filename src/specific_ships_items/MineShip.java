package specific_ships_items;

import java.awt.Point;

import shipVisuals.FighterVisual;

import model.Ship;

public class MineShip extends Ship {
	
	public MineShip(Point newLocation) {
		super(newLocation);
		
		String description = "Mine that can explode, \ndealing damage to nearby \nenemies.";
		this.constructorAid("Mine", 0, 999, 999, 999, 999, 0,
				description, 0, 0, 0, 0);
		
		setVisual(new FighterVisual(this));
		
		setAbility(new MineAbility());
		
	}

}
