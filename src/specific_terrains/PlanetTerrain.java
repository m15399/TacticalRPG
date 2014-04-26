package specific_terrains;

import java.awt.Point;

import model.Ship;
import model.Terrain;

public class PlanetTerrain extends Terrain{

	public PlanetTerrain(Point newLocation) {
		super(newLocation);
	}

	@Override
	public void applyEffect(Ship ship) {
		// Do Nothing
		// Tile should be unpassable
	}
}
