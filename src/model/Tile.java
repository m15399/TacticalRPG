package model;


/**
 * Tried to wrap all the Tile classes into one class to simplify things and cut down on the number of overall classes.
 * After a ship has moved away from a tile, or if for some other reason a tile becomes empty call setEmpty() to reset the tile back to previous conditions.
 * A built in alternative to this is just making a new tile.  Several different constructors have been provided to minimize outside method calls.
 */

public class Tile extends GameObject{
	private boolean isOccupied, isHighlighted, isEdge, isTerrain, hasShip;
	private Ship ship;
	
	/**
	 * Empty tile constructor
	 */
	public Tile(){
		setEmpty();
	}
	
	/**
	 * Ship tile constructor.
	 * @param newShip
	 */
	public Tile(Ship newShip){
		setEmpty();
		setHasShip(true, newShip);
	}
	
	/**
	 * Terrain tile constructor
	 */
	public Tile(boolean trueIfTileHasTerrain, boolean trueIfTerrainCanBeOccupied){
		setEmpty();
		setIsTerrain(trueIfTileHasTerrain, trueIfTerrainCanBeOccupied);
	}
	
	/**
	 * Edge tile constructor
	 * @return
	 */
	
	public Tile(boolean trueIfTileIsEdgeTile){
		setEmpty();
		setIsEdge(trueIfTileIsEdgeTile);
	}
	
	public boolean getIsOccupied(){
		return isOccupied;
	}
	
	public void setIsOccupied(boolean trueIfTileIsOccupied){
		isOccupied = trueIfTileIsOccupied;
		if(!trueIfTileIsOccupied)
			hasShip = false;
	}
	
	public boolean getIsHighlighted(){
		return isHighlighted;
	}
	
	public void setIsHighlighted(boolean trueIfTileIsHighlighted){
		isHighlighted = trueIfTileIsHighlighted;
	}
	
	public boolean getIsEdge(){
		return isEdge;
	}
	
	/**
	 * Automatically handles the tile being occupied.
	 */
	public void setIsEdge(boolean trueIfTileIsEdgeTile){
		isEdge = trueIfTileIsEdgeTile;
		if(trueIfTileIsEdgeTile)
			isOccupied = true;
		else
			isOccupied = false;
	}
	
	public boolean getIsTerrain(){
		return isTerrain;
	}
	
	/**
	 * Automatically handles the tile being occupied.
	 */
	public void setIsTerrain(boolean trueIfTileHasTerrain, boolean trueIfTerrainCanBeOccupied){
		isTerrain = trueIfTileHasTerrain;
		if(!trueIfTerrainCanBeOccupied)
			isOccupied = true;
	}
	
	public boolean getHasShip(){
		return hasShip;
	}
	
	/**
	 * Automatically handles the tile being occupied.
	 */
	public void setHasShip(boolean trueIfTileHasShip, Ship theShip){
		hasShip = trueIfTileHasShip;
		ship = theShip;
		if(trueIfTileHasShip)
			isOccupied = true;
		else
			isOccupied = false;
	}
	
	/**
	 * Resets the tile back to default state.
	 */
	public void setEmpty(){
		ship = null;
		isOccupied = false;
		isHighlighted = false;
		isEdge = false;
		isTerrain = false;
		hasShip = false;
	}
	
	public Ship getShip(){
		if(!hasShip)
			System.out.println("This tile does not have a Ship to return. Considering calling getHasShip() to see if it has a Ship first.");
		return ship;
	}
}
