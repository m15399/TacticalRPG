package specific_ships_items;

import java.awt.Point;

import model.Ship;

import shipVisuals.WarpVisual;

/*
 * Appears to be a warpgate but is secretly a different class
 */
public class AISpawner extends Ship {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5118342767920923227L;

	public AISpawner(Point newLocation){
		super(newLocation);
		String description = "Faster-than-light\ntransport.";
		this.constructorAid("WarpGate", 0, 1, 0, 1, 0, 0, description, 0, 0, 0, 0, true, 1);
		
		setAbility(new SpawnEnemyShipAbility());
		
		setVisual(new WarpVisual(this));
	}
	
}
