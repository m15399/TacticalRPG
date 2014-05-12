package terrains;

import java.awt.Point;

import specific_ships_items.ScrapMetal;
import terrainVisuals.SpaceWreckageVisual;
import model.Ship;

public class SpaceWreckageTerrain extends Terrain implements TerrainPopup{

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
		if(Math.random() > .5){
			ship.addToItems(new ScrapMetal());
		}
	}

	@Override
	public String getName() {
		String name = "Space Wreckage";
		return name;
	}

	@Override
	public String getDescription() {
		String description = "Move a ship onto this for a\nchance of getting scrap metal.\nShip cannot sit on terrain for\nadditional chances.";
		return description;
	}
}
