package specific_ships_items;

import java.awt.Point;

import shipVisuals.MineVisual;

import model.Ship;

public class MineShip extends Ship {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7638781214134803915L;

	public MineShip(Point newLocation) {
		super(newLocation);
		
		String description = "Mine that can explode, \ndealing damage to nearby \nenemies.";
		this.constructorAid("Mine", 0, 1, 0, 1, 0, 0,
				description, 0, 0, 0, 0, false, 0);
		
		setVisual(new MineVisual(this));
		
		setAbility(new MineAbility());
		
	}

}
