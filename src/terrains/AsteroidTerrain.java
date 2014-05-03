package terrains;

import java.awt.Point;
import java.util.Random;

import terrainVisuals.AsteroidVisual;
import model.Ship;

/*
 * Asteroids damage passing ships.
 */

public class AsteroidTerrain extends Terrain{
	
	public AsteroidTerrain(Point newLocation){
		super(newLocation);
		this.setVisual(new AsteroidVisual(this));
	}

	public void applyEffect(Ship ship) {
		Random random = new Random();
		int damage = random.nextInt(100) + 1; //1-100 damage
		ship.updateHull(-damage);
	}
}
