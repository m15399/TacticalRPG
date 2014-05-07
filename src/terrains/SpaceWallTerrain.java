package terrains;

import java.awt.Point;

import terrainVisuals.SpaceWallVisual;

public class SpaceWallTerrain extends Terrain{

	private String filename;
	
	public SpaceWallTerrain(Point newLocation) {
		super(newLocation);
		filename = "wall_bottom.png";
		this.setVisual(new SpaceWallVisual(this));
	}
	
	public String getFileName(){
		return filename;
	}
	
	public void setFilename(String newFileName){
		filename = newFileName;
	}
	
	public void setVisual(){
		this.setVisual(new SpaceWallVisual(this));
	}
}
