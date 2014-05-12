package terrains;

import java.awt.Point;

import model.Ship;
import terrainVisuals.PlanetVisual2x2;

public class PlanetTerrain2x2 extends Terrain{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9094280472386318056L;

	public PlanetTerrain2x2(Point newLocation) {
		super(newLocation);
		this.setVisual(new PlanetVisual2x2(this));
	}

	@Override
	public void applyEffect(Ship ship) {
		// TODO Auto-generated method stub
		
	}
}
