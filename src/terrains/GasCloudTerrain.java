package terrains;

import java.awt.Point;

import model.Ship;
import terrainVisuals.GasCloudVisual;

public class GasCloudTerrain extends Terrain implements TerrainEffects{

	public GasCloudTerrain(Point newLocation, boolean trueIfBeginningOfGraphic) {
		super(newLocation);
		if(trueIfBeginningOfGraphic){
			this.setVisual(new GasCloudVisual(this));
		}
	}

	@Override
	public void applyEffect(Ship ship) {
		ship.updateShielding(-Math.random());
	}
}
