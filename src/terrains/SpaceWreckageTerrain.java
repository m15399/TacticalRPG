package terrains;

import java.awt.Point;

import specific_ships_items.ScrapMetal;
import terrainVisuals.SpaceWreckageVisual;
import model.Ship;

/*
 * TODO's:
 * Add graphic in resources
 * Create Visual for this class then uncomment out line in constructor
 * Give letter code for this class for level generator
 */

public class SpaceWreckageTerrain extends Terrain implements TerrainEffects{

	public SpaceWreckageTerrain(Point newLocation) {
		super(newLocation);
		this.setVisual(new SpaceWreckageVisual(this));
	}

	@Override
	public void applyEffect(Ship ship) {
		ship.addToItems(new ScrapMetal());
	}

}
