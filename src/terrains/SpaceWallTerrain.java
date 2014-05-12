package terrains;

import java.awt.Point;

import model.Ship;
import terrainVisuals.SpaceWallVisual;

public class SpaceWallTerrain extends Terrain{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2612205335032657329L;
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

	@Override
	public void applyEffect(Ship ship) {
		// TODO Auto-generated method stub
		
	}
}
