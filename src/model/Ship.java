package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import shipVisuals.ShipVisual;
import utils.Direction;
import utils.Observer;

public class Ship extends GameObject {
	private Point location;
	private int moves, range;
	private double hull, shielding, maxHull, maxShielding, accuracy, minDamage,
			maxDamage, critChance;
	private List<Item> items;
	private String description, name;
	
	private ShipVisual visual;

	public Ship(Point newLocation) {
		location = new Point(newLocation);
		constructorAid("DefaultShip", 1, 1, 1, 1, 1, 1, new ArrayList<Item>(),
				"No description available", 1, 1, 1, 1);
		visual = null;
	}

	public boolean isShipDead() {
		if (hull > 0)
			return false;
		return true;
	}

	/**
	 * Helps remind whoever is making a new class of all the values that should
	 * be declared to avoid them being default
	 * 
	 * @param name
	 * @param moves
	 * @param hull
	 * @param shielding
	 * @param maxHull
	 * @param maxShielding
	 * @param accuracy
	 * @param items
	 * @param description
	 */

	public void constructorAid(String name, int moves, double hull,
			double shielding, double maxHull, double maxShielding,
			double accuracy, List<Item> items, String description,
			double minDamage, double maxDamage, double critChance, int range) {
		setName(name);
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
		setCritChance(critChance);
		setRange(range);
	}
	
	/*
	 * Plays the move animation 
	 */
	public void moveWithDirections(Observer notifyWhenDone, int x, int y, List<Direction> directions){
		setLocation(new Point(x, y));
		ShipVisual visual = getVisual();
		if(visual != null){
			visual.moveWithDirections(notifyWhenDone, directions);
		}
	}
	
	/*
	 * Returns a random damage amount between the min and max damage values.
	 * Remember nextInt returns a value between 0 inclusive and some value exclusive.
	 */
	
	public double getDamage(){
		double dmgModifier = Math.random();
		return (maxDamage-minDamage)*dmgModifier + minDamage;
	}

	/*
	 * Setters and Getters for private instance variables. Please add other
	 * methods above these so they are easier to find.
	 */

	public void setVisual(ShipVisual newVisual){
		if(visual != null)
			visual.destroy();
		addChild(newVisual);
		visual = newVisual;
	}
	
	public ShipVisual getVisual(){
		return visual;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String newName){
		name = newName;
	}
	
	public Point getLocation() {
		return location;
	}

	public void setLocation(Point newLocation) {
		location = newLocation;
	}

	public double getHull() {
		return hull;
	}

	public void setHull(double newHullTotal) {
		hull = newHullTotal;
	}

	public void updateHull(double valueToAdjustHullBy) {
		hull += valueToAdjustHullBy;
	}

	public double getShielding() {
		return shielding;
	}

	public void setShielding(double newShieldingValue) {
		shielding = newShieldingValue;
	}

	public void updateShielding(double valueToAdjustShieldingBy) {
		shielding += valueToAdjustShieldingBy;
	}

	public double getMaxHull() {
		return maxHull;
	}

	public void setMaxHull(double newMaxHullTotal) {
		maxHull = newMaxHullTotal;
	}

	public void updateMaxHull(double valueToAdjustMaxHullBy) {
		maxHull += valueToAdjustMaxHullBy;
	}

	public double getMaxShielding() {
		return maxShielding;
	}

	public void setMaxShielding(double newMaxShieldingValue) {
		maxShielding = newMaxShieldingValue;
	}

	public void updateMaxShielding(double valueToAdjustMaxShieldingBy) {
		maxShielding += valueToAdjustMaxShieldingBy;
	}

	public int getMoves() {
		return moves;
	}

	public void setMoves(int newMoveTotal) {
		moves = newMoveTotal;
	}

	public void updateMoves(int valueToAdjustMovesBy) {
		moves += valueToAdjustMovesBy;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> newItemList) {
		items = newItemList;
	}

	public void addToItems(Item newItem) {
		items.add(newItem);
	}

	public void removeFromItems(Item itemUsed) {
		items.remove(itemUsed);
	}

	public void setAccuracy(double newAccuracy) {
		accuracy = newAccuracy;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void updateAccuracy(double valueToAdjustBy) {
		accuracy += valueToAdjustBy;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String newDescription) {
		description = newDescription;
	}

	public void setMinDamage(double newMinDamage) {
		minDamage = newMinDamage;
	}

	public double getMinDamage() {
		return minDamage;
	}

	public void updateMinDamage(double valueToAdjustBy) {
		minDamage += valueToAdjustBy;
	}

	public void setMaxDamage(double newMaxDamage) {
		maxDamage = newMaxDamage;
	}

	public double getMaxDamage() {
		return maxDamage;
	}

	public void updateMaxDamage(double valueToAdjustBy) {
		maxDamage += valueToAdjustBy;
	}

	public void setCritChance(double newCritValue) {
		critChance = newCritValue;
	}

	public double getCritChance() {
		return critChance;
	}

	public void updateCritChance(double valueToAdjustBy) {
		critChance += valueToAdjustBy;
	}
	
	public int getRange(){
		return range;
	}
	
	public void setRange(int newRange){
		range = newRange;
	}
	
	public void updateRange(int valueToAdjustBy){
		range += valueToAdjustBy;
	}
}
