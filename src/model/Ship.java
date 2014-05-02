package model;

import java.awt.Point;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import shipVisuals.ShipVisual;
import utils.Direction;
import utils.Observer;
import view.Camera;

public class Ship extends GameObject {
	private Point location;
	private int moves, movesLeft, range;
	private double hull, shielding, maxHull, maxShielding, accuracy, minDamage,
			maxDamage, critChance;
	private List<Item> items;
	private Ability ability;
	private String description, name;
	private boolean canAttack, canMove, canUseAbility, canUseItem, isWaiting;
	private int team;
	
	private ShipVisual visual;

	public Ship(Point newLocation) {
		location = new Point(newLocation);
		constructorAid("DefaultShip", 1, 1, 1, 1, 1, 1, new ArrayList<Item>(),
				"No description available", 1, 1, 1, 1);
		visual = null;
		team = 0;
		ability = null;
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
	 * @param moves - meant as max movement range
	 * @param hull
	 * @param shielding
	 * @param maxHull
	 * @param maxShielding
	 * @param accuracy
	 * @param items
	 * @param description
	 * @param minDamage
	 * @param maxDamage
	 * @param critChance
	 * @param range - meant as ship's attack range
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

	
	public void startTurn(){
		setMovesLeft(moves);
		setCanAttack(true);
		setCanUseItem(true);
		setCanUseAbility(true);
		setIsWaiting(false);
		ability.startTurn();
	}
	
	/*
	 * Plays the move animation 
	 */
	public void moveWithDirections(Observer notifyWhenDone, int x, int y, List<Direction> directions, Camera camera){
		setLocation(new Point(x, y));
		ShipVisual visual = getVisual();
		if(visual != null){
			visual.moveWithDirections(notifyWhenDone, directions, camera);
		}
		updateMovesLeft(-directions.size());
	}
	
	/*
	 * Attack
	 */
	public void attack(Ship target) {
		
	}
	
	public void setIsWaiting(boolean b){
		isWaiting = b;
	}
	
	public boolean getIsWaiting(){
		return isWaiting;
	}
	
	/*
	 * Items
	 */
	
	public void itemUsed(Item item){
		items.remove(item);
		setCanUseItem(false);
	}
	
	/*
	 * Ability
	 */
	
	public void abilityUsed(){
		setCanUseAbility(false);
		ability.resetCooldown();
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
	 * Returns final damage after basic shielding calculations
	 */
	
	public double getFinalDamage(double initialDamage){
		if (initialDamage * ((100 - shielding) / 100) <= 0)
			return 0;
		else
			return (initialDamage * ((100 - shielding) / 100));
	}

	/*
	 * Setters and Getters for private instance variables. Please add other
	 * methods above these so they are easier to find.
	 */
	
	public void setAbility(Ability ability){
		this.ability = ability;
	}
	
	public Ability getAbility(){
		return ability;
	}
	
	public void setTeam(int newTeam){
		team = newTeam;
	}
	
	public int getTeam(){
		return team;
	}

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
		DecimalFormat df = new DecimalFormat("#.#");      
		hull = Double.valueOf(df.format(hull));
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
	
	public int getMovesLeft(){
		return movesLeft;
	}
	
	public void setMovesLeft(int newMovesLeft){
		movesLeft = newMovesLeft;
		if(movesLeft > 0){
			setCanMove(true);
		} else {
			setCanMove(false);
		}
	}
	
	public void updateMovesLeft(int valueToAdjustBy){
		setMovesLeft(getMovesLeft() + valueToAdjustBy);
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
	
	public Boolean getCanAttack() {
		return canAttack;
	}

	public void setCanAttack(boolean canAttack) {
		this.canAttack = canAttack;
	}

	public Boolean getCanMove() {
		return canMove;
	}

	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}

	public Boolean getCanUseAbility() {
		return canUseAbility;
	}

	public void setCanUseAbility(boolean canUseAbility) {
		this.canUseAbility = canUseAbility;
	}

	public boolean isCanUseItem() {
		return canUseItem;
	}

	public void setCanUseItem(boolean canUseItem) {
		this.canUseItem = canUseItem;
	}
	public List<Item> getItemsList(){
		return items;
	}
	public String itemsToString(){
		String itemsString = "";
		for(int i = 0; i < items.size(); i++){
			if(i == items.size()-1){
				itemsString += items.get(i).getName();
			}
			else{
			itemsString += items.get(i).getName() + ", ";
			}
		}
		return itemsString;
	}
	
	public String shipStatus(){
		String shipStatus = "";
		shipStatus += this.getName();
		shipStatus += "\n";
		shipStatus += "Hull: " + this.getHull();
		shipStatus += "\n";
		shipStatus += "Shielding: " + this.getMaxShielding();
		shipStatus += "\n";
		shipStatus += "Damage: " + this.getDamage();
		shipStatus += "\n";
		shipStatus += "Items: " + this.itemsToString();
		shipStatus += "\n";
		return shipStatus;
	}
	
	public String toString(){
		return shipStatus();
	}
}
