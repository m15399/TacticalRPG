package shipVisuals;

import model.Map;
import model.Ship;

public class MothershipVisual extends ShipVisual {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5562077940544244427L;

	
	public MothershipVisual(Ship ship) {
		super(ship);

		getSprite().getPosition().moveBy(0, -Map.TILESIZE);
	}

	
	
}
