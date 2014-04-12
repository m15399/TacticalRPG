package model;

import java.awt.Image;

/*
 * Tried to wrap all the Tile classes into one class to simplify things and cut down on the number of overall classes.
 * The Tile stores information about itself
 */

public class Tile extends Map{
	private boolean isOccupied, isHighlighted, isEdge, isTerrain, hasShip;
	private Ship ship;
	
	public Tile(){
		isOccupied = false;
		isHighlighted = false;
		isEdge = false;
		isTerrain = false;
		hasShip = false;
	}
	
	public boolean getIsOccupied(){
		return isOccupied;
	}
	
	public void setIsOccupied(boolean value){
		isOccupied = value;
	}
	
	public boolean getIsHighlighted(){
		return isHighlighted;
	}
	
	public void setIsHighlighted(boolean value){
		isHighlighted = value;
	}
	
	public boolean getIsEdge(){
		return isEdge;
	}
	
	public void setIsEdge(boolean value){
		isEdge = value;
		isOccupied = true;
	}
	
	public boolean getIsTerrain(){
		return isTerrain;
	}
	
	public void setIsTerrain(boolean terrainValue, boolean canTerrainBeOccupied, Image newTerrainImage){
		isTerrain = terrainValue;
		if(!canTerrainBeOccupied)
			isOccupied = true;
	}
	
	public boolean getHasShip(){
		return hasShip;
	}
	
	public void setHasShip(boolean value, Ship newShip){
		hasShip = value;
		ship = newShip;
		isOccupied = true;
	}
	
	public Ship getShip(){
		if(!hasShip)
			System.out.println("This tile does not have a Ship to return. Considering calling getHasShip() to see if it has a Ship first.");
		return ship;
	}
}
