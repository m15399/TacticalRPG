package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Ship extends GameObject{
	private Point location;
	private int moves;
	private double hull, shielding, maxHull, maxShielding;
	private List<Item> items;
	
	public Ship(Point newLocation){
		location = newLocation;
		hull = 1;
		shielding = 1;
		moves = 1;
		items = new ArrayList<Item>();
	}
	
	public boolean isShipDead(){
		if(hull > 0)
			return false;
		return true;
	}
	
	/*
	 * Setters and Getters for private instance variables.  Please add other methods above these so they are easier to find.
	 */
	
	public Point getLocation(){
		return location;
	}
	
	public void setLocation(Point newLocation){
		location = newLocation;
	}
	
	public double getHull(){
		return hull;
	}
	
	public void setHull(double newHullTotal){
		hull = newHullTotal;
	}
	
	public void updateHull(double valueToAdjustHullBy){
		hull += valueToAdjustHullBy;
	}
	
	public double getShielding(){
		return shielding;
	}
	
	public void setShielding(double newShieldingValue){
		shielding = newShieldingValue;
	}
	
	public void updateShielding(double valueToAdjustShieldingBy){
		shielding += valueToAdjustShieldingBy;
	}
	
	public double getMaxHull(){
		return maxHull;
	}
	
	public void setMaxHull(double newMaxHullTotal){
		maxHull = newMaxHullTotal;
	}
	
	public void updateMaxHull(double valueToAdjustMaxHullBy){
		maxHull += valueToAdjustMaxHullBy;
	}
	
	public double getMaxShielding(){
		return maxShielding;
	}
	
	public void setMaxShielding(double newMaxShieldingValue){
		maxShielding = newMaxShieldingValue;
	}
	
	public void updateMaxShielding(double valueToAdjustMaxShieldingBy){
		maxShielding += valueToAdjustMaxShieldingBy;
	}
	
	public int getMoves(){
		return moves;
	}
	
	public void setMoves(int newMoveTotal){
		moves = newMoveTotal;
	}
	
	public void updateMoves(int valueToAdjustMovesBy){
		moves += valueToAdjustMovesBy;
	}
	
	public List<Item> getItems(){
		return items;
	}
	
	public void addToItems(Item newItem){
		items.add(newItem);
	}
	
	public void removeFromItems(Item itemUsed){
		items.remove(itemUsed);
	}
}
