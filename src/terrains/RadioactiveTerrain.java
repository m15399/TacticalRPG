package terrains;

import java.awt.Point;
import java.util.Random;

import model.Ship;
import terrainVisuals.RadioactiveVisual;

public class RadioactiveTerrain extends Terrain implements TerrainEffects{
	
	private static final int MAX_DAMAGE = 8;
	
	public RadioactiveTerrain(Point newLocation, boolean trueIfBeginningOfGraphic) {
		super(newLocation);
		if(trueIfBeginningOfGraphic){
			this.setVisual(new RadioactiveVisual(this));
		}
	}

	@Override
	public void applyEffect(Ship ship) {
		Random random = new Random();
		int damage = random.nextInt(MAX_DAMAGE) + 1; // damage
		ship.updateHull(-damage);
//		ship.updateShielding(-Math.random());
//		ship.updateRange(-1); //Radioactivity messes with components lowering range of ship
	}
}
