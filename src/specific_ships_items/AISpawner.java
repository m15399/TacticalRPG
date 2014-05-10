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
		this.constructorAid("WarpGate", 0, 100, 20, 100, 20, 0, description, 0, 0, 0, 0, true, 1);
		
		setAbility(new SpawnEnemyShipAbility());
	}
	
	public void setTeam(int newTeam){
		if(newTeam == 0){
			super.setTeam(newTeam);
			setFileName("wormholeBlue.png");
			setVisual(new WarpVisual(this));
		}
		else if(newTeam == 1){
			super.setTeam(newTeam);
			setFileName("wormholeRed.png");
			setVisual(new WarpVisual(this));
		}
		else if(newTeam == 2){
			super.setTeam(newTeam);
			setFileName("wormholeGreen.png");
			setVisual(new WarpVisual(this));
		}
	}
	
}
