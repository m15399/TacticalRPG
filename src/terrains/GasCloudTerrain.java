package terrains;

import java.awt.Point;
import java.util.Random;

import model.Ship;
import terrainVisuals.GasCloudVisual;

public class GasCloudTerrain extends Terrain implements TerrainPopup{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4192307710658189728L;
	private static final int MAX_DAMAGE = 8;
	
	public GasCloudTerrain(Point newLocation, boolean trueIfBeginningOfGraphic) {
		super(newLocation);
		if(trueIfBeginningOfGraphic){
			this.setVisual(new GasCloudVisual(this));
		}
	}

	@Override
	public void applyEffect(Ship ship) {
		Random random = new Random();
		int damage = random.nextInt(MAX_DAMAGE) + 1; // damage
		ship.updateHull(-damage);
//		ship.updateShielding(-Math.random());
	}

	@Override
	public String getName() {
		String name = "Gas Cloud";
		return name;
	}

	@Override
	public String getDescription() {
		String description = "Damages passing ships.";
		return description;
	}
}
