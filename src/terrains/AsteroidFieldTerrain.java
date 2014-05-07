package terrains;

import java.awt.Point;
import java.util.Random;

import terrainVisuals.AsteroidFieldVisual;
import model.Ship;

public class AsteroidFieldTerrain extends Terrain implements TerrainEffects{

	public AsteroidFieldTerrain(Point newLocation) {
		super(newLocation);
		this.setVisual(new AsteroidFieldVisual(this));
	}

	@Override
	public void applyEffect(Ship ship) {
		Random random = new Random();
		int damage = random.nextInt(100) + 1; //1-100 damage
		ship.updateHull(-damage);
	}
}
