package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Tried to wrap all the Tile classes into one class to simplify things and cut
 * down on the number of overall classes. After a ship has moved away from a
 * tile, or if for some other reason a tile becomes empty call setEmpty() to
 * reset the tile back to previous conditions. A built in alternative to this is
 * just making a new tile. Several different constructors have been provided to
 * minimize outside method calls.
 */

public class Tile extends GameObject {

	private boolean isOccupied, isEdge, isTerrain, hasShip;
	private Highlight highlight;
	private boolean mousedOver;
	private Ship ship;
	private int mapX, mapY;

	/*
	 * highlight colors
	 */
	public enum Highlight {
		NONE, BLUE, RED, GREEN;
	}

	private static float highlightAlpha = .4f;
	private static Color blueColor = new Color(0f, .2f, 1f, highlightAlpha);
	private static Color lightBlueColor = new Color(.5f, .5f, 1f,
			highlightAlpha);
	private static Color redColor = new Color(1f, .2f, .2f, highlightAlpha);
	private static Color lightRedColor = new Color(1f, .5f, .5f, highlightAlpha);
	private static Color greenColor = new Color(0f, .8f, .3f, highlightAlpha);
	private static Color lightGreenColor = new Color(.5f, 1f, .5f,
			highlightAlpha);

	/**
	 * Empty Tile constructor.  Default
	 */
	
	public Tile(){
		setEmpty();
	}
	
	/**
	 * Empty tile constructor for creation at certain coordinates.
	 */
	public Tile(int mapX, int mapY) {
		setEmpty();
		this.mapX = mapX;
		this.mapY = mapY;
	}

	/**
	 * Ship tile constructor.
	 * 
	 * @param newShip
	 */
	public Tile(Ship newShip) {
		setEmpty();
		setHasShip(true, newShip);
	}

	/**
	 * Terrain tile constructor
	 */
	public Tile(boolean trueIfTileHasTerrain, boolean trueIfTerrainCanBeOccupied) {
		setEmpty();
		setIsTerrain(trueIfTileHasTerrain, trueIfTerrainCanBeOccupied);
	}

	/**
	 * Edge tile constructor
	 * 
	 * @return
	 */

	public Tile(boolean trueIfTileIsEdgeTile) {
		setEmpty();
		setIsEdge(trueIfTileIsEdgeTile);
	}

	public boolean getIsOccupied() {
		return isOccupied;
	}

	public void setIsOccupied(boolean trueIfTileIsOccupied) {
		isOccupied = trueIfTileIsOccupied;
		if (!trueIfTileIsOccupied)
			hasShip = false;
	}

	public Highlight getHighlight() {
		return highlight;
	}

	public void setHighlight(Highlight newHighlight) {
		highlight = newHighlight;
	}

	public void setMousedOver(boolean b) {
		mousedOver = b;
	}

	public boolean getIsEdge() {
		return isEdge;
	}

	/**
	 * Automatically handles the tile being occupied.
	 */
	public void setIsEdge(boolean trueIfTileIsEdgeTile) {
		isEdge = trueIfTileIsEdgeTile;
		if (trueIfTileIsEdgeTile)
			isOccupied = true;
		else
			isOccupied = false;
	}

	public boolean getIsTerrain() {
		return isTerrain;
	}

	/**
	 * Automatically handles the tile being occupied.
	 */
	public void setIsTerrain(boolean trueIfTileHasTerrain, boolean trueIfTerrainCanBeOccupied) {
		isTerrain = trueIfTileHasTerrain;
		if (!trueIfTerrainCanBeOccupied)
			isOccupied = true;
	}

	public boolean getHasShip() {
		return hasShip;
	}

	/**
	 * Automatically handles the tile being occupied.
	 */
	public void setHasShip(boolean trueIfTileHasShip, Ship theShip) {
		hasShip = trueIfTileHasShip;
		ship = theShip;
		if (trueIfTileHasShip)
			isOccupied = true;
		else
			isOccupied = false;
	}

	/**
	 * Resets the tile back to default state.
	 */
	public void setEmpty() {
		ship = null;
		isOccupied = false;
		isEdge = false;
		isTerrain = false;
		highlight = Highlight.NONE;
		hasShip = false;
	}

	public Ship getShip() {
		if (!hasShip)
			System.out
					.println("This tile does not have a Ship to return. Considering calling getHasShip() to see if it has a Ship first.");
		return ship;
	}

	public void draw(Graphics g) {
		switch (highlight) {
		case NONE:
			break;
		case BLUE:
			if (mousedOver) {
				g.setColor(lightBlueColor);
			} else {
				g.setColor(blueColor);
			}
			break;
		case RED:
			if (mousedOver) {
				g.setColor(lightRedColor);
			} else {
				g.setColor(redColor);
			}
			break;
		case GREEN:
			if (mousedOver) {
				g.setColor(lightGreenColor);
			} else {
				g.setColor(greenColor);
			}
			break;

		}
		
		if (highlight != Highlight.NONE) {
			Point px = Map.mapToPixelCoords(new Point(mapX, mapY));
			g.fillRect((int) px.getX(), (int) px.getY(), Map.TILESIZE,
					Map.TILESIZE);
		}
	}
	
	public String toString(){
		String result = "";
		if(isEdge){
			result += "E";
		}
		else if(hasShip && ship instanceof Scout){
			result += "S";
		}
		else if(isTerrain && !isOccupied){
			result += "B";
		}
		else if(isTerrain && isOccupied){
			result += "T";
		}
		else if(!isOccupied){
			result += "0";
		}
		else{
			System.out.println("Some error happened reading this Tile.  Placed 1 in for spot.");
			result += "1";
		}
		return result;
	}
}
