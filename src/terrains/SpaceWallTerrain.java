package terrains;

import java.awt.Point;

import terrainVisuals.SpaceWallVisual;

public class SpaceWallTerrain extends Terrain{

	private String filename;
	
	public SpaceWallTerrain(Point newLocation) {
		super(newLocation);
		this.setVisual(new SpaceWallVisual(this));
	}
	
	public String getFileName(){
		return filename;
	}
	
	public void setFilename(String newFileName){
		filename = newFileName;
	}
}
