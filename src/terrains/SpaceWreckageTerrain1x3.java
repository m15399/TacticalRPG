package terrains;

import java.awt.Point;

import terrainVisuals.SpaceWreckageVisual1x3;

public class SpaceWreckageTerrain1x3 extends Terrain{

	/**
	 * 
	 */
	private static final long serialVersionUID = -106152110452672740L;

	public SpaceWreckageTerrain1x3(Point newLocation, boolean trueIfBeginningOfGraphic) {
		super(newLocation);
		if(trueIfBeginningOfGraphic){
			this.setVisual(new SpaceWreckageVisual1x3(this));
		}
	}

}
