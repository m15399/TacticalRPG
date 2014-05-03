package terrainVisuals;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import terrains.Terrain;
import model.Entity;
import model.Map;

public class TerrainVisual extends Entity{
	
	private Terrain terrain;
	
	public TerrainVisual(Terrain terrain){
		this.terrain = terrain;
		setPositionToTerrainCoords();
	}
	
	public void setPositionToTerrainCoords(){
		Point coords = Map.mapToPixelCoords(terrain.getLocation());
		getPosition().setX(coords.getX() + Map.TILESIZE);
		getPosition().setY(coords.getY() + Map.TILESIZE);
	}
	
	public void draw(Graphics g1){
		Graphics2D g = (Graphics2D) g1;
		g.drawRect(terrain.getLocation().x, terrain.getLocation().y, Map.TILESIZE, Map.TILESIZE);
	}
}
