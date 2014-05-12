package terrains;

import java.awt.Point;

import model.Ship;
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

	@Override
	public void applyEffect(Ship ship) {
		// TODO Auto-generated method stub
		
	}
}
