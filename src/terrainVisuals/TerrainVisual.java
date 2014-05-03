package terrainVisuals;

import java.awt.Graphics;
import java.awt.Point;

import terrains.Terrain;
import model.Entity;
import model.Map;

public class TerrainVisual extends Entity{
	
	private Terrain terrain;
	
	public TerrainVisual(Terrain terrain){
		this.terrain = terrain;
	}
	
	public void setPositionToTerrainCoords(){
		Point coords = Map.mapToPixelCoords(terrain.getLocation());
		getPosition().setX(coords.getX() + Map.TILESIZE / 2);
		getPosition().setY(coords.getY() + Map.TILESIZE / 2);
	}
	
	public void draw(Graphics g1){
		
	}
}
