package specific_ships_items;

import java.awt.Point;
import model.Ship;
import shipVisuals.ScoutVisual;

public class WarpGateShip extends Ship {

	public WarpGateShip(Point newLocation){
		super(newLocation);
		
		String description = "Faster-than-light\ntransport.";
		this.constructorAid("WarpGate", 0, 100, 20, 100, 20, 0, description, 0, 0, 0, 0);

		setVisual(new ScoutVisual(this));
	}
	
}
