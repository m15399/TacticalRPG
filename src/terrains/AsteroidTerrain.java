package terrains;

import java.awt.Point;

import terrainVisuals.AsteroidVisual;

/*
 * Asteroids damage passing ships.
 */

public class AsteroidTerrain extends Terrain{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7474053009044091846L;

	public AsteroidTerrain(Point newLocation){
		super(newLocation);
		this.setVisual(new AsteroidVisual(this));
	}
}
