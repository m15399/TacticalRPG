package terrains;

import java.awt.Point;
import java.util.Random;

import terrainVisuals.AsteroidFieldVisual;
import model.Ship;

public class AsteroidFieldTerrain extends Terrain implements TerrainEffects{

	private static int MAX_DAMAGE = 10;
	
	public AsteroidFieldTerrain(Point newLocation) {
		super(newLocation);
		this.setVisual(new AsteroidFieldVisual(this));
	}

	@Override
	public void applyEffect(Ship ship) {
		Random random = new Random();
		int damage = random.nextInt(MAX_DAMAGE) + 1; // damage
		ship.updateHull(-damage);
	}
}
