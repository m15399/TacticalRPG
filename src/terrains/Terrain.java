package terrains;

import java.awt.Point;

import terrainVisuals.TerrainVisual;
import model.GameObject;
import model.Ship;

public abstract class Terrain extends GameObject implements TerrainEffects {
	private Point location;
	private TerrainVisual visual;
	
	public Terrain(Point newLocation){
		location = newLocation;
		visual = null;
	}
	
	public Point getLocation(){
		return location;
	}
	
	public void setLocation(Point newLocation){
		location = newLocation;
	}
	
	public void setVisual(TerrainVisual newVisual){
		if(visual != null)
			visual.destroy();
		addChild(newVisual);
		visual = newVisual;
	}
	
	public TerrainVisual getVisual(){
		return visual;
	}
	
	public void applyEffect(Ship ship){
		
	}
}
