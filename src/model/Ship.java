package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Ship extends GameObject{
	private Point location;
	private int health, armor, moves;
	private List<Item> items;
	
	public Ship(Point newLocation){
		location = newLocation;
		health = 1;
		armor = 1;
		moves = 1;
		items = new ArrayList<Item>();
	}
	
	public boolean isShipDead(){
		if(health > 0)
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
	
	public int getHealth(){
		return health;
	}
	
	public void setHealth(int newHealthTotal){
		health = newHealthTotal;
	}
	
	public void updateHealth(int valueToAdjustHealthBy){
		health += valueToAdjustHealthBy;
	}
	
	public int getArmor(){
		return armor;
	}
	
	public void setArmor(int newArmorValue){
		armor = newArmorValue;
	}
	
	public void updateArmor(int valueToAdjustArmorBy){
		armor += valueToAdjustArmorBy;
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
