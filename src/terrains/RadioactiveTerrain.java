package terrains;

import java.awt.Point;

import model.Ship;
import terrainVisuals.RadioactiveVisual;

public class RadioactiveTerrain extends Terrain implements TerrainEffects{
	
	public RadioactiveTerrain(Point newLocation, boolean trueIfBeginningOfGraphic) {
		super(newLocation);
		if(trueIfBeginningOfGraphic){
			this.setVisual(new RadioactiveVisual(this));
		}
	}

	@Override
	public void applyEffect(Ship ship) {
		ship.updateShielding(-Math.random());
		ship.updateRange(-1); //Radioactivity messes with components lowering range of ship
	}
}
