package terrains;

import java.awt.Point;

import model.GameObject;

public abstract class Terrain extends GameObject{
	private Point location;
	
	public Terrain(Point newLocation){
		location = newLocation;
	}
	
	public Point getLocation(){
		return location;
	}
	
	public void setLocation(Point newLocation){
		location = newLocation;
	}
}
