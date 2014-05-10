package terrainVisuals;

import java.awt.Point;

import terrains.Terrain;
import model.Entity;
import model.Map;

public class TerrainVisual extends Entity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5993689681861636467L;
	private Terrain terrain;
	
	public TerrainVisual(Terrain terrain){
		this.terrain = terrain;
		setPositionToTerrainCoords();
	}
	
	public void setPositionToTerrainCoords(){
		Point coords = Map.mapToPixelCoords(terrain.getLocation());
		getPosition().setX(coords.getX() + Map.TILESIZE / 2);
		getPosition().setY(coords.getY() + Map.TILESIZE / 2);
	}
}
