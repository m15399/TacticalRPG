package terrains;

import java.awt.Point;

import terrainVisuals.SpaceWallVisual;

public class SpaceWallTerrain extends Terrain{

	public SpaceWallTerrain(Point newLocation) {
		super(newLocation);
		this.setVisual(new SpaceWallVisual(this));
	}
}
