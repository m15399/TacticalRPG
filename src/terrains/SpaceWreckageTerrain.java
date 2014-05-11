package terrains;

import java.awt.Point;

import specific_ships_items.ScrapMetal;
import terrainVisuals.SpaceWreckageVisual;
import model.Ship;

public class SpaceWreckageTerrain extends Terrain implements TerrainEffects{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5886097809816229937L;

	public SpaceWreckageTerrain(Point newLocation) {
		super(newLocation);
		this.setVisual(new SpaceWreckageVisual(this));
	}

	@Override
	public void applyEffect(Ship ship) {
		double random = Math.random();
		System.out.println("random number test is random: " + random);
		if(random > .5f){
			ship.addToItems(new ScrapMetal());
		}
	}

}
