package terrains;

import java.awt.Point;

import terrainVisuals.AsteroidVisual;

/*
 * Asteroids damage passing ships.
 */

public class AsteroidTerrain extends Terrain{
	
	public AsteroidTerrain(Point newLocation){
		super(newLocation);
		this.setVisual(new AsteroidVisual(this));
	}
}
