package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Ship extends GameObject{
	private Point location;
	private int moves;
	private double hull, shielding, maxHull, maxShielding, accuracy, minDamage, maxDamage;
	private List<Item> items;
	private String description;
	
	public Ship(Point newLocation){
		location = newLocation;
		moves = 1;
		hull = 1;
		shielding = 1;
		maxHull = 1;
		maxShielding = 1;
		accuracy = 1;
		items = new ArrayList<Item>();
	}
	
	public boolean isShipDead(){
		if(hull > 0)
			return false;
		return true;
	}
	
	/**
	 * Helps remind whoever is making a new class of all the values that should be declared to avoid them being default
	 * @param moves
	 * @param hull
	 * @param shielding
	 * @param maxHull
	 * @param maxShielding
	 * @param accuracy
	 * @param items
	 * @param description
	 */
	
	public void constructorAid(int moves, double hull, double shielding, double maxHull, double maxShielding, double accuracy, 
			List<Item> items, String description, double minDamage, double maxDamage){
		setMoves(moves);
		setHull(hull);
		setShielding(shielding);
		setMaxHull(maxHull);
		setMaxShielding(maxShielding);
		setAccuracy(accuracy);
		setItems(items);
		setDescription(description);
		setMinDamage(minDamage);
		setMaxDamage(maxDamage);
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
	
	public void setItems(List<Item> newItemList){
		items = newItemList;
	}
	
	public void addToItems(Item newItem){
		items.add(newItem);
	}
	
	public void removeFromItems(Item itemUsed){
		items.remove(itemUsed);
	}
	
	public void setAccuracy(double newAccuracy){
		accuracy = newAccuracy;
	}
	
	public double getAccuracy(){
		return accuracy;
	}
	
	public void updateAccuracy(double valueToAdjustBy){
		accuracy += valueToAdjustBy;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String newDescription){
		description = newDescription;
	}
	
	public void setMinDamage(double newMinDamage){
		minDamage = newMinDamage;
	}
	
	public double getMinDamage(){
		return minDamage;
	}
	
	public void updateMinDamage(double valueToAdjustBy){
		minDamage += valueToAdjustBy;
	}
	
	public void setMaxDamage(double newMaxDamage){
		maxDamage = newMaxDamage;
	}
	
	public double getMaxDamage(){
		return maxDamage;
	}
	
	public void updateMaxDamage(double valueToAdjustBy){
		maxDamage += valueToAdjustBy;
	}
}
